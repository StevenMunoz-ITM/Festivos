package festivos.api.aplicacion;

import festivos.api.core.IFestivoService;
import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.infraestructura.repositorios.FestivoRepository;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import festivos.api.aplicacion.mapper.FestivoMapper;
import festivos.api.aplicacion.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FestivoService extends BaseService<Festivo, FestivoDTO, Long> implements IFestivoService {
    
    @Autowired
    private FestivoRepository festivoRepository;
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;
    
    @Autowired
    private FestivoMapper festivoMapper;

    @Autowired
    private ValidationUtils validationUtils;

    @Override
    protected JpaRepository<Festivo, Long> getRepository() {
        return festivoRepository;
    }

    @Override
    protected Object getMapper() {
        return festivoMapper;
    }

    @Override
    protected FestivoDTO mapToDTO(Festivo entity) {
        return festivoMapper.toDTO(entity);
    }

    @Override
    protected Festivo mapToEntity(FestivoDTO dto) {
        return festivoMapper.toEntity(dto);
    }

    @Override
    protected List<FestivoDTO> mapToDTOList(List<Festivo> entities) {
        return festivoMapper.toDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(Festivo entity, FestivoDTO dto) {
        entity.setPais(festivoMapper.paisIdToPais(dto.getPaisId()));
        entity.setNombre(dto.getNombre());
        entity.setDia(dto.getDia());
        entity.setMes(dto.getMes());
        entity.setDiasPascua(dto.getDiasPascua());
        entity.setTipoFestivo(festivoMapper.tipoFestivoIdToTipoFestivo(dto.getTipoFestivoId()));
    }

    @Override
    protected String getEntityName() {
        return "Festivo";
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorPais(Long paisId) {
        return festivoMapper.toDTOList(festivoRepository.findByPaisId(paisId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorFecha(Integer dia, Integer mes) {
        return festivoMapper.toDTOList(festivoRepository.findByDiaAndMes(dia, mes));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorPaisYMes(Long paisId, Integer mes) {
        return festivoMapper.toDTOList(festivoRepository.findByPaisIdAndMes(paisId, mes));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascua() {
        return festivoMapper.toDTOList(festivoRepository.findByDiasPascuaIsNotNull());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascuaPorPais(Long paisId) {
        return festivoMapper.toDTOList(festivoRepository.findByPaisIdAndDiasPascuaIsNotNull(paisId));
    }

    @Override
    public FestivoDTO guardar(FestivoDTO festivoDTO) {
        Festivo festivo = festivoMapper.toEntity(festivoDTO);
        validarFestivo(festivo);
        Festivo festivoGuardado = festivoRepository.save(festivo);
        return festivoMapper.toDTO(festivoGuardado);
    }

    @Override
    public FestivoDTO actualizar(Long id, FestivoDTO festivoDTO) {
        Festivo festivoExistente = festivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festivo no encontrado con ID: " + id));
        
        updateEntityFromDTO(festivoExistente, festivoDTO);
        validarFestivo(festivoExistente);
        Festivo festivoActualizado = festivoRepository.save(festivoExistente);
        return festivoMapper.toDTO(festivoActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esFestivo(LocalDate fecha, Long paisId) {
        List<Festivo> festivosDelPais = festivoRepository.findByPaisId(paisId);
        
        for (Festivo festivo : festivosDelPais) {
            LocalDate fechaFestivo = calcularFechaFestivo(festivo, fecha.getYear());
            if (fechaFestivo != null && fechaFestivo.equals(fecha)) {
                return true;
            }
        }
        
        return false;
    }

    public LocalDate calcularFechaFestivo(Festivo festivo, int año) {
        LocalDate fechaBase;
        
        if (festivo.getDia() != null && festivo.getMes() != null) {
            fechaBase = LocalDate.of(año, festivo.getMes(), festivo.getDia());
        } else if (festivo.getDiasPascua() != null) {
            LocalDate pascua = calcularPascua(año);
            fechaBase = pascua.plusDays(festivo.getDiasPascua());
        } else {
            return null;
        }
        
        String tipoFestivo = festivo.getTipoFestivo().getTipo();
        if (tipoFestivo.contains("Puente festivo")) {
            return trasladarAlSiguienteLunes(fechaBase);
        }
        
        return fechaBase;
    }

    private LocalDate trasladarAlSiguienteLunes(LocalDate fecha) {
        if (fecha.getDayOfWeek().getValue() == 1) {
            return fecha;
        }
        
        int diasHastaLunes = 8 - fecha.getDayOfWeek().getValue();
        return fecha.plusDays(diasHastaLunes);
    }

    @Override
    @Transactional(readOnly = true)
    public LocalDate obtenerFechaCelebracion(Long festivoId, int año) {
        Festivo festivo = festivoRepository.findById(festivoId)
                .orElseThrow(() -> new IllegalArgumentException("Festivo no encontrado con ID: " + festivoId));
        
        return calcularFechaFestivo(festivo, año);
    }

    private LocalDate calcularPascua(int año) {
        LocalDate domingoDeRamos = calcularDomingoDeRamos(año);
        return domingoDeRamos.plusDays(7);
    }

    @Override
    public LocalDate calcularDomingoDeRamos(int año) {
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        LocalDate fechaBase = LocalDate.of(año, 3, 15);
        
        return fechaBase.plusDays(dias);
    }

    private void validarFestivo(Festivo festivo) {
        validarEntidadesRequeridas(festivo);
        validarTipoFecha(festivo);
    }

    private void validarEntidadesRequeridas(Festivo festivo) {
        validationUtils.validarCampoRequerido(festivo.getPais(), "país");
        validationUtils.validarEntidadExiste(paisRepository::existsById, festivo.getPais().getId(), "País");
        
        validationUtils.validarCampoRequerido(festivo.getTipoFestivo(), "tipo de festivo");
        validationUtils.validarEntidadExiste(tipoFestivoRepository::existsById, festivo.getTipoFestivo().getId(), "Tipo de festivo");
    }

    private void validarTipoFecha(Festivo festivo) {
        boolean tieneFechaFija = festivo.getDia() != null && festivo.getMes() != null;
        boolean tieneDiasPascua = festivo.getDiasPascua() != null;
        
        if (tieneFechaFija && tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo no puede tener fecha fija y días de Pascua al mismo tiempo");
        }
        
        if (!tieneFechaFija && !tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo debe tener fecha fija o días de Pascua");
        }
        
        if (tieneFechaFija) {
            validarFechaFija(festivo);
        }
    }

    private void validarFechaFija(Festivo festivo) {
        validationUtils.validarRango(festivo.getDia(), 1, 31, "día");
        validationUtils.validarRango(festivo.getMes(), 1, 12, "mes");
        
        try {
            LocalDate.of(2024, festivo.getMes(), festivo.getDia());
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha inválida: día " + festivo.getDia() + ", mes " + festivo.getMes());
        }
    }
}

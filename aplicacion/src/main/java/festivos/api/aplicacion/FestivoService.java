package festivos.api.aplicacion;

import festivos.api.core.IFestivoService;
import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.infraestructura.repositorios.FestivoRepository;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import festivos.api.aplicacion.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FestivoService extends BaseService<Festivo, FestivoDTO, Long> implements IFestivoService {
    
    @Autowired
    private FestivoRepository festivoRepository;
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;

    @Autowired
    private ValidationUtils validationUtils;

    @Override
    protected JpaRepository<Festivo, Long> getRepository() {
        return this.festivoRepository;
    }

    @Override
    protected Object getMapper() {
        return this; // Retornamos this ya que tenemos métodos de conversión locales
    }

    @Override
    protected FestivoDTO mapToDTO(final Festivo entity) {
       
        final FestivoDTO dto = this.convertToDTO(entity);
        try {
            final int año = java.time.LocalDate.now().getYear();
            final java.time.LocalDate fecha = this.calcularFechaFestivo(entity, año);
            dto.setFechaCelebracion(fecha);
        } catch (final Exception ex) {
           
        }
        return dto;
    }

    @Override
    protected Festivo mapToEntity(final FestivoDTO dto) {
        return this.convertToEntity(dto);
    }

    @Override
    protected List<FestivoDTO> mapToDTOList(final List<Festivo> entities) {
        return this.convertToDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(final Festivo entity, final FestivoDTO dto) {
        // Buscar y asignar el país
        if (dto.getPaisId() != null) {
            entity.setPais(paisRepository.findById(dto.getPaisId())
                    .orElseThrow(() -> new IllegalArgumentException("País no encontrado con ID: " + dto.getPaisId())));
        }
        
        // Buscar y asignar el tipo de festivo
        if (dto.getTipoFestivoId() != null) {
            entity.setTipoFestivo(tipoFestivoRepository.findById(dto.getTipoFestivoId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de festivo no encontrado con ID: " + dto.getTipoFestivoId())));
        }
        
        entity.setNombre(dto.getNombre());
        entity.setDia(dto.getDia());
        entity.setMes(dto.getMes());
        entity.setDiasPascua(dto.getDiasPascua());
    }

    @Override
    protected String getEntityName() {
        return "Festivo";
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorPais(final Long paisId) {
        return this.convertToDTOList(this.festivoRepository.findByPaisId(paisId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorFecha(final Integer dia, final Integer mes) {
        return this.convertToDTOList(this.festivoRepository.findByDiaAndMes(dia, mes));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorPaisYMes(final Long paisId, final Integer mes) {
        return this.convertToDTOList(this.festivoRepository.findByPaisIdAndMes(paisId, mes));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascua() {
        return this.convertToDTOList(this.festivoRepository.findByDiasPascuaIsNotNull());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascuaPorPais(final Long paisId) {
        return this.convertToDTOList(this.festivoRepository.findByPaisIdAndDiasPascuaIsNotNull(paisId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> buscar(final String nombre) {
        return this.convertToDTOList(this.festivoRepository.buscar(nombre));
    }

    @Override
    public FestivoDTO guardar(final FestivoDTO festivoDTO) {
        final Festivo festivo = this.convertToEntity(festivoDTO);
        this.validarFestivo(festivo);
        final Festivo festivoGuardado = this.festivoRepository.save(festivo);
        return this.convertToDTO(festivoGuardado);
    }

    @Override
    public FestivoDTO actualizar(final Long id, final FestivoDTO festivoDTO) {
        final Festivo festivoExistente = this.festivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festivo no encontrado con ID: " + id));
        
        this.updateEntityFromDTO(festivoExistente, festivoDTO);
        this.validarFestivo(festivoExistente);
        final Festivo festivoActualizado = this.festivoRepository.save(festivoExistente);
        return this.convertToDTO(festivoActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esFestivo(final LocalDate fecha, final Long paisId) {
        final List<Festivo> festivosDelPais = this.festivoRepository.findByPaisId(paisId);
        
        for (final Festivo festivo : festivosDelPais) {
            final LocalDate fechaFestivo = this.calcularFechaFestivo(festivo, fecha.getYear());
            if (fechaFestivo != null && fechaFestivo.equals(fecha)) {
                return true;
            }
        }
        
        return false;
    }

    public LocalDate calcularFechaFestivo(final Festivo festivo, final int año) {
        LocalDate fechaBase;
        
        if (festivo.getDia() != null && festivo.getMes() != null) {
            fechaBase = LocalDate.of(año, festivo.getMes(), festivo.getDia());
        } else if (festivo.getDiasPascua() != null) {
            final LocalDate pascua = this.calcularPascua(año);
            fechaBase = pascua.plusDays(festivo.getDiasPascua());
        } else {
            return null;
        }
        
        final String tipoFestivo = festivo.getTipoFestivo().getTipo();
        if (tipoFestivo.contains("Puente festivo")) {
            return this.trasladarAlSiguienteLunes(fechaBase);
        }
        
        return fechaBase;
    }

    private LocalDate trasladarAlSiguienteLunes(final LocalDate fecha) {
        if (fecha.getDayOfWeek().getValue() == 1) {
            return fecha;
        }
        
        final int diasHastaLunes = 8 - fecha.getDayOfWeek().getValue();
        return fecha.plusDays(diasHastaLunes);
    }

    @Override
    @Transactional(readOnly = true)
    public LocalDate obtenerFechaCelebracion(final Long festivoId, final int año) {
        final Festivo festivo = this.festivoRepository.findById(festivoId)
                .orElseThrow(() -> new IllegalArgumentException("Festivo no encontrado con ID: " + festivoId));
        
        return this.calcularFechaFestivo(festivo, año);
    }

    private LocalDate calcularPascua(final int año) {
        final LocalDate domingoDeRamos = this.calcularDomingoDeRamos(año);
        return domingoDeRamos.plusDays(7);
    }

    @Override
    public LocalDate calcularDomingoDeRamos(final int año) {
        final int a = año % 19;
        final int b = año % 4;
        final int c = año % 7;
        final int d = (19 * a + 24) % 30;
        
        final int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        final LocalDate fechaBase = LocalDate.of(año, 3, 15);
        
        return fechaBase.plusDays(dias);
    }

    private void validarFestivo(final Festivo festivo) {
        this.validarEntidadesRequeridas(festivo);
        this.validarTipoFecha(festivo);
    }

    private void validarEntidadesRequeridas(final Festivo festivo) {
        this.validationUtils.validarCampoRequerido(festivo.getPais(), "país");
        this.validationUtils.validarEntidadExiste(this.paisRepository::existsById, festivo.getPais().getId(), "País");
        
        this.validationUtils.validarCampoRequerido(festivo.getTipoFestivo(), "tipo de festivo");
        this.validationUtils.validarEntidadExiste(this.tipoFestivoRepository::existsById, festivo.getTipoFestivo().getId(), "Tipo de festivo");
    }

    private void validarTipoFecha(final Festivo festivo) {
        final boolean tieneFechaFija = festivo.getDia() != null && festivo.getMes() != null;
        final boolean tieneDiasPascua = festivo.getDiasPascua() != null;
        
        if (tieneFechaFija && tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo no puede tener fecha fija y días de Pascua al mismo tiempo");
        }
        
        if (!tieneFechaFija && !tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo debe tener fecha fija o días de Pascua");
        }
        
        if (tieneFechaFija) {
            this.validarFechaFija(festivo);
        }
    }

    private void validarFechaFija(final Festivo festivo) {
        this.validationUtils.validarRango(festivo.getDia(), 1, 31, "día");
        this.validationUtils.validarRango(festivo.getMes(), 1, 12, "mes");
        
        try {
            LocalDate.of(2024, festivo.getMes(), festivo.getDia());
        } catch (final Exception e) {
            throw new IllegalArgumentException("Fecha inválida: día " + festivo.getDia() + ", mes " + festivo.getMes());
        }
    }

    // Métodos de conversión manual (reemplazan al FestivoMapper)
    private FestivoDTO convertToDTO(Festivo festivo) {
        if (festivo == null) return null;
        
        FestivoDTO dto = new FestivoDTO();
        dto.setId(festivo.getId());
        dto.setNombre(festivo.getNombre());
        dto.setDia(festivo.getDia());
        dto.setMes(festivo.getMes());
        dto.setDiasPascua(festivo.getDiasPascua());
        
        if (festivo.getPais() != null) {
            dto.setPaisId(festivo.getPais().getId());
            dto.setPaisNombre(festivo.getPais().getNombre());
        }
        
        if (festivo.getTipoFestivo() != null) {
            dto.setTipoFestivoId(festivo.getTipoFestivo().getId());
            dto.setTipoFestivoNombre(festivo.getTipoFestivo().getTipo());
        }
        
        return dto;
    }

    private Festivo convertToEntity(FestivoDTO dto) {
        if (dto == null) return null;
        
        Festivo festivo = new Festivo();
        festivo.setId(dto.getId());
        festivo.setNombre(dto.getNombre());
        festivo.setDia(dto.getDia());
        festivo.setMes(dto.getMes());
        festivo.setDiasPascua(dto.getDiasPascua());
        
        return festivo;
    }

    private List<FestivoDTO> convertToDTOList(List<Festivo> festivos) {
        if (festivos == null) return null;
        
        return festivos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

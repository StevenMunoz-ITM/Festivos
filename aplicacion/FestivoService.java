package festivos.api.aplicacion;

import festivos.api.core.IFestivoService;
import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.infraestructura.repositorios.FestivoRepository;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import festivos.api.aplicacion.mapper.FestivoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FestivoService implements IFestivoService {
    
    @Autowired
    private FestivoRepository festivoRepository;
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;
    
    @Autowired
    private FestivoMapper festivoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerTodos() {
        return convertirListaADTO(festivoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FestivoDTO> obtenerPorId(Long id) {
        return festivoRepository.findById(id)
                .map(festivoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorPais(Long paisId) {
        return convertirListaADTO(festivoRepository.findByPaisId(paisId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorFecha(Integer dia, Integer mes) {
        return convertirListaADTO(festivoRepository.findByDiaAndMes(dia, mes));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorPaisYMes(Long paisId, Integer mes) {
        return convertirListaADTO(festivoRepository.findByPaisIdAndMes(paisId, mes));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascua() {
        return convertirListaADTO(festivoRepository.findByDiasPascuaIsNotNull());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascuaPorPais(Long paisId) {
        return convertirListaADTO(festivoRepository.findByPaisIdAndDiasPascuaIsNotNull(paisId));
    }

    private List<FestivoDTO> convertirListaADTO(List<Festivo> festivos) {
        return festivos.stream()
                .map(festivoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FestivoDTO guardar(FestivoDTO festivoDTO) {
        Festivo festivo = festivoMapper.toEntity(festivoDTO);
        Festivo festivoGuardado = guardarFestivo(festivo);
        return festivoMapper.toDTO(festivoGuardado);
    }

    @Override
    public FestivoDTO actualizar(Long id, FestivoDTO festivoDTO) {
        Festivo festivoActualizado = festivoMapper.toEntity(festivoDTO);
        Festivo festivoGuardado = actualizarFestivo(id, festivoActualizado);
        return festivoMapper.toDTO(festivoGuardado);
    }

    public Festivo guardarFestivo(Festivo festivo) {
        validarFestivo(festivo);
        return festivoRepository.save(festivo);
    }

    public Festivo actualizarFestivo(Long id, Festivo festivoActualizado) {
        Festivo festivoExistente = festivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festivo no encontrado con ID: " + id));
        
        validarFestivo(festivoActualizado);
        actualizarCamposFestivo(festivoExistente, festivoActualizado);
        return festivoRepository.save(festivoExistente);
    }

    private void actualizarCamposFestivo(Festivo festivoExistente, Festivo festivoActualizado) {
        festivoExistente.setPais(festivoActualizado.getPais());
        festivoExistente.setNombre(festivoActualizado.getNombre());
        festivoExistente.setDia(festivoActualizado.getDia());
        festivoExistente.setMes(festivoActualizado.getMes());
        festivoExistente.setDiasPascua(festivoActualizado.getDiasPascua());
        festivoExistente.setTipoFestivo(festivoActualizado.getTipoFestivo());
    }

    @Override
    public void eliminar(Long id) {
        if (!festivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Festivo no encontrado con ID: " + id);
        }
        festivoRepository.deleteById(id);
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
        if (festivo.getPais() == null || !paisRepository.existsById(festivo.getPais().getId())) {
            throw new IllegalArgumentException("País inválido o no existe");
        }
        
        if (festivo.getTipoFestivo() == null || !tipoFestivoRepository.existsById(festivo.getTipoFestivo().getId())) {
            throw new IllegalArgumentException("Tipo de festivo inválido o no existe");
        }
        
        boolean tieneFechaFija = festivo.getDia() != null && festivo.getMes() != null;
        boolean tieneDiasPascua = festivo.getDiasPascua() != null;
        
        if (tieneFechaFija && tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo no puede tener fecha fija y días de Pascua al mismo tiempo");
        }
        
        if (!tieneFechaFija && !tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo debe tener fecha fija o días de Pascua");
        }
        
        if (tieneFechaFija) {
            if (festivo.getDia() < 1 || festivo.getDia() > 31) {
                throw new IllegalArgumentException("El día debe estar entre 1 y 31");
            }
            if (festivo.getMes() < 1 || festivo.getMes() > 12) {
                throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
            }
            
            try {
                LocalDate.of(2024, festivo.getMes(), festivo.getDia());
            } catch (Exception e) {
                throw new IllegalArgumentException("Fecha inválida: día " + festivo.getDia() + ", mes " + festivo.getMes());
            }
        }
    }
}

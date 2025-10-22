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
        return festivoRepository.findAll().stream()
                .map(festivoMapper::toDTO)
                .collect(Collectors.toList());
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
        return festivoRepository.findByPaisId(paisId).stream()
                .map(festivoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorFecha(Integer dia, Integer mes) {
        return festivoRepository.findByDiaAndMes(dia, mes).stream()
                .map(festivoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerPorPaisYMes(Long paisId, Integer mes) {
        return festivoRepository.findByPaisIdAndMes(paisId, mes).stream()
                .map(festivoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascua() {
        return festivoRepository.findByDiasPascuaIsNotNull().stream()
                .map(festivoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> obtenerFestivosPascuaPorPais(Long paisId) {
        return festivoRepository.findByPaisIdAndDiasPascuaIsNotNull(paisId).stream()
                .map(festivoMapper::toDTO)
                .collect(Collectors.toList());
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
        
        Festivo festivoActualizado = festivoMapper.toEntity(festivoDTO);
        validarFestivo(festivoActualizado);
        
        festivoExistente.setPais(festivoActualizado.getPais());
        festivoExistente.setNombre(festivoActualizado.getNombre());
        festivoExistente.setDia(festivoActualizado.getDia());
        festivoExistente.setMes(festivoActualizado.getMes());
        festivoExistente.setDiasPascua(festivoActualizado.getDiasPascua());
        festivoExistente.setTipoFestivo(festivoActualizado.getTipoFestivo());
        
        Festivo festivoSaved = festivoRepository.save(festivoExistente);
        return festivoMapper.toDTO(festivoSaved);
    }

    public Festivo guardar(Festivo festivo) {
        validarFestivo(festivo);
        return festivoRepository.save(festivo);
    }

    public Festivo actualizar(Long id, Festivo festivoActualizado) {
        Festivo festivoExistente = festivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festivo no encontrado con ID: " + id));
        
        validarFestivo(festivoActualizado);
        
        festivoExistente.setPais(festivoActualizado.getPais());
        festivoExistente.setNombre(festivoActualizado.getNombre());
        festivoExistente.setDia(festivoActualizado.getDia());
        festivoExistente.setMes(festivoActualizado.getMes());
        festivoExistente.setDiasPascua(festivoActualizado.getDiasPascua());
        festivoExistente.setTipoFestivo(festivoActualizado.getTipoFestivo());
        
        return festivoRepository.save(festivoExistente);
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
        // Obtener todos los festivos del país
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
        
        // Determinar la fecha base según el tipo de festivo
        if (festivo.getDia() != null && festivo.getMes() != null) {
            // Festivo de fecha fija
            fechaBase = LocalDate.of(año, festivo.getMes(), festivo.getDia());
        } else if (festivo.getDiasPascua() != null) {
            // Festivo basado en Pascua
            LocalDate pascua = calcularPascua(año);
            fechaBase = pascua.plusDays(festivo.getDiasPascua());
        } else {
            return null; // Datos incompletos
        }
        
        // Aplicar lógica de "Puente festivo" si corresponde
        String tipoFestivo = festivo.getTipoFestivo().getTipo();
        if (tipoFestivo.contains("Puente festivo")) {
            return trasladarAlSiguienteLunes(fechaBase);
        }
        
        return fechaBase;
    }

    private LocalDate trasladarAlSiguienteLunes(LocalDate fecha) {
        // Si ya es lunes, no se traslada
        if (fecha.getDayOfWeek().getValue() == 1) { // 1 = lunes
            return fecha;
        }
        
        // Calcular días hasta el siguiente lunes
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
        // Fórmula colombiana para calcular el Domingo de Ramos
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        
        // Calcular días después del 15 de marzo para el Domingo de Ramos
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        // Fecha base: 15 de marzo
        LocalDate fechaBase = LocalDate.of(año, 3, 15);
        
        // Sumar los días calculados para obtener Domingo de Ramos
        LocalDate domingoDeRamos = fechaBase.plusDays(dias);
        
        // Domingo de Pascua es 7 días después del Domingo de Ramos
        return domingoDeRamos.plusDays(7);
    }

    @Override
    public LocalDate calcularDomingoDeRamos(int año) {
        // Fórmula colombiana para calcular el Domingo de Ramos
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        
        // Calcular días después del 15 de marzo para el Domingo de Ramos
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        // Fecha base: 15 de marzo
        LocalDate fechaBase = LocalDate.of(año, 3, 15);
        
        // Sumar los días calculados para obtener Domingo de Ramos
        return fechaBase.plusDays(dias);
    }

    private void validarFestivo(Festivo festivo) {
        if (festivo.getPais() == null || !paisRepository.existsById(festivo.getPais().getId())) {
            throw new IllegalArgumentException("País inválido o no existe");
        }
        
        if (festivo.getTipoFestivo() == null || !tipoFestivoRepository.existsById(festivo.getTipoFestivo().getId())) {
            throw new IllegalArgumentException("Tipo de festivo inválido o no existe");
        }
        
        // Validar que tenga fecha fija O días de Pascua, pero no ambos
        boolean tieneFechaFija = festivo.getDia() != null && festivo.getMes() != null;
        boolean tieneDiasPascua = festivo.getDiasPascua() != null;
        
        if (tieneFechaFija && tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo no puede tener fecha fija y días de Pascua al mismo tiempo");
        }
        
        if (!tieneFechaFija && !tieneDiasPascua) {
            throw new IllegalArgumentException("Un festivo debe tener fecha fija o días de Pascua");
        }
        
        // Validar fecha fija
        if (tieneFechaFija) {
            if (festivo.getDia() < 1 || festivo.getDia() > 31) {
                throw new IllegalArgumentException("El día debe estar entre 1 y 31");
            }
            if (festivo.getMes() < 1 || festivo.getMes() > 12) {
                throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
            }
            
            // Validar que la fecha sea válida
            try {
                LocalDate.of(2024, festivo.getMes(), festivo.getDia()); // Año bisiest para validar
            } catch (Exception e) {
                throw new IllegalArgumentException("Fecha inválida: día " + festivo.getDia() + ", mes " + festivo.getMes());
            }
        }
    }
}
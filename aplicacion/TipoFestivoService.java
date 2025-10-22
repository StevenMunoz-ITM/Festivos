package festivos.api.aplicacion;

import festivos.api.core.ITipoFestivoService;
import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import festivos.api.aplicacion.mapper.TipoFestivoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TipoFestivoService implements ITipoFestivoService {
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;
    
    @Autowired
    private TipoFestivoMapper tipoFestivoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TipoFestivoDTO> obtenerTodos() {
        return tipoFestivoRepository.findAll().stream()
                .map(tipoFestivoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFestivoDTO> obtenerPorId(Long id) {
        return tipoFestivoRepository.findById(id)
                .map(tipoFestivoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFestivoDTO> obtenerPorTipo(String tipo) {
        return tipoFestivoRepository.findByTipoIgnoreCase(tipo)
                .map(tipoFestivoMapper::toDTO);
    }

    @Override
    public TipoFestivoDTO guardar(TipoFestivoDTO tipoFestivoDTO) {
        if (tipoFestivoRepository.existsByTipoIgnoreCase(tipoFestivoDTO.getTipo())) {
            throw new IllegalArgumentException("Ya existe un tipo de festivo con el tipo: " + tipoFestivoDTO.getTipo());
        }
        
        TipoFestivo tipoFestivo = tipoFestivoMapper.toEntity(tipoFestivoDTO);
        TipoFestivo tipoFestivoGuardado = tipoFestivoRepository.save(tipoFestivo);
        return tipoFestivoMapper.toDTO(tipoFestivoGuardado);
    }

    @Override
    public TipoFestivoDTO actualizar(Long id, TipoFestivoDTO tipoFestivoDTO) {
        TipoFestivo tipoFestivoExistente = tipoFestivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de festivo no encontrado con ID: " + id));
        
        // Verificar si el nuevo tipo ya existe en otro registro
        if (!tipoFestivoExistente.getTipo().equalsIgnoreCase(tipoFestivoDTO.getTipo()) &&
            tipoFestivoRepository.existsByTipoIgnoreCase(tipoFestivoDTO.getTipo())) {
            throw new IllegalArgumentException("Ya existe un tipo de festivo con el tipo: " + tipoFestivoDTO.getTipo());
        }
        
        tipoFestivoExistente.setTipo(tipoFestivoDTO.getTipo());
        TipoFestivo tipoFestivoActualizado = tipoFestivoRepository.save(tipoFestivoExistente);
        return tipoFestivoMapper.toDTO(tipoFestivoActualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!tipoFestivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Tipo de festivo no encontrado con ID: " + id);
        }
        tipoFestivoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existe(Long id) {
        return tipoFestivoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorTipo(String tipo) {
        return tipoFestivoRepository.existsByTipoIgnoreCase(tipo);
    }
    
    // Métodos adicionales para compatibilidad con controladores que necesiten entidades
    
    @Transactional(readOnly = true)
    public Optional<TipoFestivo> obtenerEntidadPorId(Long id) {
        return tipoFestivoRepository.findById(id);
    }
}
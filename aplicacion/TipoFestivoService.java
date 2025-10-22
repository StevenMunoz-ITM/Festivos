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
    
    /**
     * Obtiene todos los tipos de festivos
     * @return Lista de todos los tipos de festivos como DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoFestivoDTO> obtenerTodos() {
        return tipoFestivoRepository.findAll().stream()
                .map(tipoFestivoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un tipo de festivo por su ID
     * @param id ID del tipo de festivo
     * @return Tipo de festivo encontrado como DTO
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFestivoDTO> obtenerPorId(Long id) {
        return tipoFestivoRepository.findById(id)
                .map(tipoFestivoMapper::toDTO);
    }
    
    /**
     * Obtiene un tipo de festivo por su tipo
     * @param tipo Tipo de festivo
     * @return Tipo de festivo encontrado como DTO
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFestivoDTO> obtenerPorTipo(String tipo) {
        return tipoFestivoRepository.findByTipoIgnoreCase(tipo)
                .map(tipoFestivoMapper::toDTO);
    }
    
    /**
     * Guarda un nuevo tipo de festivo
     * @param tipoFestivoDTO DTO del tipo de festivo a guardar
     * @return Tipo de festivo guardado como DTO
     * @throws IllegalArgumentException si ya existe un tipo de festivo con el mismo tipo
     */
    @Override
    public TipoFestivoDTO guardar(TipoFestivoDTO tipoFestivoDTO) {
        if (tipoFestivoRepository.existsByTipoIgnoreCase(tipoFestivoDTO.getTipo())) {
            throw new IllegalArgumentException("Ya existe un tipo de festivo con el tipo: " + tipoFestivoDTO.getTipo());
        }
        
        TipoFestivo tipoFestivo = tipoFestivoMapper.toEntity(tipoFestivoDTO);
        TipoFestivo tipoFestivoGuardado = tipoFestivoRepository.save(tipoFestivo);
        return tipoFestivoMapper.toDTO(tipoFestivoGuardado);
    }
    
    /**
     * Actualiza un tipo de festivo existente
     * @param id ID del tipo de festivo a actualizar
     * @param tipoFestivoDTO DTO con los datos actualizados del tipo de festivo
     * @return Tipo de festivo actualizado como DTO
     * @throws IllegalArgumentException si el tipo de festivo no existe o ya existe otro con el mismo tipo
     */
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
    
    /**
     * Elimina un tipo de festivo por su ID
     * @param id ID del tipo de festivo a eliminar
     * @throws IllegalArgumentException si el tipo de festivo no existe
     */
    @Override
    public void eliminar(Long id) {
        if (!tipoFestivoRepository.existsById(id)) {
            throw new IllegalArgumentException("Tipo de festivo no encontrado con ID: " + id);
        }
        tipoFestivoRepository.deleteById(id);
    }
    
    /**
     * Verifica si existe un tipo de festivo con el ID especificado
     * @param id ID del tipo de festivo
     * @return true si existe, false si no
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existe(Long id) {
        return tipoFestivoRepository.existsById(id);
    }
    
    /**
     * Verifica si existe un tipo de festivo con el tipo especificado
     * @param tipo Tipo de festivo
     * @return true si existe, false si no
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existePorTipo(String tipo) {
        return tipoFestivoRepository.existsByTipoIgnoreCase(tipo);
    }
    
    // Métodos adicionales para compatibilidad con controladores que necesiten entidades
    /**
     * Obtiene un tipo de festivo entidad por su ID (para uso interno)
     * @param id ID del tipo de festivo
     * @return Tipo de festivo como entidad
     */
    @Transactional(readOnly = true)
    public Optional<TipoFestivo> obtenerEntidadPorId(Long id) {
        return tipoFestivoRepository.findById(id);
    }
}
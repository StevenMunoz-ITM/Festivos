package festivos.api.aplicacion;

import festivos.api.core.IPaisService;
import festivos.api.dominio.dto.PaisDTO;
import festivos.api.dominio.entidades.Pais;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.aplicacion.mapper.PaisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaisService implements IPaisService {
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private PaisMapper paisMapper;
    
    /**
     * Obtiene todos los países
     * @return Lista de todos los países como DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaisDTO> obtenerTodos() {
        return paisRepository.findAll().stream()
                .map(paisMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un país por su ID
     * @param id ID del país
     * @return País encontrado como DTO
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> obtenerPorId(Long id) {
        return paisRepository.findById(id)
                .map(paisMapper::toDTO);
    }
    
    /**
     * Obtiene un país por su nombre
     * @param nombre Nombre del país
     * @return País encontrado como DTO
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> obtenerPorNombre(String nombre) {
        return paisRepository.findByNombreIgnoreCase(nombre)
                .map(paisMapper::toDTO);
    }
    
    /**
     * Guarda un nuevo país
     * @param paisDTO DTO del país a guardar
     * @return País guardado como DTO
     * @throws IllegalArgumentException si ya existe un país con el mismo nombre
     */
    @Override
    public PaisDTO guardar(PaisDTO paisDTO) {
        if (paisRepository.existsByNombreIgnoreCase(paisDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un país con el nombre: " + paisDTO.getNombre());
        }
        
        Pais pais = paisMapper.toEntity(paisDTO);
        Pais paisGuardado = paisRepository.save(pais);
        return paisMapper.toDTO(paisGuardado);
    }
    
    /**
     * Actualiza un país existente
     * @param id ID del país a actualizar
     * @param paisDTO DTO con los datos actualizados del país
     * @return País actualizado como DTO
     * @throws IllegalArgumentException si el país no existe o ya existe otro con el mismo nombre
     */
    @Override
    public PaisDTO actualizar(Long id, PaisDTO paisDTO) {
        Pais paisExistente = paisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("País no encontrado con ID: " + id));
        
        // Verificar si el nuevo nombre ya existe en otro país
        if (!paisExistente.getNombre().equalsIgnoreCase(paisDTO.getNombre()) &&
            paisRepository.existsByNombreIgnoreCase(paisDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un país con el nombre: " + paisDTO.getNombre());
        }
        
        paisExistente.setNombre(paisDTO.getNombre());
        Pais paisActualizado = paisRepository.save(paisExistente);
        return paisMapper.toDTO(paisActualizado);
    }
    
    /**
     * Elimina un país por su ID
     * @param id ID del país a eliminar
     * @throws IllegalArgumentException si el país no existe
     */
    @Override
    public void eliminar(Long id) {
        if (!paisRepository.existsById(id)) {
            throw new IllegalArgumentException("País no encontrado con ID: " + id);
        }
        paisRepository.deleteById(id);
    }
    
    /**
     * Verifica si existe un país con el ID especificado
     * @param id ID del país
     * @return true si existe, false si no
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existe(Long id) {
        return paisRepository.existsById(id);
    }
    
    /**
     * Verifica si existe un país con el nombre especificado
     * @param nombre Nombre del país
     * @return true si existe, false si no
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existePorNombre(String nombre) {
        return paisRepository.existsByNombreIgnoreCase(nombre);
    }
    
    // Métodos adicionales para compatibilidad con controladores que necesiten entidades
    /**
     * Obtiene un país entidad por su ID (para uso interno)
     * @param id ID del país
     * @return País como entidad
     */
    @Transactional(readOnly = true)
    public Optional<Pais> obtenerEntidadPorId(Long id) {
        return paisRepository.findById(id);
    }
}
package festivos.api.core;

import festivos.api.dominio.dto.PaisDTO;
import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de países (Core/Dominio)
 */
public interface IPaisService {
    
    /**
     * Obtiene todos los países
     */
    List<PaisDTO> obtenerTodos();
    
    /**
     * Obtiene un país por su ID
     */
    Optional<PaisDTO> obtenerPorId(Long id);
    
    /**
     * Obtiene un país por su nombre
     */
    Optional<PaisDTO> obtenerPorNombre(String nombre);
    
    /**
     * Guarda un nuevo país
     */
    PaisDTO guardar(PaisDTO paisDTO);
    
    /**
     * Actualiza un país existente
     */
    PaisDTO actualizar(Long id, PaisDTO paisDTO);
    
    /**
     * Elimina un país por su ID
     */
    void eliminar(Long id);
    
    /**
     * Verifica si existe un país con el ID especificado
     */
    boolean existe(Long id);
    
    /**
     * Verifica si existe un país con el nombre especificado
     */
    boolean existePorNombre(String nombre);
}
package festivos.api.core;

import festivos.api.dominio.dto.TipoFestivoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de tipos de festivos (Core/Dominio)
 */
public interface ITipoFestivoService {
    
    /**
     * Obtiene todos los tipos de festivos
     */
    List<TipoFestivoDTO> obtenerTodos();
    
    /**
     * Obtiene un tipo de festivo por su ID
     */
    Optional<TipoFestivoDTO> obtenerPorId(Long id);
    
    /**
     * Obtiene un tipo de festivo por su tipo
     */
    Optional<TipoFestivoDTO> obtenerPorTipo(String tipo);
    
    /**
     * Guarda un nuevo tipo de festivo
     */
    TipoFestivoDTO guardar(TipoFestivoDTO tipoFestivoDTO);
    
    /**
     * Actualiza un tipo de festivo existente
     */
    TipoFestivoDTO actualizar(Long id, TipoFestivoDTO tipoFestivoDTO);
    
    /**
     * Elimina un tipo de festivo por su ID
     */
    void eliminar(Long id);
    
    /**
     * Verifica si existe un tipo de festivo con el ID especificado
     */
    boolean existe(Long id);
    
    /**
     * Verifica si existe un tipo de festivo con el tipo especificado
     */
    boolean existePorTipo(String tipo);
}
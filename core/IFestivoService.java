package festivos.api.core;

import festivos.api.dominio.dto.FestivoDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de festivos (Core/Dominio)
 */
public interface IFestivoService {
    
    /**
     * Obtiene todos los festivos
     */
    List<FestivoDTO> obtenerTodos();
    
    /**
     * Obtiene un festivo por su ID
     */
    Optional<FestivoDTO> obtenerPorId(Long id);
    
    /**
     * Obtiene todos los festivos de un país específico
     */
    List<FestivoDTO> obtenerPorPais(Long paisId);
    
    /**
     * Obtiene festivos por día y mes específicos
     */
    List<FestivoDTO> obtenerPorFecha(Integer dia, Integer mes);
    
    /**
     * Obtiene festivos de un país en un mes específico
     */
    List<FestivoDTO> obtenerPorPaisYMes(Long paisId, Integer mes);
    
    /**
     * Obtiene festivos que se calculan en base a Pascua
     */
    List<FestivoDTO> obtenerFestivosPascua();
    
    /**
     * Obtiene festivos de un país que se calculan en base a Pascua
     */
    List<FestivoDTO> obtenerFestivosPascuaPorPais(Long paisId);
    
    /**
     * Guarda un nuevo festivo
     */
    FestivoDTO guardar(FestivoDTO festivoDTO);
    
    /**
     * Actualiza un festivo existente
     */
    FestivoDTO actualizar(Long id, FestivoDTO festivoDTO);
    
    /**
     * Elimina un festivo por su ID
     */
    void eliminar(Long id);
    
    /**
     * Verifica si una fecha es festivo en un país específico
     */
    boolean esFestivo(LocalDate fecha, Long paisId);
    
    /**
     * Calcula la fecha del Domingo de Ramos para un año específico
     */
    LocalDate calcularDomingoDeRamos(int año);
    
    /**
     * Obtiene la fecha real de celebración de un festivo para un año específico
     */
    LocalDate obtenerFechaCelebracion(Long festivoId, int año);
}
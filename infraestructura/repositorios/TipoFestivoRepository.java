package festivos.api.infraestructura.repositorios;

import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoFestivoRepository extends JpaRepository<TipoFestivo, Long> {
    
    /**
     * Busca un tipo de festivo por su tipo (ignorando mayúsculas/minúsculas)
     * @param tipo Tipo de festivo
     * @return Tipo de festivo encontrado
     */
    @Query("SELECT tf FROM TipoFestivo tf WHERE LOWER(tf.tipo) = LOWER(:tipo)")
    Optional<TipoFestivo> findByTipoIgnoreCase(@Param("tipo") String tipo);
    
    /**
     * Verifica si existe un tipo de festivo con el tipo especificado
     * @param tipo Tipo de festivo
     * @return true si existe, false si no
     */
    boolean existsByTipoIgnoreCase(String tipo);
}
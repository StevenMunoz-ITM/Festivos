package festivos.api.infraestructura.repositorios;

import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoFestivoRepository extends JpaRepository<TipoFestivo, Long> {
    
    @Query("SELECT tf FROM TipoFestivo tf WHERE LOWER(tf.tipo) = LOWER(:tipo)")
    Optional<TipoFestivo> findByTipoIgnoreCase(@Param("tipo") String tipo);
    
    boolean existsByTipoIgnoreCase(String tipo);
}
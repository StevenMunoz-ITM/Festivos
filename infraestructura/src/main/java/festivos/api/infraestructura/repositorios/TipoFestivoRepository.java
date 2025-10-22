package festivos.api.infraestructura.repositorios;

import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoFestivoRepository extends JpaRepository<TipoFestivo, Long> {
    
    Optional<TipoFestivo> findByTipoIgnoreCase(String tipo);
    List<TipoFestivo> findByTipoContainingIgnoreCase(String tipo);
    List<TipoFestivo> findByTipoStartingWithIgnoreCase(String prefijo);
    boolean existsByTipoIgnoreCase(String tipo);
    long countByTipoContainingIgnoreCase(String tipo);
}

package festivos.api.infraestructura.repositorios;

import festivos.api.dominio.entidades.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    
    Optional<Pais> findByNombreIgnoreCase(String nombre);
    List<Pais> findByNombreContainingIgnoreCase(String nombre);
    List<Pais> findByNombreStartingWithIgnoreCase(String prefijo);
    boolean existsByNombreIgnoreCase(String nombre);
    long countByNombreContainingIgnoreCase(String nombre);
}

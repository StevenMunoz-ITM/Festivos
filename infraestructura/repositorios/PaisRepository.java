package festivos.api.infraestructura.repositorios;

import festivos.api.dominio.entidades.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    
    /**
     * Busca un país por su nombre (ignorando mayúsculas/minúsculas)
     * @param nombre Nombre del país
     * @return País encontrado
     */
    @Query("SELECT p FROM Pais p WHERE LOWER(p.nombre) = LOWER(:nombre)")
    Optional<Pais> findByNombreIgnoreCase(@Param("nombre") String nombre);
    
    /**
     * Verifica si existe un país con el nombre especificado
     * @param nombre Nombre del país
     * @return true si existe, false si no
     */
    boolean existsByNombreIgnoreCase(String nombre);
}
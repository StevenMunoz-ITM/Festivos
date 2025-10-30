package festivos.api.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import festivos.api.dominio.entidades.Pais;
import java.util.List;

@Repository
public interface IPaisRepositorio extends JpaRepository<Pais, Integer> {

    @Query("SELECT p FROM Pais p WHERE p.nombre LIKE %:nombre% ORDER BY p.nombre ASC")
    public List<Pais> buscar(String nombre);

}
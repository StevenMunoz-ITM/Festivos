package festivos.api.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import festivos.api.dominio.entidades.Festivo;
import java.util.List;

@Repository
public interface IFestivoRepositorio extends JpaRepository<Festivo, Integer> {

    @Query("SELECT f FROM Festivo f WHERE f.nombre LIKE %:nombre% ORDER BY f.nombre ASC")
    public List<Festivo> buscar(String nombre);

    @Query("SELECT f FROM Festivo f WHERE f.pais.id = :idPais ORDER BY f.mes ASC, f.dia ASC")
    public List<Festivo> listarPorPais(int idPais);

    @Query("SELECT f FROM Festivo f WHERE f.tipo.id = :idTipo ORDER BY f.nombre ASC")
    public List<Festivo> listarPorTipo(int idTipo);

}
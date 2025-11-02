package festivos.api.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import festivos.api.dominio.entidades.Festivo;
import java.util.List;

@Repository
public interface IFestivoRepositorio extends JpaRepository<Festivo, Integer> {
    
    @Query("SELECT f FROM Festivo f WHERE f.nombre LIKE '%' || ?1 || '%' ORDER BY f.nombre ASC")
    List<Festivo> buscar(String nombre);
    
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = ?1 ORDER BY f.mes ASC, f.dia ASC")
    List<Festivo> listarPorPais(int idPais);
    
    @Query("SELECT f FROM Festivo f WHERE f.tipo.id = ?1 ORDER BY f.nombre ASC")
    List<Festivo> listarPorTipo(int idTipo);
    
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = ?1 AND f.mes = ?2 ORDER BY f.dia ASC")
    List<Festivo> listarPorPaisYMes(int idPais, int mes);
    
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = ?1 AND f.dia = ?2 AND f.mes = ?3")
    List<Festivo> buscarPorFecha(int idPais, int dia, int mes);
}
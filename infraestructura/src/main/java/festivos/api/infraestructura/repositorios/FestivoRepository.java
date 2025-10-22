package festivos.api.infraestructura.repositorios;

import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Long> {
    
    List<Festivo> findByPaisId(Long paisId);
    List<Festivo> findByTipoFestivo(TipoFestivo tipoFestivo);
    List<Festivo> findByDiaAndMes(Integer dia, Integer mes);
    List<Festivo> findByPaisIdAndMes(Long paisId, Integer mes);
    List<Festivo> findByMes(Integer mes);
    List<Festivo> findByDiasPascuaIsNotNull();
    List<Festivo> findByPaisIdAndDiasPascuaIsNotNull(Long paisId);
    List<Festivo> findByDiasPascua(Integer diasPascua);
    List<Festivo> findByPaisIdAndTipoFestivoId(Long paisId, Long tipoFestivoId);
    List<Festivo> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByPaisIdAndNombreIgnoreCase(Long paisId, String nombre);
    boolean existsByDiaAndMes(Integer dia, Integer mes);
}

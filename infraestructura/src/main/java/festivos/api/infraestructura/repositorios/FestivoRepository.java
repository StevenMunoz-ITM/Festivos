package festivos.api.infraestructura.repositorios;

import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Long> {
    
    List<Festivo> findByPais(Pais pais);
    
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = :paisId")
    List<Festivo> findByPaisId(@Param("paisId") Long paisId);
    
    List<Festivo> findByTipoFestivo(TipoFestivo tipoFestivo);
    
    @Query("SELECT f FROM Festivo f WHERE f.dia = :dia AND f.mes = :mes")
    List<Festivo> findByDiaAndMes(@Param("dia") Integer dia, @Param("mes") Integer mes);
    
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = :paisId AND f.mes = :mes")
    List<Festivo> findByPaisIdAndMes(@Param("paisId") Long paisId, @Param("mes") Integer mes);
    
    @Query("SELECT f FROM Festivo f WHERE f.diasPascua IS NOT NULL")
    List<Festivo> findByDiasPascuaIsNotNull();
    
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = :paisId AND f.diasPascua IS NOT NULL")
    List<Festivo> findByPaisIdAndDiasPascuaIsNotNull(@Param("paisId") Long paisId);
}

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
    
    /**
     * Busca todos los festivos de un país específico
     * @param pais País del cual buscar festivos
     * @return Lista de festivos del país
     */
    List<Festivo> findByPais(Pais pais);
    
    /**
     * Busca todos los festivos de un país específico por ID
     * @param paisId ID del país
     * @return Lista de festivos del país
     */
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = :paisId")
    List<Festivo> findByPaisId(@Param("paisId") Long paisId);
    
    /**
     * Busca todos los festivos de un tipo específico
     * @param tipoFestivo Tipo de festivo
     * @return Lista de festivos del tipo especificado
     */
    List<Festivo> findByTipoFestivo(TipoFestivo tipoFestivo);
    
    /**
     * Busca festivos por día y mes específicos
     * @param dia Día del festivo
     * @param mes Mes del festivo
     * @return Lista de festivos en la fecha especificada
     */
    @Query("SELECT f FROM Festivo f WHERE f.dia = :dia AND f.mes = :mes")
    List<Festivo> findByDiaAndMes(@Param("dia") Integer dia, @Param("mes") Integer mes);
    
    /**
     * Busca festivos de un país en un mes específico
     * @param paisId ID del país
     * @param mes Mes del festivo
     * @return Lista de festivos del país en el mes especificado
     */
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = :paisId AND f.mes = :mes")
    List<Festivo> findByPaisIdAndMes(@Param("paisId") Long paisId, @Param("mes") Integer mes);
    
    /**
     * Busca festivos que se calculan en base a Pascua
     * @return Lista de festivos basados en Pascua
     */
    @Query("SELECT f FROM Festivo f WHERE f.diasPascua IS NOT NULL")
    List<Festivo> findByDiasPascuaIsNotNull();
    
    /**
     * Busca festivos de un país que se calculan en base a Pascua
     * @param paisId ID del país
     * @return Lista de festivos del país basados en Pascua
     */
    @Query("SELECT f FROM Festivo f WHERE f.pais.id = :paisId AND f.diasPascua IS NOT NULL")
    List<Festivo> findByPaisIdAndDiasPascuaIsNotNull(@Param("paisId") Long paisId);
}
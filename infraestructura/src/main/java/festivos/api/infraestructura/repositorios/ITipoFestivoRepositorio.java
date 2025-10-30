package festivos.api.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import festivos.api.dominio.entidades.TipoFestivo;
import java.util.List;

@Repository
public interface ITipoFestivoRepositorio extends JpaRepository<TipoFestivo, Integer> {

    @Query("SELECT t FROM TipoFestivo t WHERE t.tipo LIKE %:tipo% ORDER BY t.tipo ASC")
    public List<TipoFestivo> buscar(String tipo);

}
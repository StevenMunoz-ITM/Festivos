package festivos.api.core.servicios;

import java.util.List;
import festivos.api.dominio.entidades.TipoFestivo;

public interface ITipoFestivoServicio {

    public List<TipoFestivo> listar();

    public TipoFestivo obtener(int id);

    public List<TipoFestivo> buscar(String tipo);

    public TipoFestivo agregar(TipoFestivo tipoFestivo);

    public TipoFestivo modificar(TipoFestivo tipoFestivo);

    public boolean eliminar(int id);

}
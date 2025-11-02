package festivos.api.core.servicios;

import festivos.api.dominio.entidades.TipoFestivo;
import java.util.List;

public interface ITipoFestivoServicio {
    
    List<TipoFestivo> listar();
    
    TipoFestivo obtener(int id);
    
    List<TipoFestivo> buscar(String tipo);
    
    TipoFestivo agregar(TipoFestivo tipoFestivo);
    
    TipoFestivo modificar(TipoFestivo tipoFestivo);
    
    boolean eliminar(int id);
}
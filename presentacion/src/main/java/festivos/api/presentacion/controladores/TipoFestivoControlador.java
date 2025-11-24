package festivos.api.presentacion.controladores;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import festivos.api.core.servicios.ITipoFestivoServicio;
import festivos.api.dominio.entidades.TipoFestivo;

@RestController
@RequestMapping("/api/tipos-festivos")
@CrossOrigin(origins = "*")
public class TipoFestivoControlador {
    
    private final ITipoFestivoServicio servicio;
    
    public TipoFestivoControlador(ITipoFestivoServicio servicio) {
        this.servicio = servicio;
    }
    
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<TipoFestivo> listar() {
        return servicio.listar();
    }
    
    @RequestMapping(value = "/obtener/{id}", method = RequestMethod.GET)
    public TipoFestivo obtener(@PathVariable int id) {
        return servicio.obtener(id);
    }
    
    @RequestMapping(value = "/buscar/{tipo}", method = RequestMethod.GET)
    public List<TipoFestivo> buscar(@PathVariable String tipo) {
        return servicio.buscar(tipo);
    }
    
    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public TipoFestivo agregar(@RequestBody TipoFestivo tipoFestivo) {
        return servicio.agregar(tipoFestivo);
    }
    
    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public TipoFestivo modificar(@RequestBody TipoFestivo tipoFestivo) {
        return servicio.modificar(tipoFestivo);
    }
    
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
    public boolean eliminar(@PathVariable int id) {
        return servicio.eliminar(id);
    }
}
package festivos.api.presentacion.controladores;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import festivos.api.core.servicios.IFestivoServicio;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.dtos.FestivoDto;
import festivos.api.dominio.dtos.ValidacionFestivoDto;

@RestController
@RequestMapping("/api/festivos")
@CrossOrigin(origins = "*")
public class FestivoControlador {
    
    private static final Logger log = LoggerFactory.getLogger(FestivoControlador.class);
    private final IFestivoServicio servicio;
    
    public FestivoControlador(IFestivoServicio servicio) {
        this.servicio = servicio;
    }
    
    private String manejarErrorFestivo(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return "Fecha no válida";
        }
        return "Error en la consulta";
    }
    
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Festivo> listar() {
        return servicio.listar();
    }
    
    @RequestMapping(value = "/obtener/{id}", method = RequestMethod.GET)
    public Festivo obtener(@PathVariable int id) {
        return servicio.obtener(id);
    }
    
    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public Festivo agregar(@RequestBody Festivo festivo) {
        return servicio.agregar(festivo);
    }
    
    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public Festivo modificar(@RequestBody Festivo festivo) {
        return servicio.modificar(festivo);
    }
    
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
    public boolean eliminar(@PathVariable int id) {
        return servicio.eliminar(id);
    }
    
    @RequestMapping(value = "/esfestivo/{idPais}/{dia}/{mes}/{anio}", method = RequestMethod.GET)
    public String esFestivo(@PathVariable int idPais, @PathVariable int dia, 
                            @PathVariable int mes, @PathVariable int anio) {
        try {
            boolean esFestivo = servicio.esFestivo(idPais, dia, mes, anio);
            return esFestivo ? "Es festivo" : "No es festivo";
        } catch (Exception e) {
            return manejarErrorFestivo(e);
        }
    }
    

    
    @RequestMapping(value = "/validar/{idPais}/{dia}/{mes}/{anio}", method = RequestMethod.GET)
    public ValidacionFestivoDto validarFestivo(@PathVariable int idPais, @PathVariable int dia, 
                                             @PathVariable int mes, @PathVariable int anio) {
        try {
            return servicio.validarFestivo(idPais, dia, mes, anio);
        } catch (Exception e) {
            String mensaje = manejarErrorFestivo(e);
            return new ValidacionFestivoDto(false, mensaje + ": " + e.getMessage());
        }
    }
    
    @RequestMapping(value = "/listarporanio/{idPais}/{anio}", method = RequestMethod.GET)
    public List<FestivoDto> listarFestivosPorAnio(@PathVariable int idPais, @PathVariable int anio) {
        return servicio.listarFestivosPorAnio(idPais, anio);
    }
}
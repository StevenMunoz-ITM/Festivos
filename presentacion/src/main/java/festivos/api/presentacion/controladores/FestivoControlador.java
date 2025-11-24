package festivos.api.presentacion.controladores;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import festivos.api.core.servicios.IFestivoServicio;
import festivos.api.core.servicios.IPaisServicio;
import festivos.api.core.servicios.ITipoFestivoServicio;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.dominio.dtos.FestivoDto;
import festivos.api.dominio.dtos.FestivoEntradaDto;
import festivos.api.dominio.dtos.ValidacionFestivoDto;

@RestController
@RequestMapping("/api/festivos")
@CrossOrigin(origins = "*")
public class FestivoControlador {
    
    private static final Logger log = LoggerFactory.getLogger(FestivoControlador.class);
    private final IFestivoServicio servicio;
    private final IPaisServicio paisServicio;
    private final ITipoFestivoServicio tipoServicio;
    
    public FestivoControlador(IFestivoServicio servicio, IPaisServicio paisServicio, 
                             ITipoFestivoServicio tipoServicio) {
        this.servicio = servicio;
        this.paisServicio = paisServicio;
        this.tipoServicio = tipoServicio;
    }
    
    private Festivo convertirDtoAEntidad(FestivoEntradaDto dto) {
        Festivo festivo = new Festivo();
        festivo.setId(dto.getId());
        festivo.setNombre(dto.getNombre());
        festivo.setDia(dto.getDia());
        festivo.setMes(dto.getMes());
        festivo.setDiasPascua(dto.getDiasPascua());
        
        Pais pais = paisServicio.obtener(dto.getPaisId());
        festivo.setPais(pais);
        
        TipoFestivo tipo = tipoServicio.obtener(dto.getTipoFestivoId());
        festivo.setTipo(tipo);
        
        return festivo;
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
    
    // GET /api/festivos/buscar?nombre=texto - Buscar por nombre
    @GetMapping("/buscar")
    public List<Festivo> buscar(@RequestParam String nombre) {
        return servicio.buscar(nombre);
    }
    
    // GET /api/festivos/pais/{paisId} - Obtener por país
    @GetMapping("/pais/{paisId}")
    public List<Festivo> obtenerPorPais(@PathVariable int paisId) {
        return servicio.listarPorPais(paisId);
    }
    
    // GET /api/festivos/es-festivo?fecha=YYYY-MM-DD&paisId=1
    @GetMapping("/es-festivo")
    public ResponseEntity<String> verificarFecha(@RequestParam String fecha, @RequestParam int paisId) {
        try {
            String[] partes = fecha.split("-");
            int anio = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int dia = Integer.parseInt(partes[2]);
            
            boolean esFestivo = servicio.esFestivo(paisId, dia, mes, anio);
            return ResponseEntity.ok(esFestivo ? "Es festivo" : "No es festivo");
        } catch (Exception e) {
            log.error("Error al verificar fecha: {}", e.getMessage());
            return ResponseEntity.ok("Error al verificar la fecha");
        }
    }
    
    @RequestMapping(value = "/obtener/{id}", method = RequestMethod.GET)
    public Festivo obtener(@PathVariable int id) {
        return servicio.obtener(id);
    }
    
    // POST /api/festivos - Agregar nuevo festivo
    @PostMapping
    public ResponseEntity<Festivo> agregar(@RequestBody FestivoEntradaDto festivoDto) {
        try {
            Festivo festivo = convertirDtoAEntidad(festivoDto);
            Festivo nuevoFestivo = servicio.agregar(festivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoFestivo);
        } catch (Exception e) {
            log.error("Error al agregar festivo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // PUT /api/festivos/{id} - Modificar festivo existente
    @PutMapping("/{id}")
    public ResponseEntity<Festivo> modificar(@PathVariable int id, @RequestBody FestivoEntradaDto festivoDto) {
        try {
            festivoDto.setId(id);
            Festivo festivo = convertirDtoAEntidad(festivoDto);
            Festivo festivoModificado = servicio.modificar(festivo);
            return ResponseEntity.ok(festivoModificado);
        } catch (Exception e) {
            log.error("Error al modificar festivo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // DELETE /api/festivos/{id} - Eliminar festivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            boolean eliminado = servicio.eliminar(id);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al eliminar festivo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Mantener endpoints anteriores para compatibilidad
    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public Festivo agregarLegacy(@RequestBody Festivo festivo) {
        return servicio.agregar(festivo);
    }
    
    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public Festivo modificarLegacy(@RequestBody Festivo festivo) {
        return servicio.modificar(festivo);
    }
    
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
    public boolean eliminarLegacy(@PathVariable int id) {
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
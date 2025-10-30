package festivos.api.presentacion.controladores;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.dominio.dtos.TipoFestivoDto;
import festivos.api.aplicacion.servicios.TipoFestivoServicio;
import festivos.api.core.servicios.ITipoFestivoServicio;

@RestController
@RequestMapping("/api/tipos")
@CrossOrigin(origins = "*")
public class TipoFestivoControlador {

    private final ITipoFestivoServicio servicio;
    private final TipoFestivoServicio tipoFestivoServicio;

    public TipoFestivoControlador(ITipoFestivoServicio servicio, TipoFestivoServicio tipoFestivoServicio) {
        this.servicio = servicio;
        this.tipoFestivoServicio = tipoFestivoServicio;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TipoFestivo>> listar() {
        List<TipoFestivo> tipos = servicio.listar();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<TipoFestivo> obtener(@PathVariable int id) {
        TipoFestivo tipo = servicio.obtener(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipo);
    }

    @GetMapping("/buscar/{tipo}")
    public ResponseEntity<List<TipoFestivo>> buscar(@PathVariable String tipo) {
        List<TipoFestivo> tipos = servicio.buscar(tipo);
        return ResponseEntity.ok(tipos);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TipoFestivo> agregar(@RequestBody TipoFestivo tipoFestivo) {
        try {
            TipoFestivo nuevoTipo = servicio.agregar(tipoFestivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTipo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<TipoFestivo> modificar(@RequestBody TipoFestivo tipoFestivo) {
        try {
            TipoFestivo tipoModificado = servicio.modificar(tipoFestivo);
            if (tipoModificado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tipoModificado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable int id) {
        boolean eliminado = servicio.eliminar(id);
        return ResponseEntity.ok(eliminado);
    }
    
    @GetMapping("/dto/listar")
    public ResponseEntity<List<TipoFestivoDto>> listarDto() {
        List<TipoFestivoDto> tipos = tipoFestivoServicio.listarDto();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/dto/obtener/{id}")
    public ResponseEntity<TipoFestivoDto> obtenerDto(@PathVariable int id) {
        TipoFestivoDto tipo = tipoFestivoServicio.obtenerDto(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipo);
    }

    @GetMapping("/dto/buscar/{tipo}")
    public ResponseEntity<List<TipoFestivoDto>> buscarDto(@PathVariable String tipo) {
        List<TipoFestivoDto> tipos = tipoFestivoServicio.buscarDto(tipo);
        return ResponseEntity.ok(tipos);
    }
}
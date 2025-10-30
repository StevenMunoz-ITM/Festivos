package festivos.api.presentacion.controladores;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.dtos.PaisDto;
import festivos.api.aplicacion.servicios.PaisServicio;
import festivos.api.core.servicios.IPaisServicio;

@RestController
@RequestMapping("/api/paises")
@CrossOrigin(origins = "*")
public class PaisControlador {

    private final IPaisServicio servicio;
    private final PaisServicio paisServicio;

    public PaisControlador(IPaisServicio servicio, PaisServicio paisServicio) {
        this.servicio = servicio;
        this.paisServicio = paisServicio;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Pais>> listar() {
        List<Pais> paises = servicio.listar();
        return ResponseEntity.ok(paises);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Pais> obtener(@PathVariable int id) {
        Pais pais = servicio.obtener(id);
        if (pais == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pais);
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Pais>> buscar(@PathVariable String nombre) {
        List<Pais> paises = servicio.buscar(nombre);
        return ResponseEntity.ok(paises);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Pais> agregar(@RequestBody Pais pais) {
        try {
            Pais nuevoPais = servicio.agregar(pais);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPais);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<Pais> modificar(@RequestBody Pais pais) {
        try {
            Pais paisModificado = servicio.modificar(pais);
            if (paisModificado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(paisModificado);
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
    public ResponseEntity<List<PaisDto>> listarDto() {
        List<PaisDto> paises = paisServicio.listarDto();
        return ResponseEntity.ok(paises);
    }

    @GetMapping("/dto/obtener/{id}")
    public ResponseEntity<PaisDto> obtenerDto(@PathVariable int id) {
        PaisDto pais = paisServicio.obtenerDto(id);
        if (pais == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pais);
    }

    @GetMapping("/dto/buscar/{nombre}")
    public ResponseEntity<List<PaisDto>> buscarDto(@PathVariable String nombre) {
        List<PaisDto> paises = paisServicio.buscarDto(nombre);
        return ResponseEntity.ok(paises);
    }
}
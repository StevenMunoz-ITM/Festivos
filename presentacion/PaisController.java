package festivos.api.presentacion;

import festivos.api.dominio.dto.PaisDTO;
import festivos.api.aplicacion.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/paises")
@CrossOrigin(origins = "*")
public class PaisController extends BaseController {

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<PaisDTO>> obtenerTodos() {
        return obtenerTodos(paisService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> obtenerPorId(@PathVariable Long id) {
        return obtenerPorId(paisService.obtenerPorId(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PaisDTO> obtenerPorNombre(@PathVariable String nombre) {
        return obtenerPorId(paisService.obtenerPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<PaisDTO> crear(@Valid @RequestBody PaisDTO pais) {
        return crear(() -> paisService.guardar(pais));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaisDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PaisDTO pais) {
        return actualizar(() -> paisService.actualizar(id, pais));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return eliminar(() -> paisService.eliminar(id));
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existe(@PathVariable Long id) {
        return verificarExistencia(paisService.existe(id));
    }
}

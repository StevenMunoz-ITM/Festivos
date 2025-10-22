package festivos.api.presentacion;

import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.aplicacion.TipoFestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-festivos")
@CrossOrigin(origins = "*")
public class TipoFestivoController extends BaseController {

    @Autowired
    private TipoFestivoService tipoFestivoService;

    @GetMapping
    public ResponseEntity<List<TipoFestivoDTO>> obtenerTodos() {
        return obtenerTodos(tipoFestivoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoFestivoDTO> obtenerPorId(@PathVariable Long id) {
        return obtenerPorId(tipoFestivoService.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<TipoFestivoDTO> obtenerPorTipo(@PathVariable String tipo) {
        return obtenerPorId(tipoFestivoService.obtenerPorTipo(tipo));
    }

    @PostMapping
    public ResponseEntity<TipoFestivoDTO> crear(@Valid @RequestBody TipoFestivoDTO tipoFestivo) {
        return crear(() -> tipoFestivoService.guardar(tipoFestivo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoFestivoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TipoFestivoDTO tipoFestivo) {
        return actualizar(() -> tipoFestivoService.actualizar(id, tipoFestivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return eliminar(() -> tipoFestivoService.eliminar(id));
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existe(@PathVariable Long id) {
        return verificarExistencia(tipoFestivoService.existe(id));
    }
}

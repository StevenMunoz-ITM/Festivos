package festivos.api.presentacion;

import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.aplicacion.TipoFestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-festivos")
@CrossOrigin(origins = "*")
public class TipoFestivoController {

    @Autowired
    private TipoFestivoService tipoFestivoService;

    @GetMapping
    public ResponseEntity<List<TipoFestivoDTO>> obtenerTodos() {
        List<TipoFestivoDTO> tiposFestivos = tipoFestivoService.obtenerTodos();
        return ResponseEntity.ok(tiposFestivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoFestivoDTO> obtenerPorId(@PathVariable Long id) {
        Optional<TipoFestivoDTO> tipoFestivo = tipoFestivoService.obtenerPorId(id);
        return tipoFestivo.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<TipoFestivoDTO> obtenerPorTipo(@PathVariable String tipo) {
        Optional<TipoFestivoDTO> tipoFestivo = tipoFestivoService.obtenerPorTipo(tipo);
        return tipoFestivo.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoFestivoDTO> crear(@Valid @RequestBody TipoFestivoDTO tipoFestivo) {
        try {
            TipoFestivoDTO tipoGuardado = tipoFestivoService.guardar(tipoFestivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoFestivoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TipoFestivoDTO tipoFestivo) {
        try {
            TipoFestivoDTO tipoActualizado = tipoFestivoService.actualizar(id, tipoFestivo);
            return ResponseEntity.ok(tipoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            tipoFestivoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existe(@PathVariable Long id) {
        boolean existe = tipoFestivoService.existe(id);
        return ResponseEntity.ok(existe);
    }
}

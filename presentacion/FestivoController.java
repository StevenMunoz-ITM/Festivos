package festivos.api.presentacion;

import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.aplicacion.FestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/festivos")
@CrossOrigin(origins = "*")
public class FestivoController {

    @Autowired
    private FestivoService festivoService;

    @GetMapping
    public ResponseEntity<List<FestivoDTO>> obtenerTodos() {
        List<FestivoDTO> festivos = festivoService.obtenerTodos();
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FestivoDTO> obtenerPorId(@PathVariable Long id) {
        Optional<FestivoDTO> festivo = festivoService.obtenerPorId(id);
        return festivo.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pais/{paisId}")
    public ResponseEntity<List<FestivoDTO>> obtenerPorPais(@PathVariable Long paisId) {
        List<FestivoDTO> festivos = festivoService.obtenerPorPais(paisId);
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<FestivoDTO>> obtenerPorFecha(@RequestParam Integer dia, @RequestParam Integer mes) {
        try {
            List<FestivoDTO> festivos = festivoService.obtenerPorFecha(dia, mes);
            return ResponseEntity.ok(festivos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pais/{paisId}/mes/{mes}")
    public ResponseEntity<List<FestivoDTO>> obtenerPorPaisYMes(@PathVariable Long paisId, @PathVariable Integer mes) {
        try {
            List<FestivoDTO> festivos = festivoService.obtenerPorPaisYMes(paisId, mes);
            return ResponseEntity.ok(festivos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pascua")
    public ResponseEntity<List<FestivoDTO>> obtenerFestivosPascua() {
        List<FestivoDTO> festivos = festivoService.obtenerFestivosPascua();
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/pais/{paisId}/pascua")
    public ResponseEntity<List<FestivoDTO>> obtenerFestivosPascuaPorPais(@PathVariable Long paisId) {
        List<FestivoDTO> festivos = festivoService.obtenerFestivosPascuaPorPais(paisId);
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/es-festivo")
    public ResponseEntity<Boolean> esFestivo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam Long paisId) {
        try {
            boolean esFestivo = festivoService.esFestivo(fecha, paisId);
            return ResponseEntity.ok(esFestivo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{festivoId}/fecha-celebracion/{año}")
    public ResponseEntity<LocalDate> obtenerFechaCelebracion(@PathVariable Long festivoId, @PathVariable int año) {
        try {
            LocalDate fechaCelebracion = festivoService.obtenerFechaCelebracion(festivoId, año);
            return ResponseEntity.ok(fechaCelebracion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/domingo-ramos/{año}")
    public ResponseEntity<LocalDate> obtenerDomingoDeRamos(@PathVariable int año) {
        try {
            LocalDate domingoRamos = festivoService.calcularDomingoDeRamos(año);
            return ResponseEntity.ok(domingoRamos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<FestivoDTO> crear(@Valid @RequestBody FestivoDTO festivo) {
        try {
            FestivoDTO festivoGuardado = festivoService.guardar(festivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(festivoGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FestivoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody FestivoDTO festivo) {
        try {
            FestivoDTO festivoActualizado = festivoService.actualizar(id, festivo);
            return ResponseEntity.ok(festivoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            festivoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
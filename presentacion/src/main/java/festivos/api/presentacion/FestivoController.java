package festivos.api.presentacion;

import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.aplicacion.FestivoService;
import festivos.api.aplicacion.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/festivos")
@CrossOrigin(origins = "*")
public class FestivoController extends BaseController<FestivoDTO, Long> {

    @Autowired
    private FestivoService festivoService;

    @Override
    protected BaseService<?, FestivoDTO, Long> getService() {
        return festivoService;
    }

    @GetMapping("/pais/{paisId}")
    public ResponseEntity<List<FestivoDTO>> obtenerPorPais(@PathVariable Long paisId) {
        return obtenerTodos(festivoService.obtenerPorPais(paisId));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<FestivoDTO>> obtenerPorFecha(@RequestParam Integer dia, @RequestParam Integer mes) {
        return ejecutarOperacionConManejadorErrores(() -> festivoService.obtenerPorFecha(dia, mes));
    }

    @GetMapping("/pais/{paisId}/mes/{mes}")
    public ResponseEntity<List<FestivoDTO>> obtenerPorPaisYMes(@PathVariable Long paisId, @PathVariable Integer mes) {
        return ejecutarOperacionConManejadorErrores(() -> festivoService.obtenerPorPaisYMes(paisId, mes));
    }

    @GetMapping("/pascua")
    public ResponseEntity<List<FestivoDTO>> obtenerFestivosPascua() {
        return obtenerTodos(festivoService.obtenerFestivosPascua());
    }

    @GetMapping("/pais/{paisId}/pascua")
    public ResponseEntity<List<FestivoDTO>> obtenerFestivosPascuaPorPais(@PathVariable Long paisId) {
        return obtenerTodos(festivoService.obtenerFestivosPascuaPorPais(paisId));
    }

    @GetMapping("/es-festivo")
    public ResponseEntity<String> esFestivo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam Long paisId) {
        return ejecutarOperacionConManejadorErrores(() -> {
            boolean esFestivo = festivoService.esFestivo(fecha, paisId);
            return esFestivo ? "Es festivo" : "No es festivo";
        });
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

    @GetMapping("/domingo-ramos")
    public ResponseEntity<LocalDate> obtenerDomingoDeRamos(@RequestParam int anio) {
        return ejecutarOperacionConManejadorErrores(() -> festivoService.calcularDomingoDeRamos(anio));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<FestivoDTO>> buscar(@RequestParam String nombre) {
        return obtenerTodos(festivoService.buscar(nombre));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FestivoDTO>> listar() {
        return obtenerTodos();
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<FestivoDTO> obtener(@PathVariable Long id) {
        return obtenerPorId(id);
    }
}

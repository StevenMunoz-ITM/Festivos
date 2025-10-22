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

    /**
     * Obtiene todos los festivos
     * @return Lista de festivos
     */
    @GetMapping
    public ResponseEntity<List<FestivoDTO>> obtenerTodos() {
        List<FestivoDTO> festivos = festivoService.obtenerTodos();
        return ResponseEntity.ok(festivos);
    }

    /**
     * Obtiene un festivo por su ID
     * @param id ID del festivo
     * @return Festivo encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<FestivoDTO> obtenerPorId(@PathVariable Long id) {
        Optional<FestivoDTO> festivo = festivoService.obtenerPorId(id);
        return festivo.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los festivos de un país específico
     * @param paisId ID del país
     * @return Lista de festivos del país
     */
    @GetMapping("/pais/{paisId}")
    public ResponseEntity<List<FestivoDTO>> obtenerPorPais(@PathVariable Long paisId) {
        List<FestivoDTO> festivos = festivoService.obtenerPorPais(paisId);
        return ResponseEntity.ok(festivos);
    }

    /**
     * Obtiene festivos por día y mes específicos
     * @param dia Día del festivo
     * @param mes Mes del festivo
     * @return Lista de festivos en la fecha especificada
     */
    @GetMapping("/fecha")
    public ResponseEntity<List<FestivoDTO>> obtenerPorFecha(@RequestParam Integer dia, @RequestParam Integer mes) {
        try {
            List<FestivoDTO> festivos = festivoService.obtenerPorFecha(dia, mes);
            return ResponseEntity.ok(festivos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene festivos de un país en un mes específico
     * @param paisId ID del país
     * @param mes Mes del festivo
     * @return Lista de festivos del país en el mes especificado
     */
    @GetMapping("/pais/{paisId}/mes/{mes}")
    public ResponseEntity<List<FestivoDTO>> obtenerPorPaisYMes(@PathVariable Long paisId, @PathVariable Integer mes) {
        try {
            List<FestivoDTO> festivos = festivoService.obtenerPorPaisYMes(paisId, mes);
            return ResponseEntity.ok(festivos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene festivos que se calculan en base a Pascua
     * @return Lista de festivos basados en Pascua
     */
    @GetMapping("/pascua")
    public ResponseEntity<List<FestivoDTO>> obtenerFestivosPascua() {
        List<FestivoDTO> festivos = festivoService.obtenerFestivosPascua();
        return ResponseEntity.ok(festivos);
    }

    /**
     * Obtiene festivos de un país que se calculan en base a Pascua
     * @param paisId ID del país
     * @return Lista de festivos del país basados en Pascua
     */
    @GetMapping("/pais/{paisId}/pascua")
    public ResponseEntity<List<FestivoDTO>> obtenerFestivosPascuaPorPais(@PathVariable Long paisId) {
        List<FestivoDTO> festivos = festivoService.obtenerFestivosPascuaPorPais(paisId);
        return ResponseEntity.ok(festivos);
    }

    /**
     * Verifica si una fecha es festivo en un país específico
     * @param fecha Fecha a verificar (formato: yyyy-MM-dd)
     * @param paisId ID del país
     * @return true si es festivo, false si no
     */
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

    /**
     * Obtiene la fecha real de celebración de un festivo para un año específico
     * @param festivoId ID del festivo
     * @param año Año para calcular la fecha
     * @return Fecha de celebración del festivo
     */
    @GetMapping("/{festivoId}/fecha-celebracion/{año}")
    public ResponseEntity<LocalDate> obtenerFechaCelebracion(@PathVariable Long festivoId, @PathVariable int año) {
        try {
            LocalDate fechaCelebracion = festivoService.obtenerFechaCelebracion(festivoId, año);
            return ResponseEntity.ok(fechaCelebracion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Calcula la fecha del Domingo de Ramos para un año específico
     * @param año Año para calcular
     * @return Fecha del Domingo de Ramos
     */
    @GetMapping("/domingo-ramos/{año}")
    public ResponseEntity<LocalDate> obtenerDomingoDeRamos(@PathVariable int año) {
        try {
            LocalDate domingoRamos = festivoService.calcularDomingoDeRamos(año);
            return ResponseEntity.ok(domingoRamos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Crea un nuevo festivo
     * @param festivo Festivo a crear
     * @return Festivo creado
     */
    @PostMapping
    public ResponseEntity<FestivoDTO> crear(@Valid @RequestBody FestivoDTO festivo) {
        try {
            FestivoDTO festivoGuardado = festivoService.guardar(festivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(festivoGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un festivo existente
     * @param id ID del festivo a actualizar
     * @param festivo Datos actualizados del festivo
     * @return Festivo actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<FestivoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody FestivoDTO festivo) {
        try {
            FestivoDTO festivoActualizado = festivoService.actualizar(id, festivo);
            return ResponseEntity.ok(festivoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un festivo
     * @param id ID del festivo a eliminar
     * @return Respuesta sin contenido
     */
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
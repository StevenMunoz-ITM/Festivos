package festivos.api.presentacion;

import festivos.api.dominio.dto.PaisDTO;
import festivos.api.aplicacion.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paises")
@CrossOrigin(origins = "*")
public class PaisController {

    @Autowired
    private PaisService paisService;

    /**
     * Obtiene todos los países
     * @return Lista de países
     */
    @GetMapping
    public ResponseEntity<List<PaisDTO>> obtenerTodos() {
        List<PaisDTO> paises = paisService.obtenerTodos();
        return ResponseEntity.ok(paises);
    }

    /**
     * Obtiene un país por su ID
     * @param id ID del país
     * @return País encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> obtenerPorId(@PathVariable Long id) {
        Optional<PaisDTO> pais = paisService.obtenerPorId(id);
        return pais.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene un país por su nombre
     * @param nombre Nombre del país
     * @return País encontrado
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PaisDTO> obtenerPorNombre(@PathVariable String nombre) {
        Optional<PaisDTO> pais = paisService.obtenerPorNombre(nombre);
        return pais.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo país
     * @param pais País a crear
     * @return País creado
     */
    @PostMapping
    public ResponseEntity<PaisDTO> crear(@Valid @RequestBody PaisDTO pais) {
        try {
            PaisDTO paisGuardado = paisService.guardar(pais);
            return ResponseEntity.status(HttpStatus.CREATED).body(paisGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un país existente
     * @param id ID del país a actualizar
     * @param pais Datos actualizados del país
     * @return País actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaisDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PaisDTO pais) {
        try {
            PaisDTO paisActualizado = paisService.actualizar(id, pais);
            return ResponseEntity.ok(paisActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un país
     * @param id ID del país a eliminar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            paisService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Verifica si existe un país con el ID especificado
     * @param id ID del país
     * @return true si existe, false si no
     */
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existe(@PathVariable Long id) {
        boolean existe = paisService.existe(id);
        return ResponseEntity.ok(existe);
    }
}
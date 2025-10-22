package festivos.api.presentacion;

import festivos.api.aplicacion.BaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class BaseController<DTO, ID> {

    protected abstract BaseService<?, DTO, ID> getService();

    @GetMapping
    public ResponseEntity<List<DTO>> obtenerTodos() {
        return obtenerTodos(getService().obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> obtenerPorId(@PathVariable ID id) {
        return obtenerPorId(getService().obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<DTO> crear(@Valid @RequestBody DTO dto) {
        return crear(() -> getService().guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> actualizar(@PathVariable ID id, @Valid @RequestBody DTO dto) {
        return actualizar(() -> getService().actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable ID id) {
        try {
            getService().eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existe(@PathVariable ID id) {
        return verificarExistencia(getService().existe(id));
    }

    protected <T> ResponseEntity<T> obtenerPorId(Optional<T> entidad) {
        return entidad.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    protected <T> ResponseEntity<List<T>> obtenerTodos(List<T> entidades) {
        return ResponseEntity.ok(entidades);
    }

    protected <T> ResponseEntity<T> crear(Supplier<T> operacion) {
        try {
            T entidadGuardada = operacion.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(entidadGuardada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    protected <T> ResponseEntity<T> actualizar(Supplier<T> operacion) {
        try {
            T entidadActualizada = operacion.get();
            return ResponseEntity.ok(entidadActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    protected <T> ResponseEntity<T> ejecutarOperacionConManejadorErrores(Supplier<T> operacion) {
        try {
            T resultado = operacion.get();
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    protected ResponseEntity<Boolean> verificarExistencia(boolean existe) {
        return ResponseEntity.ok(existe);
    }
}
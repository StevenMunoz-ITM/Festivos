package festivos.api.presentacion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class BaseController {

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

    protected ResponseEntity<Void> eliminar(Runnable operacion) {
        try {
            operacion.run();
            return ResponseEntity.noContent().build();
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
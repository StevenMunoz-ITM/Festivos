package festivos.api.aplicacion.utils;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ValidationUtils {

    public <T, ID> void validarEntidadUnica(
            Function<String, Optional<T>> searchFunction,
            String fieldValue,
            ID idExcluir,
            String fieldName,
            String entityName,
            Function<T, ID> getIdFunction) {
        
        Optional<T> entidadExistente = searchFunction.apply(fieldValue);
        if (entidadExistente.isPresent()) {
            ID idExistente = getIdFunction.apply(entidadExistente.get());
            if (idExcluir == null || !idExistente.equals(idExcluir)) {
                throw new IllegalArgumentException(
                    String.format("Ya existe %s con %s: %s", entityName, fieldName, fieldValue)
                );
            }
        }
    }

    public void validarCampoRequerido(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("El campo %s es obligatorio", fieldName));
        }
    }

    public void validarCampoRequerido(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("El campo %s es obligatorio", fieldName));
        }
    }

    public void validarRango(Integer value, int min, int max, String fieldName) {
        if (value != null && (value < min || value > max)) {
            throw new IllegalArgumentException(
                String.format("El campo %s debe estar entre %d y %d", fieldName, min, max)
            );
        }
    }

    public void validarLongitudMaxima(String value, int maxLength, String fieldName) {
        if (value != null && value.length() > maxLength) {
            throw new IllegalArgumentException(
                String.format("El campo %s no puede exceder %d caracteres", fieldName, maxLength)
            );
        }
    }

    public <ID> void validarEntidadExiste(Function<ID, Boolean> exists, ID id, String entityName) {
        if (!exists.apply(id)) {
            throw new IllegalArgumentException(String.format("%s no encontrado con ID: %s", entityName, id));
        }
    }
}
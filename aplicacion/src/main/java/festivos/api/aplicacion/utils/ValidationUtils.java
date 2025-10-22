package festivos.api.aplicacion.utils;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ValidationUtils {

    /**
     * Valida que una entidad sea única por un campo específico
     * @param searchFunction Función que busca la entidad existente
     * @param fieldValue Valor del campo a validar
     * @param idExcluir ID a excluir en la validación (para actualizaciones)
     * @param fieldName Nombre del campo para el mensaje de error
     * @param entityName Nombre de la entidad para el mensaje de error
     * @param <T> Tipo de la entidad
     * @param <ID> Tipo del ID
     */
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

    /**
     * Valida que un valor no sea nulo o vacío
     * @param value Valor a validar
     * @param fieldName Nombre del campo
     */
    public void validarCampoRequerido(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("El campo %s es obligatorio", fieldName));
        }
    }

    /**
     * Valida que un valor no sea nulo
     * @param value Valor a validar
     * @param fieldName Nombre del campo
     */
    public void validarCampoRequerido(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("El campo %s es obligatorio", fieldName));
        }
    }

    /**
     * Valida que un número esté dentro de un rango
     * @param value Valor a validar
     * @param min Valor mínimo
     * @param max Valor máximo
     * @param fieldName Nombre del campo
     */
    public void validarRango(Integer value, int min, int max, String fieldName) {
        if (value != null && (value < min || value > max)) {
            throw new IllegalArgumentException(
                String.format("El campo %s debe estar entre %d y %d", fieldName, min, max)
            );
        }
    }

    /**
     * Valida la longitud máxima de un string
     * @param value Valor a validar
     * @param maxLength Longitud máxima
     * @param fieldName Nombre del campo
     */
    public void validarLongitudMaxima(String value, int maxLength, String fieldName) {
        if (value != null && value.length() > maxLength) {
            throw new IllegalArgumentException(
                String.format("El campo %s no puede exceder %d caracteres", fieldName, maxLength)
            );
        }
    }

    /**
     * Valida que una entidad exista
     * @param exists Función que verifica la existencia
     * @param id ID de la entidad
     * @param entityName Nombre de la entidad
     */
    public <ID> void validarEntidadExiste(Function<ID, Boolean> exists, ID id, String entityName) {
        if (!exists.apply(id)) {
            throw new IllegalArgumentException(String.format("%s no encontrado con ID: %s", entityName, id));
        }
    }
}
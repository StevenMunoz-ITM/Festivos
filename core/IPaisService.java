package festivos.api.core;

import festivos.api.dominio.dto.PaisDTO;
import java.util.List;
import java.util.Optional;

public interface IPaisService {

    List<PaisDTO> obtenerTodos();

    Optional<PaisDTO> obtenerPorId(Long id);

    Optional<PaisDTO> obtenerPorNombre(String nombre);

    PaisDTO guardar(PaisDTO paisDTO);

    PaisDTO actualizar(Long id, PaisDTO paisDTO);

    void eliminar(Long id);

    boolean existe(Long id);

    boolean existePorNombre(String nombre);
}
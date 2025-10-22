package festivos.api.core;

import festivos.api.dominio.dto.TipoFestivoDTO;
import java.util.List;
import java.util.Optional;

public interface ITipoFestivoService {

    List<TipoFestivoDTO> obtenerTodos();

    Optional<TipoFestivoDTO> obtenerPorId(Long id);

    Optional<TipoFestivoDTO> obtenerPorTipo(String tipo);

    TipoFestivoDTO guardar(TipoFestivoDTO tipoFestivoDTO);

    TipoFestivoDTO actualizar(Long id, TipoFestivoDTO tipoFestivoDTO);

    void eliminar(Long id);

    boolean existe(Long id);

    boolean existePorTipo(String tipo);
}
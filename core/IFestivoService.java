package festivos.api.core;

import festivos.api.dominio.dto.FestivoDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IFestivoService {

    List<FestivoDTO> obtenerTodos();

    Optional<FestivoDTO> obtenerPorId(Long id);

    List<FestivoDTO> obtenerPorPais(Long paisId);

    List<FestivoDTO> obtenerPorFecha(Integer dia, Integer mes);

    List<FestivoDTO> obtenerPorPaisYMes(Long paisId, Integer mes);

    List<FestivoDTO> obtenerFestivosPascua();

    List<FestivoDTO> obtenerFestivosPascuaPorPais(Long paisId);

    FestivoDTO guardar(FestivoDTO festivoDTO);

    FestivoDTO actualizar(Long id, FestivoDTO festivoDTO);

    void eliminar(Long id);

    boolean esFestivo(LocalDate fecha, Long paisId);

    LocalDate calcularDomingoDeRamos(int año);

    LocalDate obtenerFechaCelebracion(Long festivoId, int año);
}
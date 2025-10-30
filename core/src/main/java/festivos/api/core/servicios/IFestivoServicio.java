package festivos.api.core.servicios;

import java.util.List;
import java.time.LocalDate;
import festivos.api.dominio.entidades.Festivo;

public interface IFestivoServicio {

    public List<Festivo> listar();

    public List<Festivo> listarPorPais(int idPais);

    public List<Festivo> listarPorTipo(int idTipo);

    public Festivo obtener(int id);

    public List<Festivo> buscar(String nombre);

    public Festivo agregar(Festivo festivo);

    public Festivo modificar(Festivo festivo);

    public boolean eliminar(int id);

    public List<LocalDate> obtenerFestivosPorAno(int ano, int idPais);

    public boolean esFestivo(LocalDate fecha, int idPais);

    public LocalDate calcularPascua(int ano);

    public LocalDate calcularDomingoRamos(int ano);

}
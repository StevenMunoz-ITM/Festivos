package festivos.api.core.servicios;

import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.dtos.FestivoDto;
import festivos.api.dominio.dtos.ValidacionFestivoDto;
import java.time.LocalDate;
import java.util.List;

public interface IFestivoServicio {
    
    List<Festivo> listar();
    
    Festivo obtener(int id);
    
    List<Festivo> buscar(String nombre);
    
    List<Festivo> listarPorPais(int idPais);
    
    List<Festivo> listarPorTipo(int idTipo);
    
    List<Festivo> listarPorPaisYMes(int idPais, int mes);
    
    Festivo agregar(Festivo festivo);
    
    Festivo modificar(Festivo festivo);
    
    boolean eliminar(int id);
    
    boolean esFestivo(int idPais, int dia, int mes, int anio);
    
    ValidacionFestivoDto validarFestivo(int idPais, int dia, int mes, int anio);
    
    List<FestivoDto> listarFestivosPorAnio(int idPais, int anio);
    
    LocalDate obtenerFechaFestivo(Festivo festivo, int anio);
}
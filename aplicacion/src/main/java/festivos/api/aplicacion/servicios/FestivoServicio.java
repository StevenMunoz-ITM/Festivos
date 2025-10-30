package festivos.api.aplicacion.servicios;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.DayOfWeek;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.dtos.FestivoDto;

import festivos.api.infraestructura.repositorios.IFestivoRepositorio;
import festivos.api.core.servicios.IFestivoServicio;

@Service
public class FestivoServicio implements IFestivoServicio {

    private final IFestivoRepositorio repositorio;

    public FestivoServicio(IFestivoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Festivo> listar() {
        return repositorio.findAll();
    }

    @Override
    public List<Festivo> listarPorPais(int idPais) {
        return repositorio.listarPorPais(idPais);
    }

    @Override
    public List<Festivo> listarPorTipo(int idTipo) {
        return repositorio.listarPorTipo(idTipo);
    }

    @Override
    public Festivo obtener(int id) {
        var festivoEncontrado = repositorio.findById(id);
        return festivoEncontrado.isEmpty() ? null : festivoEncontrado.get();
    }

    @Override
    public List<Festivo> buscar(String nombre) {
        return repositorio.buscar(nombre);
    }

    @Override
    public Festivo agregar(Festivo festivo) {
        festivo.setId(0);
        return repositorio.save(festivo);
    }

    @Override
    public Festivo modificar(Festivo festivo) {
        if (repositorio.findById(festivo.getId()).isEmpty())
            return null;
        return repositorio.save(festivo);
    }

    @Override
    public boolean eliminar(int id) {
        try {
            repositorio.deleteById(id);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    @Override
    public List<LocalDate> obtenerFestivosPorAno(int ano, int idPais) {
        List<Festivo> festivos = repositorio.listarPorPais(idPais);
        List<LocalDate> fechasFestivos = new ArrayList<>();
        LocalDate pascua = calcularPascua(ano);

        for (Festivo festivo : festivos) {
            LocalDate fecha = calcularFechaFestivo(festivo, ano, pascua);
            if (fecha != null) {
                fechasFestivos.add(fecha);
            }
        }

        return fechasFestivos;
    }

    @Override
    public boolean esFestivo(LocalDate fecha, int idPais) {
        List<LocalDate> festivos = obtenerFestivosPorAno(fecha.getYear(), idPais);
        return festivos.contains(fecha);
    }

    @Override
    public LocalDate calcularPascua(int ano) {
        LocalDate domingoRamos = calcularDomingoRamos(ano);
        return domingoRamos.plusDays(7);
    }

    public LocalDate calcularDomingoRamos(int ano) {
        int a = ano % 19;
        int b = ano % 4;
        int c = ano % 7;
        int d = (19 * a + 24) % 30;
        
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        LocalDate fecha15Marzo = LocalDate.of(ano, 3, 15);
        return fecha15Marzo.plusDays(dias);
    }

    private LocalDate calcularFechaFestivo(Festivo festivo, int ano, LocalDate pascua) {
        LocalDate fecha = null;
        int tipoId = festivo.getTipo().getId();

        switch (tipoId) {
            case 1:
                fecha = LocalDate.of(ano, festivo.getMes(), festivo.getDia());
                break;
            case 2:
                fecha = LocalDate.of(ano, festivo.getMes(), festivo.getDia());
                fecha = aplicarLeyPuente(fecha);
                break;
            case 3:
                fecha = pascua.plusDays(festivo.getDiasPascua());
                break;
            case 4:
                fecha = pascua.plusDays(festivo.getDiasPascua());
                fecha = aplicarLeyPuente(fecha);
                break;
            case 5:
                fecha = LocalDate.of(ano, festivo.getMes(), festivo.getDia());
                fecha = aplicarLeyPuenteViernes(fecha);
                break;
        }

        return fecha;
    }

    private LocalDate aplicarLeyPuente(LocalDate fecha) {
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        
        if (diaSemana == DayOfWeek.TUESDAY || diaSemana == DayOfWeek.WEDNESDAY) {
            fecha = fecha.with(DayOfWeek.MONDAY).plusWeeks(1);
        }
        else if (diaSemana == DayOfWeek.THURSDAY || diaSemana == DayOfWeek.FRIDAY) {
            fecha = fecha.with(DayOfWeek.MONDAY).plusWeeks(1);
        }
        else if (diaSemana == DayOfWeek.SATURDAY) {
            fecha = fecha.with(DayOfWeek.MONDAY).plusWeeks(1);
        }
        else if (diaSemana == DayOfWeek.SUNDAY) {
            fecha = fecha.with(DayOfWeek.MONDAY);
        }
        
        return fecha;
    }

    private LocalDate aplicarLeyPuenteViernes(LocalDate fecha) {
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        
        
        if (diaSemana != DayOfWeek.FRIDAY) {
            
            if (diaSemana.getValue() < DayOfWeek.FRIDAY.getValue()) {
                fecha = fecha.with(DayOfWeek.FRIDAY);
            }
          
            else {
                fecha = fecha.with(DayOfWeek.FRIDAY).minusWeeks(1);
            }
        }
        
        return fecha;
    }
    

    private FestivoDto convertirADto(Festivo festivo) {
        if (festivo == null) return null;
        
        return new FestivoDto(
            festivo.getId(),
            festivo.getNombre(),
            festivo.getDia(),
            festivo.getMes(),
            festivo.getDiasPascua(),
            festivo.getPais().getId(),
            festivo.getPais().getNombre(),
            festivo.getTipo().getId(),
            festivo.getTipo().getTipo()
        );
    }
    
    
    public List<FestivoDto> listarDto() {
        return repositorio.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
    
   
    public List<FestivoDto> listarPorPaisDto(int idPais) {
        return repositorio.listarPorPais(idPais).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
  
    public List<FestivoDto> listarPorTipoDto(int idTipo) {
        return repositorio.listarPorTipo(idTipo).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
    

    public FestivoDto obtenerDto(int id) {
        var festivoEncontrado = repositorio.findById(id);
        return festivoEncontrado.isEmpty() ? null : convertirADto(festivoEncontrado.get());
    }
   
    public List<FestivoDto> buscarDto(String nombre) {
        return repositorio.buscar(nombre).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

}
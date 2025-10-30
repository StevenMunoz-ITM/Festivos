package festivos.api.aplicacion.servicios;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.dominio.dtos.TipoFestivoDto;
import festivos.api.infraestructura.repositorios.ITipoFestivoRepositorio;
import festivos.api.core.servicios.ITipoFestivoServicio;

@Service
public class TipoFestivoServicio implements ITipoFestivoServicio {

    private final ITipoFestivoRepositorio repositorio;

    public TipoFestivoServicio(ITipoFestivoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<TipoFestivo> listar() {
        return repositorio.findAll();
    }

    @Override
    public TipoFestivo obtener(int id) {
        var tipoEncontrado = repositorio.findById(id);
        return tipoEncontrado.isEmpty() ? null : tipoEncontrado.get();
    }

    @Override
    public List<TipoFestivo> buscar(String tipo) {
        return repositorio.buscar(tipo);
    }

    @Override
    public TipoFestivo agregar(TipoFestivo tipoFestivo) {
        tipoFestivo.setId(0);
        return repositorio.save(tipoFestivo);
    }

    @Override
    public TipoFestivo modificar(TipoFestivo tipoFestivo) {
        if (repositorio.findById(tipoFestivo.getId()).isEmpty())
            return null;
        return repositorio.save(tipoFestivo);
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
    
    private TipoFestivoDto convertirADto(TipoFestivo tipoFestivo) {
        if (tipoFestivo == null) return null;
        
        return new TipoFestivoDto(tipoFestivo.getId(), tipoFestivo.getTipo());
    }
    

    public List<TipoFestivoDto> listarDto() {
        return repositorio.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
  
    public TipoFestivoDto obtenerDto(int id) {
        var tipoEncontrado = repositorio.findById(id);
        return tipoEncontrado.isEmpty() ? null : convertirADto(tipoEncontrado.get());
    }
   
    public List<TipoFestivoDto> buscarDto(String tipo) {
        return repositorio.buscar(tipo).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
}
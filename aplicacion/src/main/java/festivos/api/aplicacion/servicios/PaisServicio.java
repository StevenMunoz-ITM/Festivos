package festivos.api.aplicacion.servicios;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.dtos.PaisDto;
import festivos.api.infraestructura.repositorios.IPaisRepositorio;
import festivos.api.core.servicios.IPaisServicio;

@Service
public class PaisServicio implements IPaisServicio {

    private final IPaisRepositorio repositorio;

    public PaisServicio(IPaisRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Pais> listar() {
        return repositorio.findAll();
    }

    @Override
    public Pais obtener(int id) {
        var paisEncontrado = repositorio.findById(id);
        return paisEncontrado.isEmpty() ? null : paisEncontrado.get();
    }

    @Override
    public List<Pais> buscar(String nombre) {
        return repositorio.buscar(nombre);
    }

    @Override
    public Pais agregar(Pais pais) {
        pais.setId(0);
        return repositorio.save(pais);
    }

    @Override
    public Pais modificar(Pais pais) {
        if (repositorio.findById(pais.getId()).isEmpty())
            return null;
        return repositorio.save(pais);
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
    
    private PaisDto convertirADto(Pais pais) {
        if (pais == null) return null;
        
        return new PaisDto(pais.getId(), pais.getNombre());
    }
    
    /**
     * Lista todos los países como DTOs
     */
    public List<PaisDto> listarDto() {
        return repositorio.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un país por ID como DTO
     */
    public PaisDto obtenerDto(int id) {
        var paisEncontrado = repositorio.findById(id);
        return paisEncontrado.isEmpty() ? null : convertirADto(paisEncontrado.get());
    }
    
    /**
     * Busca países por nombre y retorna DTOs
     */
    public List<PaisDto> buscarDto(String nombre) {
        return repositorio.buscar(nombre).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
}
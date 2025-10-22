package festivos.api.aplicacion;

import festivos.api.core.IPaisService;
import festivos.api.dominio.dto.PaisDTO;
import festivos.api.dominio.entidades.Pais;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.aplicacion.mapper.PaisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaisService implements IPaisService {
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private PaisMapper paisMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PaisDTO> obtenerTodos() {
        return paisRepository.findAll().stream()
                .map(paisMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> obtenerPorId(Long id) {
        return paisRepository.findById(id)
                .map(paisMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> obtenerPorNombre(String nombre) {
        return paisRepository.findByNombreIgnoreCase(nombre)
                .map(paisMapper::toDTO);
    }

    @Override
    public PaisDTO guardar(PaisDTO paisDTO) {
        if (paisRepository.existsByNombreIgnoreCase(paisDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un país con el nombre: " + paisDTO.getNombre());
        }
        
        Pais pais = paisMapper.toEntity(paisDTO);
        Pais paisGuardado = paisRepository.save(pais);
        return paisMapper.toDTO(paisGuardado);
    }

    @Override
    public PaisDTO actualizar(Long id, PaisDTO paisDTO) {
        Pais paisExistente = paisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("País no encontrado con ID: " + id));
        
        if (!paisExistente.getNombre().equalsIgnoreCase(paisDTO.getNombre()) &&
            paisRepository.existsByNombreIgnoreCase(paisDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un país con el nombre: " + paisDTO.getNombre());
        }
        
        paisExistente.setNombre(paisDTO.getNombre());
        Pais paisActualizado = paisRepository.save(paisExistente);
        return paisMapper.toDTO(paisActualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!paisRepository.existsById(id)) {
            throw new IllegalArgumentException("País no encontrado con ID: " + id);
        }
        paisRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existe(Long id) {
        return paisRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorNombre(String nombre) {
        return paisRepository.existsByNombreIgnoreCase(nombre);
    }
    
    
    @Transactional(readOnly = true)
    public Optional<Pais> obtenerEntidadPorId(Long id) {
        return paisRepository.findById(id);
    }
}

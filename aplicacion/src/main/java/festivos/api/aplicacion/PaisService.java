package festivos.api.aplicacion;

import festivos.api.core.IPaisService;
import festivos.api.dominio.dto.PaisDTO;
import festivos.api.dominio.entidades.Pais;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.aplicacion.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaisService extends BaseService<Pais, PaisDTO, Long> implements IPaisService {
    
    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ValidationUtils validationUtils;

    @Override
    protected JpaRepository<Pais, Long> getRepository() {
        return this.paisRepository;
    }

    @Override
    protected Object getMapper() {
        return this; 
    }

    @Override
    protected PaisDTO mapToDTO(final Pais entity) {
        return this.convertToDTO(entity);
    }

    @Override
    protected Pais mapToEntity(final PaisDTO dto) {
        return this.convertToEntity(dto);
    }

    @Override
    protected List<PaisDTO> mapToDTOList(final List<Pais> entities) {
        return this.convertToDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(final Pais entity, final PaisDTO dto) {
        if (dto.getNombre() != null) {
            entity.setNombre(dto.getNombre());
        }
    }

    @Override
    protected String getEntityName() {
        return "País";
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> obtenerPorNombre(final String nombre) {
        return this.paisRepository.findByNombreIgnoreCase(nombre)
                .map(this::convertToDTO);
    }

    @Override
    public PaisDTO guardar(final PaisDTO paisDTO) {
        this.validarPaisUnico(paisDTO.getNombre(), null);
        return super.guardar(paisDTO);
    }

    @Override
    public PaisDTO actualizar(final Long id, final PaisDTO paisDTO) {
        this.validarPaisUnico(paisDTO.getNombre(), id);
        return super.actualizar(id, paisDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorNombre(final String nombre) {
        return this.paisRepository.existsByNombreIgnoreCase(nombre);
    }
    
    @Transactional(readOnly = true)
    public Optional<Pais> obtenerEntidadPorId(final Long id) {
        return this.paisRepository.findById(id);
    }

    private void validarPaisUnico(final String nombre, final Long idExcluir) {
        this.validationUtils.validarEntidadUnica(
            this.paisRepository::findByNombreIgnoreCase,
            nombre,
            idExcluir,
            "el nombre",
            "un país",
            Pais::getId
        );
    }

    // Métodos de conversión manual
    private PaisDTO convertToDTO(final Pais pais) {
        if (pais == null) return null;
        
        final PaisDTO dto = new PaisDTO();
        dto.setId(pais.getId());
        dto.setNombre(pais.getNombre());
        return dto;
    }

    private Pais convertToEntity(final PaisDTO dto) {
        if (dto == null) return null;
        
        final Pais pais = new Pais();
        pais.setId(dto.getId());
        pais.setNombre(dto.getNombre());
        return pais;
    }

    private List<PaisDTO> convertToDTOList(final List<Pais> paises) {
        if (paises == null) return null;
        
        return paises.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

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
        return paisRepository;
    }

    @Override
    protected Object getMapper() {
        return this; // Retornamos this ya que tenemos métodos de conversión locales
    }

    @Override
    protected PaisDTO mapToDTO(Pais entity) {
        return convertToDTO(entity);
    }

    @Override
    protected Pais mapToEntity(PaisDTO dto) {
        return convertToEntity(dto);
    }

    @Override
    protected List<PaisDTO> mapToDTOList(List<Pais> entities) {
        return convertToDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(Pais entity, PaisDTO dto) {
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
    public Optional<PaisDTO> obtenerPorNombre(String nombre) {
        return paisRepository.findByNombreIgnoreCase(nombre)
                .map(this::convertToDTO);
    }

    @Override
    public PaisDTO guardar(PaisDTO paisDTO) {
        validarPaisUnico(paisDTO.getNombre(), null);
        return super.guardar(paisDTO);
    }

    @Override
    public PaisDTO actualizar(Long id, PaisDTO paisDTO) {
        validarPaisUnico(paisDTO.getNombre(), id);
        return super.actualizar(id, paisDTO);
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

    private void validarPaisUnico(String nombre, Long idExcluir) {
        validationUtils.validarEntidadUnica(
            paisRepository::findByNombreIgnoreCase,
            nombre,
            idExcluir,
            "el nombre",
            "un país",
            Pais::getId
        );
    }

    // Métodos de conversión manual
    private PaisDTO convertToDTO(Pais pais) {
        if (pais == null) return null;
        
        PaisDTO dto = new PaisDTO();
        dto.setId(pais.getId());
        dto.setNombre(pais.getNombre());
        return dto;
    }

    private Pais convertToEntity(PaisDTO dto) {
        if (dto == null) return null;
        
        Pais pais = new Pais();
        pais.setId(dto.getId());
        pais.setNombre(dto.getNombre());
        return pais;
    }

    private List<PaisDTO> convertToDTOList(List<Pais> paises) {
        if (paises == null) return null;
        
        return paises.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

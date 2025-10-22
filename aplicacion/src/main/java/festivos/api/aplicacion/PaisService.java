package festivos.api.aplicacion;

import festivos.api.core.IPaisService;
import festivos.api.dominio.dto.PaisDTO;
import festivos.api.dominio.entidades.Pais;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.aplicacion.mapper.PaisMapper;
import festivos.api.aplicacion.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaisService extends BaseService<Pais, PaisDTO, Long> implements IPaisService {
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private PaisMapper paisMapper;

    @Autowired
    private ValidationUtils validationUtils;

    @Override
    protected JpaRepository<Pais, Long> getRepository() {
        return paisRepository;
    }

    @Override
    protected Object getMapper() {
        return paisMapper;
    }

    @Override
    protected PaisDTO mapToDTO(Pais entity) {
        return paisMapper.toDTO(entity);
    }

    @Override
    protected Pais mapToEntity(PaisDTO dto) {
        return paisMapper.toEntity(dto);
    }

    @Override
    protected List<PaisDTO> mapToDTOList(List<Pais> entities) {
        return paisMapper.toDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(Pais entity, PaisDTO dto) {
        paisMapper.updateEntity(entity, dto);
    }

    @Override
    protected String getEntityName() {
        return "País";
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> obtenerPorNombre(String nombre) {
        return paisRepository.findByNombreIgnoreCase(nombre)
                .map(paisMapper::toDTO);
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
}

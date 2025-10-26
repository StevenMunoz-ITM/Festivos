package festivos.api.aplicacion;

import festivos.api.core.ITipoFestivoService;
import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import festivos.api.aplicacion.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoFestivoService extends BaseService<TipoFestivo, TipoFestivoDTO, Long> implements ITipoFestivoService {
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;

    @Autowired
    private ValidationUtils validationUtils;

    @Override
    protected JpaRepository<TipoFestivo, Long> getRepository() {
        return this.tipoFestivoRepository;
    }

    private TipoFestivoDTO convertToDTO(final TipoFestivo entity) {
        if (entity == null) return null;
        
        final TipoFestivoDTO dto = new TipoFestivoDTO();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        return dto;
    }

    private TipoFestivo convertToEntity(final TipoFestivoDTO dto) {
        if (dto == null) return null;
        
        final TipoFestivo entity = new TipoFestivo();
        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        return entity;
    }

    private List<TipoFestivoDTO> convertToDTOList(final List<TipoFestivo> entities) {
        if (entities == null) return null;
        
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected Object getMapper() {
        return this;
    }

    @Override
    protected TipoFestivoDTO mapToDTO(final TipoFestivo entity) {
        return this.convertToDTO(entity);
    }

    @Override
    protected TipoFestivo mapToEntity(final TipoFestivoDTO dto) {
        return this.convertToEntity(dto);
    }

    @Override
    protected List<TipoFestivoDTO> mapToDTOList(final List<TipoFestivo> entities) {
        return this.convertToDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(final TipoFestivo entity, final TipoFestivoDTO dto) {
        if (dto.getTipo() != null) {
            entity.setTipo(dto.getTipo());
        }
    }

    @Override
    protected String getEntityName() {
        return "Tipo de festivo";
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFestivoDTO> obtenerPorTipo(final String tipo) {
        return this.tipoFestivoRepository.findByTipoIgnoreCase(tipo)
                .map(this::convertToDTO);
    }

    @Override
    public TipoFestivoDTO guardar(final TipoFestivoDTO tipoFestivoDTO) {
        this.validarTipoUnico(tipoFestivoDTO.getTipo(), null);
        return super.guardar(tipoFestivoDTO);
    }

    @Override
    public TipoFestivoDTO actualizar(final Long id, final TipoFestivoDTO tipoFestivoDTO) {
        this.validarTipoUnico(tipoFestivoDTO.getTipo(), id);
        return super.actualizar(id, tipoFestivoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorTipo(final String tipo) {
        return this.tipoFestivoRepository.existsByTipoIgnoreCase(tipo);
    }
    
    @Transactional(readOnly = true)
    public Optional<TipoFestivo> obtenerEntidadPorId(final Long id) {
        return this.tipoFestivoRepository.findById(id);
    }

    private void validarTipoUnico(final String tipo, final Long idExcluir) {
        this.validationUtils.validarEntidadUnica(
            this.tipoFestivoRepository::findByTipoIgnoreCase,
            tipo,
            idExcluir,
            "el tipo",
            "un tipo de festivo",
            TipoFestivo::getId
        );
    }
}

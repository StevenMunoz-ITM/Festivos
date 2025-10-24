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
        return tipoFestivoRepository;
    }

    // Métodos de conversión manual para reemplazar el mapper
    private TipoFestivoDTO convertToDTO(TipoFestivo entity) {
        if (entity == null) return null;
        
        TipoFestivoDTO dto = new TipoFestivoDTO();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        return dto;
    }

    private TipoFestivo convertToEntity(TipoFestivoDTO dto) {
        if (dto == null) return null;
        
        TipoFestivo entity = new TipoFestivo();
        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        return entity;
    }

    private List<TipoFestivoDTO> convertToDTOList(List<TipoFestivo> entities) {
        if (entities == null) return null;
        
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected Object getMapper() {
        return this; // Retornamos this ya que tenemos métodos de conversión locales
    }

    @Override
    protected TipoFestivoDTO mapToDTO(TipoFestivo entity) {
        return convertToDTO(entity);
    }

    @Override
    protected TipoFestivo mapToEntity(TipoFestivoDTO dto) {
        return convertToEntity(dto);
    }

    @Override
    protected List<TipoFestivoDTO> mapToDTOList(List<TipoFestivo> entities) {
        return convertToDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(TipoFestivo entity, TipoFestivoDTO dto) {
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
    public Optional<TipoFestivoDTO> obtenerPorTipo(String tipo) {
        return tipoFestivoRepository.findByTipoIgnoreCase(tipo)
                .map(this::convertToDTO);
    }

    @Override
    public TipoFestivoDTO guardar(TipoFestivoDTO tipoFestivoDTO) {
        validarTipoUnico(tipoFestivoDTO.getTipo(), null);
        return super.guardar(tipoFestivoDTO);
    }

    @Override
    public TipoFestivoDTO actualizar(Long id, TipoFestivoDTO tipoFestivoDTO) {
        validarTipoUnico(tipoFestivoDTO.getTipo(), id);
        return super.actualizar(id, tipoFestivoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorTipo(String tipo) {
        return tipoFestivoRepository.existsByTipoIgnoreCase(tipo);
    }
    
    @Transactional(readOnly = true)
    public Optional<TipoFestivo> obtenerEntidadPorId(Long id) {
        return tipoFestivoRepository.findById(id);
    }

    private void validarTipoUnico(String tipo, Long idExcluir) {
        validationUtils.validarEntidadUnica(
            tipoFestivoRepository::findByTipoIgnoreCase,
            tipo,
            idExcluir,
            "el tipo",
            "un tipo de festivo",
            TipoFestivo::getId
        );
    }
}

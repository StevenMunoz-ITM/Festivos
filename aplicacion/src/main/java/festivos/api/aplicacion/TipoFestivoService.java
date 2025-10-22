package festivos.api.aplicacion;

import festivos.api.core.ITipoFestivoService;
import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import festivos.api.aplicacion.mapper.TipoFestivoMapper;
import festivos.api.aplicacion.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoFestivoService extends BaseService<TipoFestivo, TipoFestivoDTO, Long> implements ITipoFestivoService {
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;
    
    @Autowired
    private TipoFestivoMapper tipoFestivoMapper;

    @Autowired
    private ValidationUtils validationUtils;

    @Override
    protected JpaRepository<TipoFestivo, Long> getRepository() {
        return tipoFestivoRepository;
    }

    @Override
    protected Object getMapper() {
        return tipoFestivoMapper;
    }

    @Override
    protected TipoFestivoDTO mapToDTO(TipoFestivo entity) {
        return tipoFestivoMapper.toDTO(entity);
    }

    @Override
    protected TipoFestivo mapToEntity(TipoFestivoDTO dto) {
        return tipoFestivoMapper.toEntity(dto);
    }

    @Override
    protected List<TipoFestivoDTO> mapToDTOList(List<TipoFestivo> entities) {
        return tipoFestivoMapper.toDTOList(entities);
    }

    @Override
    protected void updateEntityFromDTO(TipoFestivo entity, TipoFestivoDTO dto) {
        entity.setTipo(dto.getTipo());
    }

    @Override
    protected String getEntityName() {
        return "Tipo de festivo";
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFestivoDTO> obtenerPorTipo(String tipo) {
        return tipoFestivoRepository.findByTipoIgnoreCase(tipo)
                .map(tipoFestivoMapper::toDTO);
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

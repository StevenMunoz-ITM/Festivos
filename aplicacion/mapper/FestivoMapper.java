package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Festivo y FestivoDTO
 */
@Component
public class FestivoMapper {
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;
    
    /**
     * Convierte una entidad Festivo a un DTO
     * @param festivo Entidad Festivo
     * @return DTO FestivoDTO
     */
    public FestivoDTO toDTO(Festivo festivo) {
        if (festivo == null) {
            return null;
        }
        
        FestivoDTO dto = new FestivoDTO();
        dto.setId(festivo.getId());
        dto.setNombre(festivo.getNombre());
        dto.setDia(festivo.getDia());
        dto.setMes(festivo.getMes());
        dto.setDiasPascua(festivo.getDiasPascua());
        
        if (festivo.getPais() != null) {
            dto.setPaisId(festivo.getPais().getId());
            dto.setPaisNombre(festivo.getPais().getNombre());
        }
        
        if (festivo.getTipoFestivo() != null) {
            dto.setTipoFestivoId(festivo.getTipoFestivo().getId());
            dto.setTipoFestivoNombre(festivo.getTipoFestivo().getTipo());
        }
        
        return dto;
    }
    
    /**
     * Convierte un DTO FestivoDTO a una entidad
     * @param festivoDTO DTO FestivoDTO
     * @return Entidad Festivo
     */
    public Festivo toEntity(FestivoDTO festivoDTO) {
        if (festivoDTO == null) {
            return null;
        }
        
        Festivo festivo = new Festivo();
        festivo.setId(festivoDTO.getId());
        festivo.setNombre(festivoDTO.getNombre());
        festivo.setDia(festivoDTO.getDia());
        festivo.setMes(festivoDTO.getMes());
        festivo.setDiasPascua(festivoDTO.getDiasPascua());
        
        // Buscar y asignar el país
        if (festivoDTO.getPaisId() != null) {
            Pais pais = paisRepository.findById(festivoDTO.getPaisId())
                    .orElseThrow(() -> new IllegalArgumentException("País no encontrado con ID: " + festivoDTO.getPaisId()));
            festivo.setPais(pais);
        }
        
        // Buscar y asignar el tipo de festivo
        if (festivoDTO.getTipoFestivoId() != null) {
            TipoFestivo tipoFestivo = tipoFestivoRepository.findById(festivoDTO.getTipoFestivoId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de festivo no encontrado con ID: " + festivoDTO.getTipoFestivoId()));
            festivo.setTipoFestivo(tipoFestivo);
        }
        
        return festivo;
    }
}
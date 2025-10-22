package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre TipoFestivo y TipoFestivoDTO
 */
@Component
public class TipoFestivoMapper {
    
    /**
     * Convierte una entidad TipoFestivo a un DTO
     * @param tipoFestivo Entidad TipoFestivo
     * @return DTO TipoFestivoDTO
     */
    public TipoFestivoDTO toDTO(TipoFestivo tipoFestivo) {
        if (tipoFestivo == null) {
            return null;
        }
        
        TipoFestivoDTO dto = new TipoFestivoDTO();
        dto.setId(tipoFestivo.getId());
        dto.setTipo(tipoFestivo.getTipo());
        return dto;
    }
    
    /**
     * Convierte un DTO TipoFestivoDTO a una entidad
     * @param tipoFestivoDTO DTO TipoFestivoDTO
     * @return Entidad TipoFestivo
     */
    public TipoFestivo toEntity(TipoFestivoDTO tipoFestivoDTO) {
        if (tipoFestivoDTO == null) {
            return null;
        }
        
        TipoFestivo tipoFestivo = new TipoFestivo();
        tipoFestivo.setId(tipoFestivoDTO.getId());
        tipoFestivo.setTipo(tipoFestivoDTO.getTipo());
        return tipoFestivo;
    }
}
package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.stereotype.Component;

@Component
public class TipoFestivoMapper {

    public TipoFestivoDTO toDTO(TipoFestivo tipoFestivo) {
        if (tipoFestivo == null) {
            return null;
        }
        
        TipoFestivoDTO dto = new TipoFestivoDTO();
        dto.setId(tipoFestivo.getId());
        dto.setTipo(tipoFestivo.getTipo());
        return dto;
    }

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

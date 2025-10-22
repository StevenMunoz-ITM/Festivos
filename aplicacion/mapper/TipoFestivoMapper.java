package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.dominio.entidades.TipoFestivo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<TipoFestivoDTO> toDTOList(List<TipoFestivo> tiposFestivo) {
        return tiposFestivo.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

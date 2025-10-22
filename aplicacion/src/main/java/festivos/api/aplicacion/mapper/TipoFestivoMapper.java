package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.dominio.entidades.TipoFestivo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoFestivoMapper {

    TipoFestivoDTO toDTO(TipoFestivo tipoFestivo);

    @Mapping(target = "festivos", ignore = true)
    TipoFestivo toEntity(TipoFestivoDTO tipoFestivoDTO);

    List<TipoFestivoDTO> toDTOList(List<TipoFestivo> tiposFestivo);
}

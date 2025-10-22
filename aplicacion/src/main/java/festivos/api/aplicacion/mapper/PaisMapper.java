package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.PaisDTO;
import festivos.api.dominio.entidades.Pais;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaisMapper {

    PaisDTO toDTO(Pais pais);

    @Mapping(target = "festivos", ignore = true)
    Pais toEntity(PaisDTO paisDTO);

    List<PaisDTO> toDTOList(List<Pais> paises);

    @Mapping(target = "festivos", ignore = true)
    void updateEntity(@MappingTarget Pais pais, PaisDTO paisDTO);
}

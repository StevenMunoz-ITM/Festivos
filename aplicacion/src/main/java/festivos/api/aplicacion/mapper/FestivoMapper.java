package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.entidades.TipoFestivo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FestivoMapper {
    
    @Autowired
    protected festivos.api.infraestructura.repositorios.PaisRepository paisRepository;
    
    @Autowired
    protected festivos.api.infraestructura.repositorios.TipoFestivoRepository tipoFestivoRepository;

    @Mapping(source = "pais.id", target = "paisId")
    @Mapping(source = "pais.nombre", target = "paisNombre")
    @Mapping(source = "tipoFestivo.id", target = "tipoFestivoId")
    @Mapping(source = "tipoFestivo.tipo", target = "tipoFestivoNombre")
    @Mapping(target = "fechaCelebracion", ignore = true)
    public abstract FestivoDTO toDTO(Festivo festivo);

    @Mapping(source = "paisId", target = "pais", qualifiedByName = "paisIdToPais")
    @Mapping(source = "tipoFestivoId", target = "tipoFestivo", qualifiedByName = "tipoFestivoIdToTipoFestivo")
    public abstract Festivo toEntity(FestivoDTO festivoDTO);

    public abstract List<FestivoDTO> toDTOList(List<Festivo> festivos);

    @Named("paisIdToPais")
    public Pais paisIdToPais(Long paisId) {
        if (paisId == null) return null;
        return paisRepository.findById(paisId)
                .orElseThrow(() -> new IllegalArgumentException("País no encontrado con ID: " + paisId));
    }

    @Named("tipoFestivoIdToTipoFestivo")
    public TipoFestivo tipoFestivoIdToTipoFestivo(Long tipoFestivoId) {
        if (tipoFestivoId == null) return null;
        return tipoFestivoRepository.findById(tipoFestivoId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de festivo no encontrado con ID: " + tipoFestivoId));
    }
}

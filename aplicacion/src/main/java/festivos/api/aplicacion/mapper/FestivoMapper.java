package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.FestivoDTO;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FestivoMapper {
    
    @Autowired
    private PaisRepository paisRepository;
    
    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;

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
        
        mapearPais(festivo, dto);
        mapearTipoFestivo(festivo, dto);
        
        return dto;
    }

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
        
        asignarPais(festivoDTO, festivo);
        asignarTipoFestivo(festivoDTO, festivo);
        
        return festivo;
    }

    public List<FestivoDTO> toDTOList(List<Festivo> festivos) {
        return festivos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private void mapearPais(Festivo festivo, FestivoDTO dto) {
        if (festivo.getPais() != null) {
            dto.setPaisId(festivo.getPais().getId());
            dto.setPaisNombre(festivo.getPais().getNombre());
        }
    }

    private void mapearTipoFestivo(Festivo festivo, FestivoDTO dto) {
        if (festivo.getTipoFestivo() != null) {
            dto.setTipoFestivoId(festivo.getTipoFestivo().getId());
            dto.setTipoFestivoNombre(festivo.getTipoFestivo().getTipo());
        }
    }

    private void asignarPais(FestivoDTO festivoDTO, Festivo festivo) {
        if (festivoDTO.getPaisId() != null) {
            Pais pais = paisRepository.findById(festivoDTO.getPaisId())
                    .orElseThrow(() -> new IllegalArgumentException("País no encontrado con ID: " + festivoDTO.getPaisId()));
            festivo.setPais(pais);
        }
    }

    private void asignarTipoFestivo(FestivoDTO festivoDTO, Festivo festivo) {
        if (festivoDTO.getTipoFestivoId() != null) {
            TipoFestivo tipoFestivo = tipoFestivoRepository.findById(festivoDTO.getTipoFestivoId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de festivo no encontrado con ID: " + festivoDTO.getTipoFestivoId()));
            festivo.setTipoFestivo(tipoFestivo);
        }
    }
}

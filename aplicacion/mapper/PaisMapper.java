package festivos.api.aplicacion.mapper;

import festivos.api.dominio.dto.PaisDTO;
import festivos.api.dominio.entidades.Pais;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para conversión entre Pais y PaisDTO
 */
@Component
public class PaisMapper {
    
    /**
     * Convierte de Entity a DTO
     */
    public PaisDTO toDTO(Pais pais) {
        if (pais == null) return null;
        
        return new PaisDTO(pais.getId(), pais.getNombre());
    }
    
    /**
     * Convierte de DTO a Entity
     */
    public Pais toEntity(PaisDTO paisDTO) {
        if (paisDTO == null) return null;
        
        Pais pais = new Pais(paisDTO.getNombre());
        pais.setId(paisDTO.getId());
        return pais;
    }
    
    /**
     * Convierte lista de Entities a DTOs
     */
    public List<PaisDTO> toDTOList(List<Pais> paises) {
        return paises.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntity(Pais pais, PaisDTO paisDTO) {
        if (pais != null && paisDTO != null) {
            pais.setNombre(paisDTO.getNombre());
        }
    }
}
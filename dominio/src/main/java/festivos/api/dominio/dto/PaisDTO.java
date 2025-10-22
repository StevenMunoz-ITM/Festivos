package festivos.api.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PaisDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre del país es obligatorio")
    @Size(max = 100, message = "El nombre del país no puede exceder 100 caracteres")
    private String nombre;
    
    public PaisDTO() {}
    
    public PaisDTO(String nombre) {
        this.nombre = nombre;
    }
    
    public PaisDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

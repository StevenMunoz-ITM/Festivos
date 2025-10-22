package festivos.api.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoFestivoDTO {
    
    private Long id;
    
    @NotBlank(message = "El tipo de festivo es obligatorio")
    @Size(max = 100, message = "El tipo de festivo no puede exceder 100 caracteres")
    private String tipo;
    
    // Constructores
    public TipoFestivoDTO() {}
    
    public TipoFestivoDTO(String tipo) {
        this.tipo = tipo;
    }
    
    public TipoFestivoDTO(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
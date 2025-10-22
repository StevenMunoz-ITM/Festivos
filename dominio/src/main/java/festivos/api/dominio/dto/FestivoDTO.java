package festivos.api.dominio.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class FestivoDTO {
    
    private Long id;
    
    @NotNull(message = "El ID del país es obligatorio")
    private Long paisId;
    
    private String paisNombre;
    
    @NotBlank(message = "El nombre del festivo es obligatorio")
    @Size(max = 100, message = "El nombre del festivo no puede exceder 100 caracteres")
    private String nombre;
    
    @Min(value = 1, message = "El día debe estar entre 1 y 31")
    @Max(value = 31, message = "El día debe estar entre 1 y 31")
    private Integer dia;
    
    @Min(value = 1, message = "El mes debe estar entre 1 y 12")
    @Max(value = 12, message = "El mes debe estar entre 1 y 12")
    private Integer mes;
    
    private Integer diasPascua;
    
    @NotNull(message = "El ID del tipo de festivo es obligatorio")
    private Long tipoFestivoId;
    
    private String tipoFestivoNombre;
    
    private LocalDate fechaCelebracion;
    
    public FestivoDTO() {}
    
    public FestivoDTO(String nombre, Long paisId, Long tipoFestivoId) {
        this.nombre = nombre;
        this.paisId = paisId;
        this.tipoFestivoId = tipoFestivoId;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPaisId() {
        return paisId;
    }
    
    public void setPaisId(Long paisId) {
        this.paisId = paisId;
    }
    
    public String getPaisNombre() {
        return paisNombre;
    }
    
    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getDia() {
        return dia;
    }
    
    public void setDia(Integer dia) {
        this.dia = dia;
    }
    
    public Integer getMes() {
        return mes;
    }
    
    public void setMes(Integer mes) {
        this.mes = mes;
    }
    
    public Integer getDiasPascua() {
        return diasPascua;
    }
    
    public void setDiasPascua(Integer diasPascua) {
        this.diasPascua = diasPascua;
    }
    
    public Long getTipoFestivoId() {
        return tipoFestivoId;
    }
    
    public void setTipoFestivoId(Long tipoFestivoId) {
        this.tipoFestivoId = tipoFestivoId;
    }
    
    public String getTipoFestivoNombre() {
        return tipoFestivoNombre;
    }
    
    public void setTipoFestivoNombre(String tipoFestivoNombre) {
        this.tipoFestivoNombre = tipoFestivoNombre;
    }
    
    public LocalDate getFechaCelebracion() {
        return fechaCelebracion;
    }
    
    public void setFechaCelebracion(LocalDate fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }
}

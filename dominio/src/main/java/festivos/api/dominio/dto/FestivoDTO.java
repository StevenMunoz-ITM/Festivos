package festivos.api.dominio.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class FestivoDTO {
    
    private Long id;
    
    @NotNull(message = "El ID del país es obligatorio.")
    private Long paisId;
    
    private String paisNombre;
    
    @NotBlank(message = "El nombre del festivo es obligatorio")
    @Size(max = 100, message = "El nombre del festivo no puede exceder 100 caracteres.")
    private String nombre;
    
    @Min(value = 1, message = "El día debe estar entre 1 y 31.")
    @Max(value = 31, message = "El día debe estar entre 1 y 31.")
    private Integer dia;
    
    @Min(value = 1, message = "El mes debe estar entre 1 y 12.")
    @Max(value = 12, message = "El mes debe estar entre 1 y 12.")
    private Integer mes;
    
    private Integer diasPascua;
    
    @NotNull(message = "El ID del tipo de festivo es obligatorio.")
    private Long tipoFestivoId;
    
    private String tipoFestivoNombre;
    
    private LocalDate fechaCelebracion;
    
    public FestivoDTO() {}
    
    public FestivoDTO(final String nombre, final Long paisId, final Long tipoFestivoId) {
        this.nombre = nombre;
        this.paisId = paisId;
        this.tipoFestivoId = tipoFestivoId;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(final Long id) {
        this.id = id;
    }
    
    public Long getPaisId() {
        return this.paisId;
    }
    
    public void setPaisId(final Long paisId) {
        this.paisId = paisId;
    }
    
    public String getPaisNombre() {
        return this.paisNombre;
    }
    
    public void setPaisNombre(final String paisNombre) {
        this.paisNombre = paisNombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getDia() {
        return this.dia;
    }
    
    public void setDia(final Integer dia) {
        this.dia = dia;
    }
    
    public Integer getMes() {
        return this.mes;
    }
    
    public void setMes(final Integer mes) {
        this.mes = mes;
    }
    
    public Integer getDiasPascua() {
        return this.diasPascua;
    }
    
    public void setDiasPascua(final Integer diasPascua) {
        this.diasPascua = diasPascua;
    }
    
    public Long getTipoFestivoId() {
        return this.tipoFestivoId;
    }
    
    public void setTipoFestivoId(final Long tipoFestivoId) {
        this.tipoFestivoId = tipoFestivoId;
    }
    
    public String getTipoFestivoNombre() {
        return this.tipoFestivoNombre;
    }
    
    public void setTipoFestivoNombre(final String tipoFestivoNombre) {
        this.tipoFestivoNombre = tipoFestivoNombre;
    }
    
    public LocalDate getFechaCelebracion() {
        return this.fechaCelebracion;
    }
    
    public void setFechaCelebracion(final LocalDate fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }
}

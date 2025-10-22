package festivos.api.dominio.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "festivo")
public class Festivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El país es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais", nullable = false)
    private Pais pais;
    
    @NotBlank(message = "El nombre del festivo es obligatorio")
    @Size(max = 100, message = "El nombre del festivo no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Min(value = 1, message = "El día debe estar entre 1 y 31")
    @Max(value = 31, message = "El día debe estar entre 1 y 31")
    @Column(name = "dia")
    private Integer dia;
    
    @Min(value = 1, message = "El mes debe estar entre 1 y 12")
    @Max(value = 12, message = "El mes debe estar entre 1 y 12")
    @Column(name = "mes")
    private Integer mes;
    
    @Column(name = "dias_pascua")
    private Integer diasPascua;
    
    @NotNull(message = "El tipo de festivo es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoFestivo tipoFestivo;
    

    public Festivo() {}
    
    public Festivo(Pais pais, String nombre, Integer dia, Integer mes, Integer diasPascua, TipoFestivo tipoFestivo) {
        this.pais = pais;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diasPascua = diasPascua;
        this.tipoFestivo = tipoFestivo;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
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
    
    public TipoFestivo getTipoFestivo() {
        return tipoFestivo;
    }
    
    public void setTipoFestivo(TipoFestivo tipoFestivo) {
        this.tipoFestivo = tipoFestivo;
    }
    
    @Override
    public String toString() {
        return "Festivo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dia=" + dia +
                ", mes=" + mes +
                ", diasPascua=" + diasPascua +
                '}';
    }
}
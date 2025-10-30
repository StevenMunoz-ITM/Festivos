package festivos.api.dominio.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "festivo")
public class Festivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre", length = 100, nullable = false)
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Column(name = "mes", nullable = false)
    @NotNull(message = "El mes no puede ser nulo")
    private int mes;
    
    @Column(name = "dia", nullable = false)
    @NotNull(message = "El día no puede ser nulo")
    private int dia;
    
    @Column(name = "diaspascua")
    private Integer diasPascua;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtipo", referencedColumnName = "id", nullable = false)
    @NotNull(message = "El tipo de festivo no puede ser nulo")
    private TipoFestivo tipo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpais", referencedColumnName = "id", nullable = false)
    @NotNull(message = "El país no puede ser nulo")
    private Pais pais;
    
    public Festivo() {
    }
    
    public Festivo(int id, String nombre, int mes, int dia, Integer diasPascua, TipoFestivo tipo, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.mes = mes;
        this.dia = dia;
        this.diasPascua = diasPascua;
        this.tipo = tipo;
        this.pais = pais;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getMes() {
        return mes;
    }
    
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    public int getDia() {
        return dia;
    }
    
    public void setDia(int dia) {
        this.dia = dia;
    }
    
    public Integer getDiasPascua() {
        return diasPascua;
    }
    
    public void setDiasPascua(Integer diasPascua) {
        this.diasPascua = diasPascua;
    }
    
    public TipoFestivo getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoFestivo tipo) {
        this.tipo = tipo;
    }
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    @Override
    public String toString() {
        return "Festivo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", mes=" + mes +
                ", dia=" + dia +
                ", diasPascua=" + diasPascua +
                ", tipo=" + tipo +
                ", pais=" + pais +
                '}';
    }
}
package festivos.api.dominio.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "festivo")
public class Festivo {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "idpais", referencedColumnName = "id", nullable = false)
    private Pais pais;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "dia", nullable = false)
    private int dia;
    
    @Column(name = "mes", nullable = false)
    private int mes;
    
    @Column(name = "diaspascua", nullable = false)
    private int diasPascua;
    
    @ManyToOne
    @JoinColumn(name = "idtipo", referencedColumnName = "id", nullable = false)
    private TipoFestivo tipo;
    
    public Festivo() {
    }
    
    public Festivo(int id, Pais pais, String nombre, int dia, int mes, int diasPascua, TipoFestivo tipo) {
        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diasPascua = diasPascua;
        this.tipo = tipo;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
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
    
    public int getDia() {
        return dia;
    }
    
    public void setDia(int dia) {
        this.dia = dia;
    }
    
    public int getMes() {
        return mes;
    }
    
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    public int getDiasPascua() {
        return diasPascua;
    }
    
    public void setDiasPascua(int diasPascua) {
        this.diasPascua = diasPascua;
    }
    
    public TipoFestivo getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoFestivo tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return "Festivo{" +
                "id=" + id +
                ", pais=" + pais +
                ", nombre='" + nombre + '\'' +
                ", dia=" + dia +
                ", mes=" + mes +
                ", diasPascua=" + diasPascua +
                ", tipo=" + tipo +
                '}';
    }
}
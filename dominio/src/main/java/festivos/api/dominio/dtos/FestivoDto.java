package festivos.api.dominio.dtos;

import java.time.LocalDate;

public class FestivoDto {
    
    private int id;
    private String nombre;
    private LocalDate fecha;
    private String tipo;
    private String pais;
    private boolean esFechaFija;
    
    public FestivoDto() {
    }
    
    public FestivoDto(int id, String nombre, LocalDate fecha, String tipo, String pais, boolean esFechaFija) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = tipo;
        this.pais = pais;
        this.esFechaFija = esFechaFija;
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
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getPais() {
        return pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public boolean isEsFechaFija() {
        return esFechaFija;
    }
    
    public void setEsFechaFija(boolean esFechaFija) {
        this.esFechaFija = esFechaFija;
    }
    
    @Override
    public String toString() {
        return "FestivoDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", tipo='" + tipo + '\'' +
                ", pais='" + pais + '\'' +
                ", esFechaFija=" + esFechaFija +
                '}';
    }
}
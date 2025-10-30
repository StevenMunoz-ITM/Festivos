package festivos.api.dominio.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pais")
public class Pais {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre", length = 80, nullable = false)
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 80, message = "El nombre no puede exceder 80 caracteres")
    private String nombre;
    
    public Pais() {
    }
    
    public Pais(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    
    @Override
    public String toString() {
        return "Pais{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
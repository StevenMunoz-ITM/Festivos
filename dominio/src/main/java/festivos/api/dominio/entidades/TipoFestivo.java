package festivos.api.dominio.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tipofestivo")
public class TipoFestivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "tipo", length = 100, nullable = false)
    @NotEmpty(message = "El tipo no puede estar vacío")
    @Size(max = 100, message = "El tipo no puede exceder 100 caracteres")
    private String tipo;
    
    public TipoFestivo() {
    }
    
    public TipoFestivo(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return "TipoFestivo{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
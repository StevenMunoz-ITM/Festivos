package festivos.api.dominio.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tipo_festivo")
public class TipoFestivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El tipo de festivo es obligatorio")
    @Size(max = 100, message = "El tipo de festivo no puede exceder 100 caracteres")
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;
    
    @OneToMany(mappedBy = "tipoFestivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Festivo> festivos;
    
    // Constructores
    public TipoFestivo() {}
    
    public TipoFestivo(String tipo) {
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
    
    public List<Festivo> getFestivos() {
        return festivos;
    }
    
    public void setFestivos(List<Festivo> festivos) {
        this.festivos = festivos;
    }
    
    @Override
    public String toString() {
        return "TipoFestivo{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
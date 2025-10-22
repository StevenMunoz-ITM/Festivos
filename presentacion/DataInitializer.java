package festivos.api.presentacion;

import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.entidades.Pais;
import festivos.api.dominio.entidades.TipoFestivo;
import festivos.api.infraestructura.repositorios.FestivoRepository;
import festivos.api.infraestructura.repositorios.PaisRepository;
import festivos.api.infraestructura.repositorios.TipoFestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;

    @Autowired
    private FestivoRepository festivoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializar si no hay datos
        if (paisRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Crear país Colombia
        Pais colombia = new Pais("Colombia");
        colombia = paisRepository.save(colombia);

        // Crear tipos de festivos según especificación oficial
        TipoFestivo tipo1 = new TipoFestivo("Fijo");
        TipoFestivo tipo2 = new TipoFestivo("Ley de \"Puente festivo\"");
        TipoFestivo tipo3 = new TipoFestivo("Basado en el domingo de pascua");
        TipoFestivo tipo4 = new TipoFestivo("Basado en el domingo de pascua y Ley de \"Puente festivo\"");

        tipo1 = tipoFestivoRepository.save(tipo1);
        tipo2 = tipoFestivoRepository.save(tipo2);
        tipo3 = tipoFestivoRepository.save(tipo3);
        tipo4 = tipoFestivoRepository.save(tipo4);

        // Crear festivos de Colombia según la tabla proporcionada
        crearFestivosColumbia(colombia, tipo1, tipo2, tipo3, tipo4);
    }

    private void crearFestivosColumbia(Pais colombia, TipoFestivo tipo1, TipoFestivo tipo2, 
                                     TipoFestivo tipo3, TipoFestivo tipo4) {
        
        // Festivos de fecha fija (Tipo 1)
        festivoRepository.save(new Festivo(colombia, "Año nuevo", 1, 1, null, tipo1));
        festivoRepository.save(new Festivo(colombia, "Día del Trabajo", 1, 5, null, tipo1));
        festivoRepository.save(new Festivo(colombia, "Independencia Colombia", 20, 7, null, tipo1));
        festivoRepository.save(new Festivo(colombia, "Batalla de Boyacá", 7, 8, null, tipo1));
        festivoRepository.save(new Festivo(colombia, "Inmaculada Concepción", 8, 12, null, tipo1));
        festivoRepository.save(new Festivo(colombia, "Navidad", 25, 12, null, tipo1));

        // Festivos de fecha fija transferibles (Tipo 2)
        festivoRepository.save(new Festivo(colombia, "Santos Reyes", 6, 1, null, tipo2));
        festivoRepository.save(new Festivo(colombia, "San José", 19, 3, null, tipo2));
        festivoRepository.save(new Festivo(colombia, "San Pedro y San Pablo", 29, 6, null, tipo2));
        festivoRepository.save(new Festivo(colombia, "Asunción de la Virgen", 15, 8, null, tipo2));
        festivoRepository.save(new Festivo(colombia, "Día de la Raza", 12, 10, null, tipo2));
        festivoRepository.save(new Festivo(colombia, "Todos los santos", 1, 11, null, tipo2));
        festivoRepository.save(new Festivo(colombia, "Independencia de Cartagena", 11, 11, null, tipo2));

        // Festivos basados en Pascua (Tipo 3)
        festivoRepository.save(new Festivo(colombia, "Jueves Santo", null, null, -3, tipo3));
        festivoRepository.save(new Festivo(colombia, "Viernes Santo", null, null, -2, tipo3));
        festivoRepository.save(new Festivo(colombia, "Domingo de Pascua", null, null, 0, tipo3));

        // Festivos basados en Pascua transferibles (Tipo 4)
        festivoRepository.save(new Festivo(colombia, "Ascensión del Señor", null, null, 40, tipo4));
        festivoRepository.save(new Festivo(colombia, "Corpus Christi", null, null, 61, tipo4));
        festivoRepository.save(new Festivo(colombia, "Sagrado Corazón de Jesús", null, null, 68, tipo4));
    }
}
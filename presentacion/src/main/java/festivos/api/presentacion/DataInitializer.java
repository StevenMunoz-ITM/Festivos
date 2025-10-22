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
        if (paisRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        Pais colombia = new Pais("Colombia");
        colombia = paisRepository.save(colombia);

        TipoFestivo tipo1 = new TipoFestivo("Fijo");
        TipoFestivo tipo2 = new TipoFestivo("Ley de \"Puente festivo\"");
        TipoFestivo tipo3 = new TipoFestivo("Basado en el domingo de pascua");
        TipoFestivo tipo4 = new TipoFestivo("Basado en el domingo de pascua y Ley de \"Puente festivo\"");

        tipo1 = tipoFestivoRepository.save(tipo1);
        tipo2 = tipoFestivoRepository.save(tipo2);
        tipo3 = tipoFestivoRepository.save(tipo3);
        tipo4 = tipoFestivoRepository.save(tipo4);

        crearFestivosColumbia(colombia, tipo1, tipo2, tipo3, tipo4);
    }

    private void crearFestivosColumbia(Pais colombia, TipoFestivo tipo1, TipoFestivo tipo2, 
                                     TipoFestivo tipo3, TipoFestivo tipo4) {
        
        crearFestivosFijos(colombia, tipo1);
        crearFestivosPuente(colombia, tipo2);
        crearFestivosPascua(colombia, tipo3);
        crearFestivosPascuaPuente(colombia, tipo4);
    }

    private void crearFestivosFijos(Pais colombia, TipoFestivo tipo) {
        String[][] festivosFijos = {
            {"Año nuevo", "1", "1"},
            {"Día del Trabajo", "1", "5"},
            {"Independencia Colombia", "20", "7"},
            {"Batalla de Boyacá", "7", "8"},
            {"Inmaculada Concepción", "8", "12"},
            {"Navidad", "25", "12"}
        };
        
        for (String[] festivo : festivosFijos) {
            festivoRepository.save(new Festivo(colombia, festivo[0], 
                Integer.parseInt(festivo[1]), Integer.parseInt(festivo[2]), null, tipo));
        }
    }

    private void crearFestivosPuente(Pais colombia, TipoFestivo tipo) {
        String[][] festivosPuente = {
            {"Santos Reyes", "6", "1"},
            {"San José", "19", "3"},
            {"San Pedro y San Pablo", "29", "6"},
            {"Asunción de la Virgen", "15", "8"},
            {"Día de la Raza", "12", "10"},
            {"Todos los santos", "1", "11"},
            {"Independencia de Cartagena", "11", "11"}
        };
        
        for (String[] festivo : festivosPuente) {
            festivoRepository.save(new Festivo(colombia, festivo[0], 
                Integer.parseInt(festivo[1]), Integer.parseInt(festivo[2]), null, tipo));
        }
    }

    private void crearFestivosPascua(Pais colombia, TipoFestivo tipo) {
        String[][] festivosPascua = {
            {"Jueves Santo", "-3"},
            {"Viernes Santo", "-2"},
            {"Domingo de Pascua", "0"}
        };
        
        for (String[] festivo : festivosPascua) {
            festivoRepository.save(new Festivo(colombia, festivo[0], 
                null, null, Integer.parseInt(festivo[1]), tipo));
        }
    }

    private void crearFestivosPascuaPuente(Pais colombia, TipoFestivo tipo) {
        String[][] festivosPascuaPuente = {
            {"Ascensión del Señor", "40"},
            {"Corpus Christi", "61"},
            {"Sagrado Corazón de Jesús", "68"}
        };
        
        for (String[] festivo : festivosPascuaPuente) {
            festivoRepository.save(new Festivo(colombia, festivo[0], 
                null, null, Integer.parseInt(festivo[1]), tipo));
        }
    }
}

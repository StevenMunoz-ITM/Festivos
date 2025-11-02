package festivos.api.aplicacion.servicios;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import festivos.api.core.servicios.IFestivoServicio;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.dtos.FestivoDto;
import festivos.api.dominio.dtos.ValidacionFestivoDto;
import festivos.api.infraestructura.repositorios.IFestivoRepositorio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class FestivoServicio implements IFestivoServicio {
    
    private static final Logger log = LoggerFactory.getLogger(FestivoServicio.class);
    private final IFestivoRepositorio repositorio;
    
    public FestivoServicio(IFestivoRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    @Override
    public List<Festivo> listar() {
        return repositorio.findAll();
    }
    
    @Override
    public Festivo obtener(int id) {
        return repositorio.findById(id).orElse(null);
    }
    
    @Override
    public List<Festivo> buscar(String nombre) {
        return repositorio.buscar(nombre);
    }
    
    @Override
    public List<Festivo> listarPorPais(int idPais) {
        return repositorio.listarPorPais(idPais);
    }
    
    @Override
    public List<Festivo> listarPorTipo(int idTipo) {
        return repositorio.listarPorTipo(idTipo);
    }
    
    @Override
    public List<Festivo> listarPorPaisYMes(int idPais, int mes) {
        return repositorio.listarPorPaisYMes(idPais, mes);
    }
    
    @Override
    public Festivo agregar(Festivo festivo) {
        festivo.setId(0);
        return repositorio.save(festivo);
    }
    
    @Override
    public Festivo modificar(Festivo festivo) {
        if (repositorio.findById(festivo.getId()).isEmpty()) {
            return null;
        }
        return repositorio.save(festivo);
    }
    
    @Override
    public boolean eliminar(int id) {
        try {
            repositorio.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public boolean esFestivo(int idPais, int dia, int mes, int anio) {
        log.error("*** INICIO esFestivo({}, {}, {}, {}) ***", idPais, dia, mes, anio);
        
        // Crear la fecha que queremos validar
        LocalDate fechaConsulta = LocalDate.of(anio, mes, dia);
        log.error("Fecha a validar: {} ({})", fechaConsulta, fechaConsulta.getDayOfWeek());
        
        // Obtener todos los festivos del país
        List<Festivo> festivosPais = repositorio.listarPorPais(idPais);
        log.error("Total festivos para el país {}: {}", idPais, festivosPais.size());
        
        // Verificar cada festivo
        for (int i = 0; i < festivosPais.size(); i++) {
            Festivo festivo = festivosPais.get(i);
            log.error("--- Verificando festivo {}/{} ---", i+1, festivosPais.size());
            log.error("Nombre: {}", festivo.getNombre());
            log.error("Tipo: {} ({})", festivo.getTipo().getId(), festivo.getTipo().getTipo());
            
            // Calcular la fecha real del festivo para este año
            LocalDate fechaFestivo = obtenerFechaFestivo(festivo, anio);
            
            // Comparar fechas completas
            if (fechaFestivo.equals(fechaConsulta)) {
                log.error("*** ¡FESTIVO ENCONTRADO! ***");
                log.error("Festivo: {}", festivo.getNombre());
                log.error("Fecha calculada: {}", fechaFestivo);
                log.error("Fecha consultada: {}", fechaConsulta);
                return true;
            } else {
                log.error("No coincide - Fecha festivo: {} vs Consulta: {}", fechaFestivo, fechaConsulta);
            }
        }
        
        log.error("*** NO ES FESTIVO ***");
        return false;
    }
    
    @Override
    public LocalDate obtenerFechaFestivo(Festivo festivo, int anio) {
        log.error("=== CALCULANDO FECHA FESTIVO ===");
        log.error("Festivo: {}", festivo.getNombre());
        log.error("Tipo ID: {}", festivo.getTipo().getId());
        log.error("Año: {}", anio);
        
        LocalDate resultado;
        
        switch (festivo.getTipo().getId()) {
            case 1:
                resultado = calcularTipoFijo(festivo, anio);
                break;
            case 2:
                resultado = calcularTipoPuenteFestivo(festivo, anio);
                break;
            case 3:
                resultado = calcularTipoBasadoEnPascua(festivo, anio);
                break;
            case 4:
                resultado = calcularTipoPascuaConPuente(festivo, anio);
                break;
            case 5:
                resultado = calcularTipoPuenteViernes(festivo, anio);
                break;
            default:
                log.warn("TIPO DESCONOCIDO, usando fecha fija");
                resultado = calcularTipoFijo(festivo, anio);
                break;
        }
        
        log.error("Fecha calculada: {}", resultado);
        log.error("=== FIN CALCULO FECHA ===");
        
        return resultado;
    }
    
    private LocalDate obtenerFechaOriginal(Festivo festivo, int anio) {
        return LocalDate.of(anio, festivo.getMes(), festivo.getDia());
    }
    
    private LocalDate aplicarTrasladoConLog(LocalDate fechaOriginal, String tipoTraslado) {
        log.error("Fecha original: {} ({})", fechaOriginal, fechaOriginal.getDayOfWeek());
        LocalDate fechaTraladada = tipoTraslado.equals("lunes") ? 
            trasladarALunes(fechaOriginal) : trasladarAViernes(fechaOriginal);
        log.error("Fecha trasladada a {}: {}", tipoTraslado, fechaTraladada);
        return fechaTraladada;
    }
    
    private LocalDate calcularTipoFijo(Festivo festivo, int anio) {
        log.error("Calculando TIPO 1 - FIJO: {}/{}/{}", festivo.getDia(), festivo.getMes(), anio);
        return obtenerFechaOriginal(festivo, anio);
    }
    
    private LocalDate calcularTipoPuenteFestivo(Festivo festivo, int anio) {
        log.error("Calculando TIPO 2 - PUENTE FESTIVO");
        LocalDate fechaOriginal = obtenerFechaOriginal(festivo, anio);
        return aplicarTrasladoConLog(fechaOriginal, "lunes");
    }
    
    private LocalDate calcularTipoBasadoEnPascua(Festivo festivo, int anio) {
        log.error("Calculando TIPO 3 - BASADO EN PASCUA");
        log.error("Días desde Pascua: {}", festivo.getDiasPascua());
        LocalDate domingoPascua = calcularDomingoPascua(anio);
        log.error("Domingo de Pascua: {}", domingoPascua);
        LocalDate fechaCalculada = domingoPascua.plusDays(festivo.getDiasPascua());
        log.error("Fecha calculada (Pascua + {} días): {}", festivo.getDiasPascua(), fechaCalculada);
        return fechaCalculada;
    }
    
    private LocalDate calcularTipoPascuaConPuente(Festivo festivo, int anio) {
        log.error("Calculando TIPO 4 - PASCUA CON PUENTE");
        LocalDate fechaPascua = calcularTipoBasadoEnPascua(festivo, anio);
        return aplicarTrasladoConLog(fechaPascua, "lunes");
    }
    
    private LocalDate calcularTipoPuenteViernes(Festivo festivo, int anio) {
        log.error("Calculando TIPO 5 - PUENTE VIERNES");
        LocalDate fechaOriginal = obtenerFechaOriginal(festivo, anio);
        log.error("Fecha original: {} ({})", fechaOriginal, fechaOriginal.getDayOfWeek());
        LocalDate fechaTraladada = trasladarAViernes(fechaOriginal);
        log.error("Fecha trasladada a viernes: {}", fechaTraladada);
        return fechaTraladada;
    }
    
    private LocalDate calcularDomingoPascua(int anio) {
        int a = anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (19 * a + 24) % 30;
        
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        log.info("=== CÁLCULO PASCUA {} ===", anio);
        log.error("a={}, b={}, c={}, d={}", a, b, c, d);
        log.error("dias={}", dias);
        
        LocalDate domingoRamos = LocalDate.of(anio, 3, 15).plusDays(dias);
        log.error("Domingo de Ramos: {}", domingoRamos);
        
        LocalDate pascua = domingoRamos.plusDays(7);
        log.error("Domingo de Pascua: {}", pascua);
        
        return pascua;
    }
    
    private LocalDate trasladarALunes(LocalDate fecha) {
        if (fecha.getDayOfWeek() == DayOfWeek.MONDAY) {
            return fecha;
        }
        
        int diasHastaLunes = DayOfWeek.MONDAY.getValue() - fecha.getDayOfWeek().getValue();
        if (diasHastaLunes <= 0) {
            diasHastaLunes += 7;
        }
        
        return fecha.plusDays(diasHastaLunes);
    }
    
    private LocalDate trasladarAViernes(LocalDate fecha) {
        if (fecha.getDayOfWeek() == DayOfWeek.FRIDAY) {
            return fecha;
        }
        
        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return fecha.minusDays(1);
        }
        if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return fecha.minusDays(2);
        }
        
        int diasHastaViernes = DayOfWeek.FRIDAY.getValue() - fecha.getDayOfWeek().getValue();
        if (diasHastaViernes < 0) {
            diasHastaViernes += 7;
        }
        
        return fecha.plusDays(diasHastaViernes);
    }
    
    @Override
    public ValidacionFestivoDto validarFestivo(int idPais, int dia, int mes, int anio) {
        List<Festivo> festivosPais = repositorio.listarPorPais(idPais);
        
        for (Festivo festivo : festivosPais) {
            LocalDate fechaFestivo = obtenerFechaFestivo(festivo, anio);
            if (fechaFestivo.getDayOfMonth() == dia && fechaFestivo.getMonthValue() == mes) {
                return new ValidacionFestivoDto(
                    true, 
                    "La fecha corresponde al festivo: " + festivo.getNombre(),
                    festivo.getNombre(),
                    festivo.getTipo().getTipo()
                );
            }
        }
        
        return new ValidacionFestivoDto(false, "La fecha no corresponde a ningún día festivo");
    }
    
    @Override
    public List<FestivoDto> listarFestivosPorAnio(int idPais, int anio) {
        List<Festivo> festivos = repositorio.listarPorPais(idPais);
        List<FestivoDto> festivosDto = new ArrayList<>();
        
        for (Festivo festivo : festivos) {
            LocalDate fechaCalculada = obtenerFechaFestivo(festivo, anio);
            boolean esFechaFija = festivo.getTipo().getId() == 1;
            
            festivosDto.add(new FestivoDto(
                festivo.getId(),
                festivo.getNombre(),
                fechaCalculada,
                festivo.getTipo().getTipo(),
                festivo.getPais().getNombre(),
                esFechaFija
            ));
        }
        
        festivosDto.sort((f1, f2) -> f1.getFecha().compareTo(f2.getFecha()));
        
        return festivosDto;
    }
}
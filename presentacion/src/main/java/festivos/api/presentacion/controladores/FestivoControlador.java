package festivos.api.presentacion.controladores;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import festivos.api.dominio.entidades.Festivo;
import festivos.api.dominio.dtos.FestivoDto;
import java.util.Map;
import java.util.HashMap;
import festivos.api.aplicacion.servicios.FestivoServicio;
import festivos.api.core.servicios.IFestivoServicio;

@RestController
@RequestMapping("/api/festivos")
@CrossOrigin(origins = "*")
public class FestivoControlador {

    private final IFestivoServicio servicio;
    private final FestivoServicio festivoServicio;

    public FestivoControlador(IFestivoServicio servicio, FestivoServicio festivoServicio) {
        this.servicio = servicio;
        this.festivoServicio = festivoServicio;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Festivo>> listar() {
        List<Festivo> festivos = servicio.listar();
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Festivo> obtener(@PathVariable int id) {
        Festivo festivo = servicio.obtener(id);
        if (festivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(festivo);
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Festivo>> buscar(@PathVariable String nombre) {
        List<Festivo> festivos = servicio.buscar(nombre);
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/pais/{idPais}")
    public ResponseEntity<List<Festivo>> listarPorPais(@PathVariable int idPais) {
        List<Festivo> festivos = servicio.listarPorPais(idPais);
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/tipo/{idTipo}")
    public ResponseEntity<List<Festivo>> listarPorTipo(@PathVariable int idTipo) {
        List<Festivo> festivos = servicio.listarPorTipo(idTipo);
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/verificar/{ano}/{mes}/{dia}/{idPais}")
    public ResponseEntity<String> esFestivo(
            @PathVariable int ano,
            @PathVariable int mes,
            @PathVariable int dia,
            @PathVariable int idPais) {
        try {
            LocalDate fecha = LocalDate.of(ano, mes, dia);
            boolean esFestivo = servicio.esFestivo(fecha, idPais);
            
            String mensaje = esFestivo ? "Es festivo" : "No es festivo";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: Fecha inválida o país no encontrado");
        }
    }

    @GetMapping("/obtener/{ano}/{idPais}")
    public ResponseEntity<List<LocalDate>> obtenerFestivosPorAno(
            @PathVariable int ano,
            @PathVariable int idPais) {
        List<LocalDate> festivos = servicio.obtenerFestivosPorAno(ano, idPais);
        return ResponseEntity.ok(festivos);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Festivo> agregar(@RequestBody Festivo festivo) {
        try {
            Festivo nuevoFestivo = servicio.agregar(festivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoFestivo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<Festivo> modificar(@RequestBody Festivo festivo) {
        try {
            Festivo festivoModificado = servicio.modificar(festivo);
            if (festivoModificado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(festivoModificado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable int id) {
        boolean eliminado = servicio.eliminar(id);
        return ResponseEntity.ok(eliminado);
    }

    @GetMapping("/pascua/{ano}")
    public ResponseEntity<LocalDate> calcularPascua(@PathVariable int ano) {
        try {
            LocalDate pascua = servicio.calcularPascua(ano);
            return ResponseEntity.ok(pascua);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/domingo-ramos/{ano}")
    public ResponseEntity<LocalDate> calcularDomingoRamos(@PathVariable int ano) {
        try {
            LocalDate domingoRamos = servicio.calcularDomingoRamos(ano);
            return ResponseEntity.ok(domingoRamos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/dto/listar")
    public ResponseEntity<List<FestivoDto>> listarDto() {
        List<FestivoDto> festivos = festivoServicio.listarDto();
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/dto/obtener/{id}")
    public ResponseEntity<FestivoDto> obtenerDto(@PathVariable int id) {
        FestivoDto festivo = festivoServicio.obtenerDto(id);
        if (festivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(festivo);
    }

    @GetMapping("/dto/buscar/{nombre}")
    public ResponseEntity<List<FestivoDto>> buscarDto(@PathVariable String nombre) {
        List<FestivoDto> festivos = festivoServicio.buscarDto(nombre);
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/dto/pais/{idPais}")
    public ResponseEntity<List<FestivoDto>> listarPorPaisDto(@PathVariable int idPais) {
        List<FestivoDto> festivos = festivoServicio.listarPorPaisDto(idPais);
        return ResponseEntity.ok(festivos);
    }

    @GetMapping("/dto/tipo/{idTipo}")
    public ResponseEntity<List<FestivoDto>> listarPorTipoDto(@PathVariable int idTipo) {
        List<FestivoDto> festivos = festivoServicio.listarPorTipoDto(idTipo);
        return ResponseEntity.ok(festivos);
    }
    
    @GetMapping("/simple/{idPais}/{ano}")
    public ResponseEntity<List<Map<String, String>>> obtenerFestivosSimple(
            @PathVariable int idPais, 
            @PathVariable int ano) {
        
        List<Festivo> festivosDelPais = servicio.listarPorPais(idPais);
        List<Map<String, String>> resultado = new ArrayList<>();
        
        for (Festivo festivo : festivosDelPais) {
            Map<String, String> item = new HashMap<>();
            item.put("festivo", festivo.getNombre());
            
            if (festivo.getMes() > 0 && festivo.getDia() > 0) {
                item.put("fecha", String.format("%d-%02d-%02d", ano, festivo.getMes(), festivo.getDia()));
            } else {
                item.put("fecha", ano + "-01-01");
            }
            
            resultado.add(item);
        }
        
        return ResponseEntity.ok(resultado);
    }
}
package festivos.api.presentacion;

import festivos.api.dominio.dto.TipoFestivoDTO;
import festivos.api.aplicacion.TipoFestivoService;
import festivos.api.aplicacion.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-festivos")
@CrossOrigin(origins = "*")
public class TipoFestivoController extends BaseController<TipoFestivoDTO, Long> {

    @Autowired
    private TipoFestivoService tipoFestivoService;

    @Override
    protected BaseService<?, TipoFestivoDTO, Long> getService() {
        return tipoFestivoService;
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<TipoFestivoDTO> obtenerPorTipo(@PathVariable String tipo) {
        return obtenerPorId(tipoFestivoService.obtenerPorTipo(tipo));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TipoFestivoDTO>> listar() {
        return obtenerTodos();
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<TipoFestivoDTO> obtener(@PathVariable Long id) {
        return obtenerPorId(id);
    }
}

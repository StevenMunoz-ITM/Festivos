package festivos.api.presentacion;

import festivos.api.dominio.dto.PaisDTO;
import festivos.api.aplicacion.PaisService;
import festivos.api.aplicacion.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paises")
@CrossOrigin(origins = "*")
public class PaisController extends BaseController<PaisDTO, Long> {

    @Autowired
    private PaisService paisService;

    @Override
    protected BaseService<?, PaisDTO, Long> getService() {
        return paisService;
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PaisDTO> obtenerPorNombre(@PathVariable String nombre) {
        return obtenerPorId(paisService.obtenerPorNombre(nombre));
    }
}

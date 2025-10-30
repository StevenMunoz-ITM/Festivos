package festivos.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		System.out.println("🎉 API de Festivos iniciada correctamente!");
		System.out.println("📚 Documentación disponible en: http://localhost:8080/");
		System.out.println("🔗 Endpoints principales:");
		System.out.println("   - GET /api/paises/listar");
		System.out.println("   - GET /api/tipos/listar");
		System.out.println("   - GET /api/festivos/pais/{idPais}");
		System.out.println("   - GET /api/festivos/verificar/{ano}/{mes}/{dia}/{idPais} (respuesta: 'Es festivo' o 'No es festivo')");
		System.out.println("   - GET /api/festivos/domingo-ramos/{ano}");
		System.out.println("   - GET /api/festivos/pascua/{ano}");
	}

}

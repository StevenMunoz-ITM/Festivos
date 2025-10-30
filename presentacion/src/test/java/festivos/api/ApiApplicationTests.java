package festivos.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApiApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("✅ Contexto de Spring cargado correctamente");
	}

}

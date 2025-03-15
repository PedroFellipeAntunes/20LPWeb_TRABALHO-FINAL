package ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClienteApiRestApplication {
	
	@GetMapping("/api")
	public String hello() {
		return "Projeto Cliente API Rest";
	}

	public static void main(String[] args) {
		SpringApplication.run(ClienteApiRestApplication.class, args);
	}

}

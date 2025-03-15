package ex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ex.model.LoginRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    
    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Verifica se o usuário e senha estão corretos
        if ("admin".equals(loginRequest.getUsuario()) && "admin".equals(loginRequest.getSenha())) {
            // Retorna sucesso se as credenciais estiverem corretas
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            // Retorna erro se as credenciais estiverem incorretas
            return ResponseEntity.status(401).body("Usuário ou senha incorretos");
        }
    }
}

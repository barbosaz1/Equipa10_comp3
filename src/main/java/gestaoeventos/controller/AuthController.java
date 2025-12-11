package gestaoeventos.controller;

import gestaoeventos.dto.LoginRequestDTO;
import gestaoeventos.dto.LoginResponseDTO;
import gestaoeventos.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Serviço que trata da autenticação
    private final AuthService authService;

    // Construtor
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint para login.
    // Recebe um DTO com número e password, chama o serviço de autenticação e devolve
    // um DTO com os dados públicos do utilizador em caso de sucesso.
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO res = authService.login(dto);
        return ResponseEntity.ok(res);
    }
}
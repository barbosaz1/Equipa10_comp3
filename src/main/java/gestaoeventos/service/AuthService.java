package gestaoeventos.service;

import gestaoeventos.dto.LoginRequestDTO;
import gestaoeventos.dto.LoginResponseDTO;
import gestaoeventos.entity.Utilizador;
import gestaoeventos.exception.BusinessException;
import gestaoeventos.repository.UtilizadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
/**
 * Serviço responsável pela autenticação simples dos utilizadores.
 */
public class AuthService {

    // Repositório para procurar o utilizador por número
    private final UtilizadorRepository utilizadorRepository;
    //Comparar a password fornecida com o hash armazenado
    private final PasswordEncoder passwordEncoder;

    // Construtor
    public AuthService(UtilizadorRepository repo, PasswordEncoder encoder) {
        this.utilizadorRepository = repo;
        this.passwordEncoder = encoder;
    }

    /**
     * Processo de login:
     * 1) procura o utilizador pelo número fornecido
     * 2) verifica se a conta está activa
     * 3) compara a password com o hash
     * 4) em caso de sucesso, constrói e devolve um LoginResponseDTO com
     *    os dados públicos (não inclui o hash da password).
     * Lança BusinessException para dados inválidas ou utilizador inactivo.
     */
    public LoginResponseDTO login(LoginRequestDTO dto) {
        Utilizador u = utilizadorRepository.findById(dto.getNumero())
                .orElseThrow(() -> new BusinessException("Credenciais inválidas"));

        if (!u.isAtivo()) {
            throw new BusinessException("Utilizador inativo");
        }

        if (!passwordEncoder.matches(dto.getPassword(), u.getPasswordHash())) {
            throw new BusinessException("Credenciais inválidas");
        } 

        LoginResponseDTO res = new LoginResponseDTO();
        res.setNumero(u.getNumero());
        res.setNome(u.getNome());
        res.setEmail(u.getEmail());
        res.setPerfil(u.getPerfil());
        return res;
    }
}
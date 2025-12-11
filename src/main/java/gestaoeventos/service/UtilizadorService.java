package gestaoeventos.service;

import gestaoeventos.dto.UtilizadorCreateDTO;
import gestaoeventos.dto.UtilizadorDTO;
import gestaoeventos.entity.Utilizador;
import gestaoeventos.exception.BusinessException;
import gestaoeventos.exception.NotFoundException;
import gestaoeventos.repository.UtilizadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilizadorService {

    // Repositório para aceder aos utilizadores na base de dados
    private final UtilizadorRepository utilizadorRepository;
    // Encoder para criar hash da password (não armazenamos a password em claro)
    private final PasswordEncoder passwordEncoder;

    // Construtor
    public UtilizadorService(UtilizadorRepository utilizadorRepository,
                             PasswordEncoder passwordEncoder) {
        this.utilizadorRepository = utilizadorRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Altera o estado "ativo" de um utilizador identificado pelo número.
     * Procura o utilizador, actualiza o campo e guarda as alterações.
     * Lança NotFoundException se o utilizador não existir.
     */
    public UtilizadorDTO alterarEstadoAtivo(Integer numero, boolean ativo) {
        Utilizador u = utilizadorRepository.findById(numero)
                .orElseThrow(() -> new NotFoundException("Utilizador não encontrado"));
        u.setAtivo(ativo);
        Utilizador salvo = utilizadorRepository.save(u);
        return toDTO(salvo);
    }


    /**
     * Lista todos os utilizadores como DTOs.
     */
    public List<UtilizadorDTO> listarTodos() {
        return utilizadorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém um utilizador por número e converte para DTO.
     */
    public UtilizadorDTO obterPorNumero(Integer numero) {
        Utilizador u = utilizadorRepository.findById(numero)
                .orElseThrow(() -> new NotFoundException("Utilizador não encontrado"));
        return toDTO(u);
    }

    /**
     * Cria um novo utilizador a partir do DTO de criação.
     * Lança BusinessException em casos de validação/negócio.
     */
    public UtilizadorDTO criar(UtilizadorCreateDTO dto) {
        if (dto.getNumero() == null) {
            throw new BusinessException("Número de utilizador é obrigatório");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new BusinessException("Password é obrigatória");
        }

        // Verifica se já existe um utilizador com esse número
        utilizadorRepository.findById(dto.getNumero())
                .ifPresent(u -> {
                    throw new BusinessException("Já existe um utilizador com esse número");
                });

        // Verifica se já existe um utilizador com esse email
        utilizadorRepository.findByEmail(dto.getEmail())
                .ifPresent(u -> {
                    throw new BusinessException("Já existe um utilizador com esse email");
                });

        Utilizador u = new Utilizador();
        u.setNumero(dto.getNumero());
        u.setNome(dto.getNome());
        u.setEmail(dto.getEmail());
        u.setPerfil(dto.getPerfil());
        u.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());

        // hash da password — nunca guardar a password em texto simples
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        u.setPasswordHash(passwordHash);

        Utilizador salvo = utilizadorRepository.save(u);
        return toDTO(salvo);
    }

    /**
     * Atualiza um utilizador existente. Só actualiza campos fornecidos no DTO.
     * Se for fornecida uma nova password, faz o hash antes de guardar.
     */
    public UtilizadorDTO atualizar(Integer numero, UtilizadorCreateDTO dto) {
        Utilizador existente = utilizadorRepository.findById(numero)
                .orElseThrow(() -> new NotFoundException("Utilizador não encontrado"));

        if (dto.getNome() != null) {
            existente.setNome(dto.getNome());
        }
        if (dto.getEmail() != null) {
            existente.setEmail(dto.getEmail());
        }
        if (dto.getPerfil() != null) {
            existente.setPerfil(dto.getPerfil());
        }
        if (dto.getAtivo() != null) {
            existente.setAtivo(dto.getAtivo());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            // Apenas atualizamos o hash se houver nova password
            String passwordHash = passwordEncoder.encode(dto.getPassword());
            existente.setPasswordHash(passwordHash);
        }

        Utilizador salvo = utilizadorRepository.save(existente);
        return toDTO(salvo);
    }

    /**
     * Apaga um utilizador por número. Lança NotFoundException se não existir.
     */
    public void apagar(Integer numero) {
        if (!utilizadorRepository.existsById(numero)) {
            throw new NotFoundException("Utilizador não encontrado");
        }
        utilizadorRepository.deleteById(numero);
    }

    /**
     * Converte a entidade Utilizador para o DTO.
     * nota: não incluímos o hash no DTO.
     */
    private UtilizadorDTO toDTO(Utilizador u) {
        return new UtilizadorDTO(
                u.getNumero(),
                u.getNome(),
                u.getEmail(),
                u.getPerfil(),
                u.isAtivo()
        );
    }
}
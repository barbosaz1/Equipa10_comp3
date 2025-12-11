package gestaoeventos.controller;

import gestaoeventos.dto.UtilizadorCreateDTO;
import gestaoeventos.dto.UtilizadorDTO;
import gestaoeventos.service.UtilizadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilizadores")
public class UtilizadorController {

    // Serviço que contém a lógica de negócio dos utilizadores.
    private final UtilizadorService utilizadorService;

    // Construtor
    public UtilizadorController(UtilizadorService utilizadorService) {
        this.utilizadorService = utilizadorService;
    }
    
    // PATCH: ativa a conta de um utilizador (por número).
    // Usamos PATCH porque estamos a alterar apenas o estado 'ativo'.
    @PatchMapping("/{numero}/ativar")
    public UtilizadorDTO ativar(@PathVariable Integer numero) {
        return utilizadorService.alterarEstadoAtivo(numero, true);
    }

    // PATCH: desativa a conta de um utilizador.
    @PatchMapping("/{numero}/desativar")
    public UtilizadorDTO desativar(@PathVariable Integer numero) {
        return utilizadorService.alterarEstadoAtivo(numero, false);
    }

    // GET: lista todos os utilizadores como DTOs (não expomos campos sensíveis).
    @GetMapping
    public List<UtilizadorDTO> listar() {
        return utilizadorService.listarTodos();
    }

    // GET: obtém um único utilizador pelo número
    @GetMapping("/{numero}")
    public UtilizadorDTO obter(@PathVariable Integer numero) {
        return utilizadorService.obterPorNumero(numero);
    }

    // POST: cria um novo utilizador. Recebe um DTO de criação e devolve 201 Created
    // com o DTO do utilizador criado.
    @PostMapping
    public ResponseEntity<UtilizadorDTO> criar(@RequestBody UtilizadorCreateDTO dto) {
        UtilizadorDTO criado = utilizadorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // PUT: atualiza um utilizador existente (substituição conforme o DTO).
    @PutMapping("/{numero}")
    public UtilizadorDTO atualizar(@PathVariable Integer numero,
                                   @RequestBody UtilizadorCreateDTO dto) {
        return utilizadorService.atualizar(numero, dto);
    }

    // DELETE: apaga um utilizador pelo número; devolve 204 No Content em caso de sucesso.
    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> apagar(@PathVariable Integer numero) {
        utilizadorService.apagar(numero);
        return ResponseEntity.noContent().build();
    }
}
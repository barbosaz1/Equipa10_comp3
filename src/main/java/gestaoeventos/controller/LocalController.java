package gestaoeventos.controller;

import gestaoeventos.dto.LocalCreateDTO;
import gestaoeventos.dto.LocalDTO;
import gestaoeventos.service.LocalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locais")
public class LocalController {

    private final LocalService localService;

    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    @GetMapping
    public List<LocalDTO> listar() {
        return localService.listarTodos();
    }

    @GetMapping("/{id}")
    public LocalDTO obter(@PathVariable Integer id) {
        return localService.obterPorId(id);
    }

    @PostMapping
    public ResponseEntity<LocalDTO> criar(@RequestBody LocalCreateDTO dto) {
        LocalDTO criado = localService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public LocalDTO atualizar(@PathVariable Integer id,
                              @RequestBody LocalCreateDTO dto) {
        return localService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Integer id) {
        localService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}

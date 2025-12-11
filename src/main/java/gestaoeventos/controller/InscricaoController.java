package gestaoeventos.controller;

import gestaoeventos.dto.InscricaoDTO;
import gestaoeventos.exception.BusinessException;
import gestaoeventos.service.InscricaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    // --- LISTAGENS / CONSULTA ---

    @GetMapping("/evento/{eventoId}")
    public List<InscricaoDTO> listarPorEvento(@PathVariable Integer eventoId) {
        return inscricaoService.listarPorEvento(eventoId);
    }

    @GetMapping("/utilizador/{numero}")
    public List<InscricaoDTO> listarPorUtilizador(@PathVariable Integer numero) {
        return inscricaoService.listarPorUtilizador(numero);
    }

    @GetMapping("/{id}")
    public InscricaoDTO obter(@PathVariable Integer id) {
        return inscricaoService.obterPorId(id);
    }

    // --- CANCELAMENTO ---

    @PostMapping("/{id}/cancelar")
    public InscricaoDTO cancelar(@PathVariable Integer id) {
        return inscricaoService.cancelarInscricao(id);
    }

    // --- CHECK-IN POR ID ---

    @PostMapping("/{id}/checkin")
    public InscricaoDTO checkin(@PathVariable Integer id) {
        return inscricaoService.fazerCheckin(id);
    }

    // --- QR CODE: OBTÉM URL E TOKEN ---

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<Map<String, String>> obterQrCode(@PathVariable Integer id) {
        InscricaoDTO dto = inscricaoService.obterPorId(id);
        String url = inscricaoService.obterQrCodeUrl(id);
        String token = inscricaoService.obterQrCodeToken(id);

        return ResponseEntity.ok(
                Map.of(
                        "inscricaoId", String.valueOf(dto.getId()),
                        "eventoId", dto.getEventoId() != null ? String.valueOf(dto.getEventoId()) : "",
                        "utilizadorNumero", dto.getUtilizadorNumero() != null ? String.valueOf(dto.getUtilizadorNumero()) : "",
                        "qrCodeToken", token,
                        "qrCodeUrl", url
                )
        );
    }

    // --- CHECK-IN POR TOKEN DE QR CODE ---

    @PostMapping("/checkin/qrcode")
    public InscricaoDTO checkinPorQrCode(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token == null || token.isBlank()) {
            throw new BusinessException("Token é obrigatório");
        }
        return inscricaoService.fazerCheckinPorToken(token);
    }
}

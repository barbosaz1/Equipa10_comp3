package gestaoeventos.service;

import gestaoeventos.dto.NotificacaoDTO;
import gestaoeventos.entity.Notificacao;
import gestaoeventos.exception.NotFoundException;
import gestaoeventos.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public List<NotificacaoDTO> listarPorDestinatario(Integer numero) {
        return notificacaoRepository.findByDestinatarioNumeroOrderByDataCriacaoDesc(numero)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public NotificacaoDTO marcarComoLida(Integer id) {
        Notificacao n = notificacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificação não encontrada"));
        n.setLida(true);
        Notificacao salvo = notificacaoRepository.save(n);
        return toDTO(salvo);
    }

    private NotificacaoDTO toDTO(Notificacao n) {
        NotificacaoDTO dto = new NotificacaoDTO();
        dto.setId(n.getId());
        dto.setDestinatarioNumero(n.getDestinatario().getNumero());
        dto.setEventoId(n.getEvento() != null ? n.getEvento().getId() : null);
        dto.setTipo(n.getTipo());
        dto.setConteudo(n.getConteudo());
        dto.setCanal(n.getCanal());
        dto.setLida(n.isLida());
        dto.setDataCriacao(n.getDataCriacao());
        return dto;
    }
}

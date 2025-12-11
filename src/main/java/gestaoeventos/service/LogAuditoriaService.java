package gestaoeventos.service;

import gestaoeventos.dto.LogAuditoriaDTO;
import gestaoeventos.entity.LogAuditoria;
import gestaoeventos.repository.LogAuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogAuditoriaService {

    private final LogAuditoriaRepository logAuditoriaRepository;

    public LogAuditoriaService(LogAuditoriaRepository logAuditoriaRepository) {
        this.logAuditoriaRepository = logAuditoriaRepository;
    }

    public List<LogAuditoriaDTO> listarTodos() {
        return logAuditoriaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<LogAuditoriaDTO> listarPorEntidade(String entidade, Integer entidadeId) {
        return logAuditoriaRepository.findByEntidadeAndEntidadeIdOrderByDataHoraDesc(entidade, entidadeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<LogAuditoriaDTO> listarPorAutor(Integer autorNumero) {
        return logAuditoriaRepository.findByAutorNumeroOrderByDataHoraDesc(autorNumero)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private LogAuditoriaDTO toDTO(LogAuditoria l) {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setId(l.getId());
        dto.setAcao(l.getAcao());
        dto.setEntidade(l.getEntidade());
        dto.setEntidadeId(l.getEntidadeId());
        dto.setMotivo(l.getMotivo());
        dto.setIpOrigem(l.getIpOrigem());
        dto.setDataHora(l.getDataHora());
        dto.setAutorNumero(l.getAutor() != null ? l.getAutor().getNumero() : null);
        return dto;
    }
}

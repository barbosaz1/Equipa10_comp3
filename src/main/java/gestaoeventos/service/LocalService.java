package gestaoeventos.service;

import gestaoeventos.dto.LocalCreateDTO;
import gestaoeventos.dto.LocalDTO;
import gestaoeventos.entity.Local;
import gestaoeventos.exception.NotFoundException;
import gestaoeventos.repository.LocalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalService {

    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public List<LocalDTO> listarTodos() {
        return localRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LocalDTO obterPorId(Integer id) {
        Local l = localRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Local não encontrado"));
        return toDTO(l);
    }

    public LocalDTO criar(LocalCreateDTO dto) {
        Local l = new Local();
        l.setNome(dto.getNome());
        l.setMorada(dto.getMorada());
        l.setCapacidade(dto.getCapacidade());
        l.setDisponibilidadeHoraria(dto.getDisponibilidadeHoraria());
        l.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());
        Local salvo = localRepository.save(l);
        return toDTO(salvo);
    }

    public LocalDTO atualizar(Integer id, LocalCreateDTO dto) {
        Local existente = localRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Local não encontrado"));

        if (dto.getNome() != null) existente.setNome(dto.getNome());
        if (dto.getMorada() != null) existente.setMorada(dto.getMorada());
        if (dto.getCapacidade() != null) existente.setCapacidade(dto.getCapacidade());
        if (dto.getDisponibilidadeHoraria() != null)
            existente.setDisponibilidadeHoraria(dto.getDisponibilidadeHoraria());
        if (dto.getAtivo() != null) existente.setAtivo(dto.getAtivo());

        Local salvo = localRepository.save(existente);
        return toDTO(salvo);
    }

    public void apagar(Integer id) {
        if (!localRepository.existsById(id)) {
            throw new NotFoundException("Local não encontrado");
        }
        localRepository.deleteById(id);
    }

    private LocalDTO toDTO(Local l) {
        LocalDTO dto = new LocalDTO();
        dto.setId(l.getId());
        dto.setNome(l.getNome());
        dto.setMorada(l.getMorada());
        dto.setCapacidade(l.getCapacidade());
        dto.setDisponibilidadeHoraria(l.getDisponibilidadeHoraria());
        dto.setAtivo(l.isAtivo());
        return dto;
    }
}

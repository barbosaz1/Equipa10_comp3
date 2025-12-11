package gestaoeventos.service;

import gestaoeventos.dto.ListaEsperaDTO;
import gestaoeventos.entity.ListaEspera;
import gestaoeventos.repository.ListaEsperaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaEsperaService {

    private final ListaEsperaRepository listaEsperaRepository;

    public ListaEsperaService(ListaEsperaRepository listaEsperaRepository) {
        this.listaEsperaRepository = listaEsperaRepository;
    }

    public List<ListaEsperaDTO> listarPorEvento(Integer eventoId) {
        return listaEsperaRepository.findByEventoIdOrderByDataEntradaAsc(eventoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ListaEsperaDTO toDTO(ListaEspera le) {
        ListaEsperaDTO dto = new ListaEsperaDTO();
        dto.setId(le.getId());
        dto.setEventoId(le.getEvento().getId());
        dto.setUtilizadorNumero(le.getUtilizador().getNumero());
        dto.setDataEntrada(le.getDataEntrada());
        dto.setPosicao(le.getPosicao());
        return dto;
    }
}

package gestaoeventos.controller;

import gestaoeventos.dto.ListaEsperaDTO;
import gestaoeventos.service.ListaEsperaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lista-espera")
public class ListaEsperaController {

    private final ListaEsperaService listaEsperaService;

    public ListaEsperaController(ListaEsperaService listaEsperaService) {
        this.listaEsperaService = listaEsperaService;
    }

    @GetMapping("/evento/{eventoId}")
    public List<ListaEsperaDTO> listarPorEvento(@PathVariable Integer eventoId) {
        return listaEsperaService.listarPorEvento(eventoId);
    }
}

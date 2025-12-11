package gestaoeventos.repository;

import gestaoeventos.entity.ListaEspera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Integer> {

    List<ListaEspera> findByEventoIdOrderByDataEntradaAsc(Integer eventoId);

    Optional<ListaEspera> findFirstByEventoIdOrderByPosicaoAsc(Integer eventoId);
}



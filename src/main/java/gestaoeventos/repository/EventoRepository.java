package gestaoeventos.repository;

import gestaoeventos.entity.EstadoEvento;
import gestaoeventos.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    List<Evento> findByEstado(EstadoEvento estado);

    List<Evento> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);
}

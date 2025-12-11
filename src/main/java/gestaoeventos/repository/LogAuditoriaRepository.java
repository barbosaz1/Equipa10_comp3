package gestaoeventos.repository;

import gestaoeventos.entity.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Integer> {

    List<LogAuditoria> findByEntidadeAndEntidadeIdOrderByDataHoraDesc(String entidade, Integer entidadeId);

    List<LogAuditoria> findByAutorNumeroOrderByDataHoraDesc(Integer autorNumero);
}


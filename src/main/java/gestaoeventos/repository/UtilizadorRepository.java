package gestaoeventos.repository;

import gestaoeventos.entity.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para a entidade Utilizador.
 */
public interface UtilizadorRepository extends JpaRepository<Utilizador, Integer> {

    /**
     * Procura um utilizador pelo email.
     *
     * Retorna um Optional porque o utilizador pode não existir
     */
    Optional<Utilizador> findByEmail(String email);
}
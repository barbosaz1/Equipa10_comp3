package gestaoeventos.exception;

/**
 * Excepção lançada quando um recurso não é encontrado.
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor com mensagem explicativa.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
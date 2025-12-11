package gestaoeventos.exception;

/**
 * Excepção usada para sinalizar erros de negócio/validação na camada de serviço.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * A mensagem é apresentada no body da resposta HTTP.
     */
    public BusinessException(String message) {
        super(message);
    }
}
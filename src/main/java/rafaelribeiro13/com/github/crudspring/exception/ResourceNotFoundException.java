package rafaelribeiro13.com.github.crudspring.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Long id) {
        super("Recurso de id: " + id + " não encontrado");
    }
    
}

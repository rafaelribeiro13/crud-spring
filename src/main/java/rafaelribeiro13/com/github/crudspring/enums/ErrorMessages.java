package rafaelribeiro13.com.github.crudspring.enums;

public enum ErrorMessages {
    
    NOT_FOUND("Recurso não encontrado"),
    VALIDATION("Falha na validação");

    private String value;

    ErrorMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}

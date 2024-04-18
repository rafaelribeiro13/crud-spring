package rafaelribeiro13.com.github.crudspring.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import rafaelribeiro13.com.github.crudspring.exception.ResourceNotFoundException;
import rafaelribeiro13.com.github.crudspring.exception.StandardError;

@RestControllerAdvice
public class ApplicationControllerAdivice {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StandardError> handleResourceNotFoundException(
        ResourceNotFoundException ex, HttpServletRequest request
    ) {
        String error = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        var standardError = new StandardError(
            Instant.now(), 
            status.value(), 
            error, ex.getMessage(), 
            request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }

}

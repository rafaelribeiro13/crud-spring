package rafaelribeiro13.com.github.crudspring.controller;

import java.time.Instant;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import rafaelribeiro13.com.github.crudspring.enums.ErrorMessages;
import rafaelribeiro13.com.github.crudspring.exception.ResourceNotFoundException;
import rafaelribeiro13.com.github.crudspring.exception.StandardError;

@RestControllerAdvice
public class ApplicationControllerAdivice {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(
        ResourceNotFoundException ex, HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        var standardError = new StandardError(
            Instant.now(), 
            status.value(), 
            ErrorMessages.NOT_FOUND.getValue(),
            ex.getMessage(), 
            request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String message = ex.getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .reduce("", (acc, fieldError) -> acc + fieldError + ", ");
        
        var standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError(ErrorMessages.VALIDATION.getValue());
        standardError.setMessage(message);
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> handleConstraintViolationException(
        ConstraintViolationException ex, HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String message = ex.getConstraintViolations().stream()
            .map(fieldError -> fieldError.getPropertyPath() + " " + fieldError.getMessage())
            .reduce("", (acc, fieldError) -> acc + fieldError + ", ");
        
        var standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError(ErrorMessages.VALIDATION.getValue());
        standardError.setMessage(message);
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex, HttpServletRequest request
    ) {
        var message = "";

        if (Objects.nonNull(ex) && Objects.nonNull(ex.getRequiredType())) {
            String type = ex.getRequiredType().getName();
            String[] typeParts = type.split("\\.");
            String typeName = typeParts[typeParts.length - 1];

            message = ex.getPropertyName() + " should be of type " + typeName;
        } else {
            message = "Argument type not valid";
        }

        HttpStatus status = HttpStatus.BAD_REQUEST;

        var standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError(ErrorMessages.VALIDATION.getValue());
        standardError.setMessage(message);
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

}

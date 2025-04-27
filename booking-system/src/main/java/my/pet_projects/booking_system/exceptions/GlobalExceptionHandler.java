package my.pet_projects.booking_system.exceptions;


import my.pet_projects.booking_system.dto.responses.ExceptionResponse;
import my.pet_projects.booking_system.dto.responses.ValidationExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(ConflictException e) {
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ValidationExceptionResponse> handleException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(new ValidationExceptionResponse(
                "Ошибка валидации", errors, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

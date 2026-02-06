package com.example.recipeapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Validación @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        log.warn("400 Validation error - fields={}", errors);

        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }

    // 400 - JSON mal formado / tipos mal / fechas mal
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException ex) {
        log.warn("400 Malformed JSON - message={}", ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ErrorResponse.generalError(400, "bad-request", "Malformed JSON or invalid value"));
    }

    // 404 - Not Found
    @ExceptionHandler({IngredientNotFoundException.class, RecipeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        log.warn("404 Not Found - message={}", ex.getMessage());

        return ResponseEntity.status(404).body(ErrorResponse.notFound(ex.getMessage()));
    }

    // 400 - por ejemplo Season.valueOf(...) si manda algo inválido
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("400 Illegal argument - message={}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ErrorResponse.generalError(400, "bad-request", ex.getMessage()));
    }
    // 500 - cualquier otra cosa
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("500 Unexpected error - path={}", request.getRequestURI(), ex);

        return ResponseEntity.status(500)
                .body(ErrorResponse.generalError(500, "internal-error", "Unexpected error"));
    }
}

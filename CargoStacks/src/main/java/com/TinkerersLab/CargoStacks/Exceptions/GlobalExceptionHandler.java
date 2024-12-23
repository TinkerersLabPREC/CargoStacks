package com.TinkerersLab.CargoStacks.Exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.TinkerersLab.CargoStacks.models.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<String>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {

        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .message("Method not allowed")
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .payload("Method not allowed for this endpoint " + exception.getMethod())
                .success(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidToolException(
            SQLIntegrityConstraintViolationException exception) {

        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .payload("Something went wrong, ensure entity integrity")
                .success(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleNoResourceFoundException(NoResourceFoundException exception) {

        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .message("Resource not found")
                .status(HttpStatus.NOT_FOUND)
                .payload(exception.getMessage())
                .success(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {

        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .payload("The Resource with provided id: " + exception.getId() + " was not found")
                .success(false)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse<Map<String, String>> errorResponse = ErrorResponse.<Map<String, String>>builder()
                .message("Invalid Object provided")
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .payload(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidProvidedObjectException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleInvalidProvidedObjectException(
            InvalidProvidedObjectException exception) {

        ErrorResponse<Map<String, String>> errorResponse = ErrorResponse.<Map<String, String>>builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .payload(exception.getErrors())
                .success(false)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}

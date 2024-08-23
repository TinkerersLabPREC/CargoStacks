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
    public ResponseEntity<ErrorResponse<String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setSuccess(false);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity< ErrorResponse<String> > handleInvalidToolException(SQLIntegrityConstraintViolationException exception){

        ErrorResponse<String> errorResponse = new ErrorResponse<>();
    
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setSuccess(false);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } 

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleNoResourceFoundException(NoResourceFoundException exception){
    
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setSuccess(false);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);    
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String> > handleResourceNotFoundException(ResourceNotFoundException exception){

        ErrorResponse<String> errorResponse =  new ErrorResponse<String>();

        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setSuccess(false);
        errorResponse.setPayload(exception.getId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);        
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException exception){

        ErrorResponse<Map<String, String>> errorResponse = new ErrorResponse<>();
        errorResponse.setMessage("Invalid Object provided");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setSuccess(false);
        
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach( error -> {

            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        errorResponse.setPayload(errors);
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

    @ExceptionHandler(InvalidProvidedObjectException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleInvalidProvidedObjectException(InvalidProvidedObjectException exception){

        ErrorResponse<Map<String, String>> errorResponse = new ErrorResponse<>();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setPayload(exception.getErrors());
        errorResponse.setSuccess(false);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);



    }
    
}

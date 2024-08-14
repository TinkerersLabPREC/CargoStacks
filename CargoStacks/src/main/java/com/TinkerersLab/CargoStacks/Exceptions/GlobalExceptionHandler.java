package com.TinkerersLab.CargoStacks.Exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.TinkerersLab.CargoStacks.models.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    // public ResponseEntity<ErrorResponse> handleDuplicateObjectException(SQLIntegrityConstraintViolationException exception){

    //     ErrorResponse errorResponse = new ErrorResponse<>();
    //     errorResponse.setMessage(exception.getMessage());
    //     errorResponse.setStatus(HttpStatus.);
    //     errorResponse.setPayload(errorResponse);;
    //     errorResponse.setSuccess(false);;
        
    //     return new ResponseEntity<>(null)
    // } 

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity< ErrorResponse<String> > handleInvalidToolException(SQLIntegrityConstraintViolationException exception){

        ErrorResponse<String> errorResponse = new ErrorResponse<>();
    
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setSuccess(false);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    } 


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String> > handleResourceNotFoundException(ResourceNotFoundException exception){

        ErrorResponse<String> errorResponse =  new ErrorResponse<String>();

        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setSuccess(false);
        errorResponse.setPayload(exception.getId());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    
}

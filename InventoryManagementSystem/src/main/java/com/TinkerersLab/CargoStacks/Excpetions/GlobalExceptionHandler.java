package com.TinkerersLab.CargoStacks.Excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.TinkerersLab.CargoStacks.models.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(RuntimeException)
    // public ResponseEntity<ErrorResponse> 

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<Integer> > handleResourceNotFoundException(ResourceNotFoundException exception){

        ErrorResponse<Integer> errorResponse =  new ErrorResponse<Integer>();

        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setSuccess(false);
        errorResponse.setPayload(exception.getId());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

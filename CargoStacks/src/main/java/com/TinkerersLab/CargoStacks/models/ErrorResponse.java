package com.TinkerersLab.CargoStacks.models;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponse<T> {

    public String message;
    
    public T payload;
    
    public HttpStatus status;

    public boolean success = false;
}
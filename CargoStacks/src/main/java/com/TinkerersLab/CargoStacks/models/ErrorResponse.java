package com.TinkerersLab.CargoStacks.models;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {

    public String message;

    public T payload;

    public HttpStatus status;

    public boolean success;
}
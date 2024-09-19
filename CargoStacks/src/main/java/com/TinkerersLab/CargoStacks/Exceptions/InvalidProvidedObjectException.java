package com.TinkerersLab.CargoStacks.Exceptions;

import java.util.Map;

public class InvalidProvidedObjectException extends RuntimeException {

    Map<String, String> errors;

    public InvalidProvidedObjectException(Map<String, String> errors) {
        super("Provided object not valid");
        this.errors = errors;
    }

    public InvalidProvidedObjectException(String string) {
        super("Provided object not valid");
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

}

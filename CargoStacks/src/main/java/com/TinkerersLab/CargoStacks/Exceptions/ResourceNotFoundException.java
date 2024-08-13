package com.TinkerersLab.CargoStacks.Exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private String id;

    public ResourceNotFoundException(String message, String id){
        super(message);
        this.id = id;   
    }

    public ResourceNotFoundException(){
        super("Resource not found !");
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

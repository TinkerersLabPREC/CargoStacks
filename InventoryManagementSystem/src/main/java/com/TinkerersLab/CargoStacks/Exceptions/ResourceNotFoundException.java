package com.TinkerersLab.CargoStacks.Exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private int id;

    public ResourceNotFoundException(String message, int id){
        super(message);
        this.id = id;   
    }

    public ResourceNotFoundException(){
        super("Resource not found !");
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

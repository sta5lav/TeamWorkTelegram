package com.example.shelterforpets.exceptions;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException() {
    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}

package com.example.shelterforpets.exceptions;

public class VolunteerNotFoundException extends RuntimeException{
    public VolunteerNotFoundException() {
    }

    public VolunteerNotFoundException(String message) {
        super(message);
    }
}

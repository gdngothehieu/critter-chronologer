package com.udacity.jdnd.course3.critter.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PetNotFoundException extends RuntimeException{

    public PetNotFoundException() {
    }

    public PetNotFoundException(String message) {
        super(message);
    }
}


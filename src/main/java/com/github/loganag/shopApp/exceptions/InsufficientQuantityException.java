package com.github.loganag.shopApp.exceptions;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String message){
        super(message);
    }
}

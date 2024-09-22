package com.mgbryan.car;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(final String message) {
        super(message);
    }
}
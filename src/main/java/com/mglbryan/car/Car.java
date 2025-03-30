package com.mglbryan.car;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public final class Car {
    private String manufacturer;
    private String model;
    private int year;
    private UUID carId;
    private double rentalPricePerHour;
    private boolean isElectric;
    private boolean isAvailable;
}
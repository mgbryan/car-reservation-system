package com.mgbryan.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
public @Data class Car {
    private @NonNull String manufacturer;
    private @NonNull String model;
    private @NonNull String carId;
    private double rentalPricePerHour;
    private boolean isElectric;
    private boolean isAvailable;

    @Override
    public String toString() {
        return this.getManufacturer() + " " + this.getModel();
    }
}
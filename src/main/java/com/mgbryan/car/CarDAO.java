package com.mgbryan.car;

import java.util.Arrays;

import com.github.javafaker.Faker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public final class CarDAO {
    @Getter
    private static Car[] cars;

    static {
        cars = new Car[]{
                new Car("BMW", "M3", new Faker().idNumber().valid(),
                        40.00, false, true),
                new Car("Toyota", "Prius", new Faker().idNumber().valid(),
                        30.00, true, true),
                new Car("Tesla", "Model Y", new Faker().idNumber().valid(),
                        30.00, true, true)
        };
    }

    /* CRUD OPERATIONS */
    void addCar(@NonNull Car car) {
        cars = Arrays.copyOf(cars, cars.length + 1);
        cars[cars.length - 1] = car;
    }

    void deleteCar(@NonNull final String carID) {
        cars = Arrays.stream(cars)
                     .filter(car -> !car.getCarId().equals(carID))
                     .toArray(Car[]::new);
    }

    public static Car getCar(@NonNull String carId) {
        for (Car car : cars) {
            if (car.getCarId().equals(carId)) return car;
        }
        return null;
    }
}
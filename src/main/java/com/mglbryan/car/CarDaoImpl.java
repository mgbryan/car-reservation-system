package com.mglbryan.car;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public final class CarDaoImpl implements CarDao {
    private static final List<Car> carList = new ArrayList<>();

    static {
        carList.add(
                new Car("BMW", "M2", 2023,
                        UUID.randomUUID(), 75.00, false, true)
        );

        carList.add(
                new Car("Ford", "F-150 Lightning", 2025,
                        UUID.randomUUID(), 120.00, true, true)
        );
    }

    @Override
    public void addCar(@NonNull final Car car) {
        carList.add(car);
    }

    @Override
    public void deleteCar(@NonNull final Car car) {
        carList.remove(car);
//        final Optional<Car> car = carList.stream()
//                .filter(c -> c.getCarId().equals(carID))
//                .findAny();
//        car.ifPresentOrElse();
    }

    @Override
    public List<Car> getCars() {
        return Collections.unmodifiableList(carList);
    }
}
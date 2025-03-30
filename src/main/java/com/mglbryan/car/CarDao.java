package com.mglbryan.car;

import java.util.List;

public interface CarDao {
    public void addCar(final Car car);
    public void deleteCar(final Car car);
    public List<Car> getCars();
}

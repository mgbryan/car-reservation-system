package com.mglbryan.car;

import lombok.NonNull;

import java.util.*;

public final class CarService {
    private final CarDao carDao;

    public CarService(@NonNull final CarDao carDao) {
        this.carDao = carDao;
    }

    public void addCar(@NonNull final Car car) {
        carDao.addCar(car);
    }

    public void deleteCar(@NonNull final Car car) {
        carDao.deleteCar(car);
    }

//    /**
//     * Deletes/removes a Car object from the Car database if the given Car ID exists.
//     *
//     * @throws NotRemovableException Thrown if the Car object with the matching input ID is 'in-use/reserved'
//     * @throws CarNotFoundException Thrown if the input ID does not exist in the Car database
//     */
//    public void deleteCar() {
//        if (CarDaoImpl.getCars().length == 0) {
//            System.out.println("There are 0 cars to delete. Returning to main menu.");
//            return;
//        }
//
//        getAllCars();
//        System.out.println("Enter the ID for the car you wish to remove:");
//
//        try {
//            final String CAR_ID = new Scanner(System.in).next("^\\d{3}-\\d{2}-\\d{4}$");
//            if (!isValidCar(CAR_ID)) {
//                throw new CarNotFoundException("⚠️ Car with ID " + CAR_ID + " does not exist.");
//            }
//
//            if (Arrays.stream(ReservationDaoImpl.getReservations()).anyMatch(o -> o.getCar().getCarId().equals(CAR_ID))) {
//                throw new NotRemovableException("⚠️ Car cannot be deleted because it is currently reserved.");
//            }
//
//            carDaoImpl.deleteCar(CAR_ID);
//            System.out.println("✅ Car " + CAR_ID + " has been successfully deleted from the database.");
//        } catch (NoSuchElementException | CarNotFoundException | NotRemovableException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public List<Car> getCars() {
       return carDao.getCars();
    }

    public Optional<Car> findCarByUUID(@NonNull final UUID carUUID) {
        return carDao.getCars()
                .stream()
                .filter(car -> car.getCarId().equals(carUUID))
                .findFirst();
    }
}
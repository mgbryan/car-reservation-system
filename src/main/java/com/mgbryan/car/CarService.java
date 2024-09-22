package com.mgbryan.car;

import com.mgbryan.NotRemovableException;
import com.mgbryan.reservation.ReservationDAO;
import com.github.javafaker.Faker;

import lombok.NonNull;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class CarService {
    private final @NonNull CarDAO carDAO;

    public CarService() {
        this.carDAO = new CarDAO();
    }

    public void addCar() {
        System.out.println("Enter the manufacturer name:");
        final String MANUFACTURER = new Scanner(System.in).nextLine();

        System.out.println("Enter the model name:");
        final String MODEL = new Scanner(System.in).nextLine();

        System.out.println("Enter the price/hr rate:");
        final double PRICE = new Scanner(System.in).nextDouble();

        System.out.println("Is this car electric? (true/false):");
        final boolean IS_ELECTRIC = new Scanner(System.in).nextBoolean();

        System.out.println("Would you like to make this car available to reserve? (true/false)");
        final boolean IS_AVAILABLE = new Scanner(System.in).nextBoolean();

        carDAO.addCar(new Car(MANUFACTURER, MODEL, new Faker().idNumber().valid(), PRICE, IS_ELECTRIC, IS_AVAILABLE));
    }

    /**
     * Deletes/removes a Car object from the Car database if the given Car ID exists.
     *
     * @throws NotRemovableException Thrown if the Car object with the matching input ID is 'in-use/reserved'
     * @throws CarNotFoundException Thrown if the input ID does not exist in the Car database
     */
    public void deleteCar() {
        if (CarDAO.getCars().length == 0) {
            System.out.println("There are 0 cars to delete. Returning to main menu.");
            return;
        }

        getAllCars();
        System.out.println("Enter the ID for the car you wish to remove:");

        try {
            final String CAR_ID = new Scanner(System.in).next("^\\d{3}-\\d{2}-\\d{4}$");
            if (!isValidCar(CAR_ID)) {
                throw new CarNotFoundException("⚠️ Car with ID " + CAR_ID + " does not exist.");
            }

            if (Arrays.stream(ReservationDAO.getReservations()).anyMatch(o -> o.getCar().getCarId().equals(CAR_ID))) {
                throw new NotRemovableException("⚠️ Car cannot be deleted because it is currently reserved.");
            }

            carDAO.deleteCar(CAR_ID);
            System.out.println("✅ Car " + CAR_ID + " has been successfully deleted from the database.");
        } catch (NoSuchElementException | CarNotFoundException | NotRemovableException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getAllCars() {
        final Car[] cars = CarDAO.getCars();
        final StringBuilder TABLE = new StringBuilder();
        final String SEPARATOR =
                "+--------------------+-----------------+-----------------+------------+-----------+-----------+\n";

        TABLE.append(SEPARATOR);
        TABLE.append(String.format("| %-18s | %-15s | %-15s | %-10s | %-9s | %-9s |\n",
                            "MANUFACTURER", "MODEL", "ID", "PRICE/HR", "ELECTRIC", "AVAILABLE"));
        TABLE.append(SEPARATOR);

        for (Car car : cars) {
            TABLE.append(String.format("| %-18s | %-15s | %-15s | %-10.2f | %-9s | %-9s |\n",
                    car.getManufacturer(),
                    car.getModel(),
                    car.getCarId(),
                    car.getRentalPricePerHour(),
                    car.isElectric() ? "YES" : "NO",
                    car.isAvailable() ? "YES" : "NO"));
        }
        TABLE.append(SEPARATOR);
        System.out.println(TABLE);
    }

    public static void getAvailableCars() {
        final Car[] allAvailableCars = Arrays.stream(CarDAO.getCars())
                .filter(Car::isAvailable)
                .toArray(Car[]::new);

        printAvailabilityTable(allAvailableCars);
    }

    public void getAvailableElectrics() {
        final Car[] availableElectrics = Arrays.stream(CarDAO.getCars())
                .filter(Car::isElectric)
                .filter(Car::isAvailable)
                .toArray(Car[]::new);

        printAvailabilityTable(availableElectrics);
    }

    private boolean isValidCar(@NonNull final String carID) {
        return Arrays.stream(CarDAO.getCars())
                     .anyMatch(car -> car.getCarId().compareTo(carID) == 0);
    }

    private static void printAvailabilityTable(@NonNull final Car[] cars) {
        if (cars.length == 0) {
            System.out.println("No cars available.");
            return;
        }

        System.out.println("---------------------------------------------------");
        System.out.printf("%-20s %-15s %-10s", "MANUFACTURER", "MODEL", "ID");
        System.out.println();
        System.out.println("---------------------------------------------------");

        for (Car car : cars) {
            System.out.printf("%-20s %-15s %-10s", car.getManufacturer(), car.getModel(), car.getCarId());
            System.out.println();
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Total Available: " + cars.length);
    }
}
package com.mglbryan;

import com.mglbryan.car.Car;
import com.mglbryan.car.CarDao;
import com.mglbryan.car.CarDaoImpl;
import com.mglbryan.car.CarService;
import com.mglbryan.customer.Customer;
import com.mglbryan.customer.CustomerDao;
import com.mglbryan.customer.CustomerDaoImpl;
import com.mglbryan.customer.CustomerService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mglbryan.reservation.Reservation;
import com.mglbryan.reservation.ReservationDao;
import com.mglbryan.reservation.ReservationDaoImpl;
import com.mglbryan.reservation.ReservationService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws InterruptedException {
        // dependencies
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final CustomerDao customerDao = new CustomerDaoImpl();
        final CarDao carDao = new CarDaoImpl();
        final ReservationDao reservationDao = new ReservationDaoImpl();
        final Scanner scanner = new Scanner(System.in);

        // dependency injection
        final CustomerService customerService = new CustomerService(customerDao);
        final CarService carService = new CarService(carDao);
        final ReservationService reservationService = new ReservationService(reservationDao);

        boolean notDone = true;
        do {
            printMainMenu();
            final int USER_CHOICE;

            try {
                USER_CHOICE = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.err.println("Input is invalid.");
                scanner.next();
                continue;
            }

            switch (USER_CHOICE) {
                case 0:
                    notDone = false;
                    break;
                case 1:
                    createReservation(reservationService, carService, customerService, gson, scanner);
                    break;
                case 2:
                    cancelReservation(reservationService, gson, scanner);
                    break;
                case 3:
                    getReservedCars(reservationService, gson);
                    break;
                case 4:
                    getReservations(reservationService, gson);
                    break;
                case 5:
                    getCars(carService, gson);
                    break;
                case 6:
                    getAvailableCars(carService, gson);
                    break;
                case 7:
                    getAvailableElectricCars(carService, gson);
                    break;
                case 8:
                    addCar(carService, scanner);
                    break;
                case 9:
                    deleteCar(carService, scanner, gson);
                    break;
                case 10:
                    createCustomer(customerService, scanner);
                    break;
                case 11:
                    deleteCustomer(customerService, scanner);
                    break;
                case 12:
                    printCustomers(customerService, gson);
                    break;
                default:
                    break;
            }
            Thread.sleep(1500);
        } while (notDone);
    }

    private static void printMainMenu() {
        System.out.println("********* MAIN MENU ************");
        System.out.println("RESERVATIONS ↘");
        System.out.println("❬ 1 ❭ ↦ Create Reservation");
        System.out.println("❬ 2 ❭ ↦ Cancel Reservation");
        System.out.println("❬ 3 ❭ ↦ Show All Reserved Cars");
        System.out.println("❬ 4 ❭ ↦ Show All Reservations");
        System.out.println("CARS ↘");
        System.out.println("❬ 5 ❭ ↦ Show All Cars");
        System.out.println("❬ 6 ❭ ↦ Show All Available Cars");
        System.out.println("❬ 7 ❭ ↦ Show All Available Electric Cars");
        System.out.println("❬ 8 ❭ ↦ Add New Car To Database");
        System.out.println("❬ 9 ❭ ↦ Delete Car From Database");
        System.out.println("CUSTOMERS ↘");
        System.out.println("❬ 10 ❭ ↦ Add New Customer");
        System.out.println("❬ 11 ❭ ↦ Delete Customer");
        System.out.println("❬ 12 ❭ ↦ Show All Customers");
        System.out.println("❬ 13 ❭ ↦ Update Customer Data (pending)");
        System.out.println("❬ 0 ❭ ↦ Exit App ❌");
        System.out.println("********************************");
    }

    // <---------- Customers ---------->
    private static void createCustomer(final CustomerService customerService, final Scanner scanner) {
        final String CUSTOMER_NAME;
        final String CUSTOMER_EMAIL;

        try {
            System.out.println("Enter the customer's full name:");
            CUSTOMER_NAME = scanner.nextLine();

            System.out.println("Enter the customer's email:");
            CUSTOMER_EMAIL = scanner.next();

            customerService.createCustomer(new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, UUID.randomUUID()));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteCustomer(final CustomerService customerService, final Scanner scanner) {
        final UUID CUSTOMER_UUID;
        final Optional<Customer> optionalCustomer;

        try {
            System.out.println("Enter the customer's UUID:");
            CUSTOMER_UUID = UUID.fromString(scanner.next());

            optionalCustomer = customerService.findCustomerByUUID(CUSTOMER_UUID);
            optionalCustomer.ifPresentOrElse(customerService::deleteCustomer,
                    () -> System.err.println("Cannot delete: customer does not exist."));
        } catch (IllegalArgumentException e) {
            System.out.println("Your input is invalid. Returning to main menu.");
        }
    }

    private static void printCustomers(final CustomerService customerService, final Gson gson) {
        final List<Customer> customersList = customerService.getCustomers();
        if (customersList.isEmpty()) {
            System.err.println("There are no customers in the database.");
            return;
        }
        customersList.forEach(customer -> System.out.println(gson.toJson(customer)));
    }

    // <---------- Car ---------->
    private static void addCar(final CarService carService, final Scanner scanner) {
        final String manufacturer;
        final String model;
        final int year;
        final double pricePerHourRate;
        final boolean isElectric;
        final boolean isAvailable;

        try {
            System.out.println("Enter the manufacturer name:");
            manufacturer = scanner.nextLine();

            System.out.println("Enter the model name:");
            model = scanner.nextLine();

            System.out.println("Enter the year:");
            year = scanner.nextInt();

            System.out.println("Enter the price/hr rate:");
            pricePerHourRate = scanner.nextDouble();

            System.out.println("Is this car electric? (true/false):");
            isElectric = scanner.nextBoolean();

            System.out.println("Would you like to make this car available to reserve? (true/false)");
            isAvailable = scanner.nextBoolean();

            carService.addCar(
                    new Car(manufacturer, model, year, UUID.randomUUID(), pricePerHourRate, isElectric, isAvailable)
            );
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, abandoning transaction: returning to main menu.");
        }
    }

    private static void deleteCar(final CarService carService, final Scanner scanner, final Gson gson) {
        if (carService.getCars().isEmpty()) {
            System.err.println("No cars to delete.");
            return;
        }

        carService.getCars().forEach(car -> System.out.println(gson.toJson(car)));
        System.out.println("Enter the UUID of the car to delete:");
        try {
            final UUID carId = UUID.fromString(scanner.next());
            final Optional<Car> carOptional = carService.getCars()
                    .stream()
                    .filter(car -> car.getCarId().equals(carId))
                    .findAny();
            carOptional.ifPresentOrElse((carService::deleteCar),
                    () -> System.err.println("Cannot delete: car does not exist.")
            );
        } catch (InputMismatchException e) {
            System.err.println("Invalid input: returning to main menu.");
        }
    }

    private static void getCars(final CarService carService, final Gson gson) {
        final List<Car> carsList = carService.getCars();
        if (carsList.isEmpty()) {
            System.err.println("There are no cars in the database.");
            return;
        }
        carsList.forEach(car -> System.out.println(gson.toJson(car)));
    }

    private static void getAvailableCars(final CarService carService, final Gson gson) {
        final List<Car> availableCars = carService.getCars()
                .stream()
                .filter(Car::isAvailable)
                .collect(Collectors.toUnmodifiableList());

        if (availableCars.isEmpty()) {
            System.err.println("No cars available.");
            return;
        }
        availableCars.forEach(car -> System.out.println(gson.toJson(car)));
    }

    private static void getAvailableElectricCars(final CarService carService, final Gson gson) {
        final List<Car> availableElectricCars = carService.getCars()
                .stream()
                .filter(Car::isElectric)
                .collect(Collectors.toUnmodifiableList());

        if (availableElectricCars.isEmpty()) {
            System.err.println("No electric cars available.");
            return;
        }

        availableElectricCars.forEach(car -> System.out.println(gson.toJson(car)));
    }

    // <---------- Reservation ---------->
    private static void createReservation(final ReservationService reservationService,
                                          final CarService carService,
                                          final CustomerService customerService,
                                          final Gson gson,
                                          final Scanner scanner) {
        if (carService.getCars().isEmpty()) {
            System.err.println("Cannot create reservation: no cars available to reserve.");
            return;
        }

        if (customerService.getCustomers().isEmpty()) {
            System.err.println("Cannot create reservation: no customers.");
            return;
        }

        try {
            printCustomers(customerService, gson);
            System.out.println("Enter the customer's UUID:");
            final UUID customerUUID = UUID.fromString(scanner.next());
            final Customer customer = customerService.findCustomerByUUID(customerUUID).orElseThrow();

            getAvailableCars(carService, gson);
            System.out.println("Enter the car's UUID:");
            final UUID carUUID = UUID.fromString(scanner.next());
            final Car car = carService.findCarByUUID(carUUID).orElseThrow();
            car.setAvailable(false);
            reservationService.createReservation(
                    new Reservation(customer, car, UUID.randomUUID(), LocalDateTime.now(), false)
            );
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cancelReservation(final ReservationService reservationService,
                                          final Gson gson,
                                          final Scanner scanner) {
        if (reservationService.getReservations().isEmpty()) {
            System.err.println("There are no active reservations to cancel.");
            return;
        }

        try {
            getReservations(reservationService, gson);
            System.out.println("Enter the reservation UUID:");
            final UUID reservationUUID = UUID.fromString(scanner.next());
            final Reservation reservation = reservationService.getReservationByUUID(reservationUUID).orElseThrow();
            reservation.getCar().setAvailable(true); // make the car available to reserve again
            reservationService.cancelReservation(reservation);
            System.out.println("Reservation with UUID " + reservation.getReservationId() + " successfully canceled.");
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void getReservations(final ReservationService reservationService, final Gson gson) {
        final List<Reservation> reservationsList = reservationService.getReservations();
        if (reservationsList.isEmpty()) {
            System.err.println("There are no active reservations.");
            return;
        }
        reservationsList.forEach(reservation -> System.out.println(gson.toJson(reservation)));
    }

    private static void getReservedCars(final ReservationService reservationService, final Gson gson) {
        final List<Reservation> reservationList = reservationService.getReservations();
        if (reservationList.isEmpty()) {
            System.err.println("There are no cars reserved.");
            return;
        }
        reservationList.forEach(reservation -> System.out.println(gson.toJson(reservation.getCar())));
    }
}
package com.mgbryan.reservation;

import com.mgbryan.car.CarService;
import com.mgbryan.customer.Customer;
import com.mgbryan.customer.CustomerDAO;
import com.mgbryan.customer.CustomerService;
import com.mgbryan.car.Car;
import com.mgbryan.car.CarDAO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public final class ReservationService {
    private final ReservationDAO reservationDAO;
    public ReservationService() {
        this.reservationDAO = new ReservationDAO();
    }

    /**
     * Creates a new reservation by prompting the admin to select a customer
     * from the Customer database, followed by a car from the Car database.
     * Once input has been verified a UUID, representing the reservation confirmation code,
     * and the date & time of the reservation are generated.
     * The newly created Reservation is stored in the Reservation database.
     */
    public void createReservation() {
        CustomerService.getAllCustomers();
        System.out.println("Select the customer requesting the reservation (UUID):");
        final Customer customer = Objects.requireNonNull(
                                  CustomerDAO.getCustomer(UUID.fromString(new Scanner(System.in).next())));

        if (Arrays.stream(CarDAO.getCars()).filter(Car::isAvailable).toArray(Car[]::new).length == 0) {
            System.out.println("There are no cars available to reserve. Returning to main menu.");
            return;
        }

        CarService.getAvailableCars();
        System.out.println("Select a car to reserve (ID):");
        final Car car = Objects.requireNonNull(
                                  CarDAO.getCar(new Scanner(System.in).next("^\\d{3}-\\d{2}-\\d{4}$")));
        if (ReservationDAO.getReservations()[0] != null &&
                Arrays.stream(ReservationDAO.getReservations())
                                            .anyMatch(o -> o.getCar().equals(car))) {
            System.out.println(car + " has already been reserved. Returning to main menu.");
            return;
        }

        car.setAvailable(false);
        final Reservation reservation =
                new Reservation(customer, car, UUID.randomUUID(), LocalDateTime.now(), false);
        reservationDAO.addReservation(reservation);
        System.out.println("✅ Reservation successfully created! Your reference ID is: " + reservation.getReservationId());
    }

    public void cancelReservation() {
        if (ReservationDAO.getReservations()[0] == null) {
            System.out.println("There are no active reservations to cancel. Returning to main menu.");
            return;
        }

        printAllReservations();
        System.out.println("Enter the reservation ID to cancel:");
        final Reservation RESERVATION = ReservationDAO.getReservation(UUID.fromString(new Scanner(System.in).next()));
        assert RESERVATION != null;
        RESERVATION.getCar().setAvailable(true);

        if (ReservationDAO.getReservations().length == 1 && ReservationDAO.getReservations()[0].equals(RESERVATION)) {
            ReservationDAO.getReservations()[0] = null;
        } else {
            reservationDAO.deleteReservation(RESERVATION);
            System.out.println("✅ Reservation " + RESERVATION.getReservationId() + " successfully canceled.");
        }
    }

    public void printAllReservations() {
        if (ReservationDAO.getReservations()[0] == null) {
            System.out.println("No active reservations. Returning to main menu.");
            return;
        }
        System.out.println("+--------------------+--------------------+--------------------------------------+------------------------------+");
        System.out.printf("| %-18s | %-18s | %-36s | %-28s |%n",
                "CUSTOMER", "CAR", "REFERENCE ID", "DATE & TIME RESERVED");
        System.out.println("+--------------------+--------------------+--------------------------------------+------------------------------+");

        for (Reservation reservation : ReservationDAO.getReservations()) {
            System.out.printf("| %-18s | %-18s | %-32s | %-28s |%n",
                    reservation.getCustomer(), reservation.getCar(), reservation.getReservationId(),
                    reservation.getDateTimeReserved());
        }
        System.out.println("+--------------------+--------------------+--------------------------------------+------------------------------+");
    }

    public void getAllReservedCars() {
        if (ReservationDAO.getReservations()[0] == null) {
            System.out.println("No cars reserved. Returning to main menu.");
            return;
        }

        for (Reservation reservation : ReservationDAO.getReservations()) {
            System.out.println(reservation.getCar());
        }
        System.out.println("Total cars reserved: " + ReservationDAO.getReservations().length);
    }
}
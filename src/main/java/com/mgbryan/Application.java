package com.mgbryan;

import com.mgbryan.car.CarService;
import com.mgbryan.customer.CustomerService;
import com.mgbryan.reservation.ReservationService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        final ReservationService reservationService = new ReservationService();
        final CustomerService customerService = new CustomerService();
        final CarService carService = new CarService();
        final Scanner scanner = new Scanner(System.in);

        runApp(scanner, carService, customerService, reservationService);
    }

    private static void runApp(final Scanner scanner, final CarService carService, final CustomerService customerService,
                               final ReservationService reservationService)
            throws InterruptedException {
        boolean notDone = true;

        do {
            printMainMenu();
            int userChoice;

            try {
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("⚠️Invalid entry, please try again:\n");
                Thread.sleep(1000);
                scanner.next();
                continue;
            }

            switch (userChoice) {
                case 0:
                    notDone = false;
                    System.out.println("Goodbye 🤠");
                    break;
                case 1:
                    reservationService.createReservation();
                    break;
                case 2:
                    reservationService.cancelReservation();
                    break;
                case 3:
                    reservationService.getAllReservedCars();
                    break;
                case 4:
                    reservationService.printAllReservations();
                    break;
                case 5:
                    CarService.getAllCars();
                    break;
                case 6:
                    carService.getAvailableElectrics();
                    break;
                case 7:
                    customerService.deleteCustomer();
                    break;
                case 8:
                    carService.deleteCar();
                    break;
                case 9:
                    customerService.addCustomer();
                    break;
                case 10:
                    carService.addCar();
                    break;
                case 11:
                    CarService.getAvailableCars();
                    break;
                default:
                    System.out.println("⚠️ Invalid option, please try again:");
                    break;

            }
            // Sleep for a while after handling the switch cases
            try {
                Thread.sleep(1500); // Sleep for 1000 milliseconds
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } while (notDone);
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("========== MAIN MENU ===========");
        System.out.println("    ==== RESERVATIONS ===     ");
        System.out.println("[ 1 ] ➡ Create Reservation");
        System.out.println("[ 2 ] ➡ Cancel Reservation");
        System.out.println("[ 3 ] ➡ Show All Reserved Cars");
        System.out.println("[ 4 ] ➡ Show All Reservations");
        System.out.println("    ====    CARS      ===     ");
        System.out.println("[ 4 ] ➡ Show All Available Cars");
        System.out.println("[ 5 ] ➡ Show All Available Electric Cars");
        System.out.println("[ 6 ] ➡ Add New Car To Database");
        System.out.println("[ 7 ] ➡ Delete Car From Database");
        System.out.println("    ====  CUSTOMERS   ===     ");
        System.out.println("[ 8 ] ➡ Add New Customer To Database");
        System.out.println("[ 9 ] ➡ Delete Customer To Database");
        System.out.println("[ 10 ] ➡ Show All Customers");
        System.out.println("[ 11 ] ➡ Update Customer Data (pending)");
        System.out.println("================================");
        System.out.println("[ 0 ] ➡ Exit App ❌");
    }
}
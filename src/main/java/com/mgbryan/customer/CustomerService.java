package com.mgbryan.customer;

import com.mgbryan.NotRemovableException;
import com.mgbryan.reservation.ReservationDAO;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public static void getAllCustomers() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-20s %-20s %20s", "FIRST NAME", "LAST NAME", "UUID");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");

        final Customer[] customers = CustomerDAO.getCustomers();
        for (Customer customer : customers) {
            System.out.printf("%-20s %-20s %25s",
                    customer.getFirstName(), customer.getLastName(), customer.getCustomerID());
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void addCustomer() {
        System.out.println("Enter the customer's first name:");
        final String FIRST_NAME = new Scanner(System.in).nextLine();
        System.out.println("Enter the customer's last name:");
        final String LAST_NAME = new Scanner(System.in).nextLine();

        final String FULL_NAME = FIRST_NAME + " " + LAST_NAME;

        if (!isValidName(FULL_NAME)) {
            throw new IllegalArgumentException("Invalid format.");
        }
        customerDAO.addCustomer(new Customer(FIRST_NAME, LAST_NAME, UUID.randomUUID()));
        System.out.println(FULL_NAME + " successfully added to database.");
    }

    public void deleteCustomer() {
        if (CustomerDAO.getCustomers().length == 0) {
            System.out.println("There are 0 customers to delete. Returning to main menu.");
            return;
        }

        getAllCustomers();
        System.out.println("Enter the Customer UUID to delete:");
        final UUID CUSTOMER_ID = UUID.fromString(new Scanner(System.in).next());

        try {
            if (ReservationDAO.getReservations()[0] != null && Arrays.stream(ReservationDAO.getReservations())
                      .anyMatch(o -> o.getCustomer().getCustomerID().equals(CUSTOMER_ID)))
            {
                throw new NotRemovableException("⚠️ Customer cannot be deleted because they have an active reservation.");
            }

            if (!isCustomer(CUSTOMER_ID)) {
                throw new CustomerNotFoundException("⚠️ Customer " + CUSTOMER_ID + " not found.");
            }
        } catch (CustomerNotFoundException | NotRemovableException e) {
            System.out.println(e.getMessage() + " Returning to main menu.");
            return;
        }

        customerDAO.removeCustomer(CUSTOMER_ID);
        System.out.println("✅ Customer " + CUSTOMER_ID + " successfully deleted from database.");
    }

    private boolean isCustomer(@NonNull final UUID customerID) {
        return Arrays.stream(CustomerDAO.getCustomers())
                     .anyMatch(customer -> customer.getCustomerID().compareTo(customerID) == 0);
    }

    private boolean isValidName(@NonNull final String fullName) {
        final String regex = "^[\\p{L} .'-]+$";
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(fullName);
        return matcher.find();
    }
}
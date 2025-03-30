package com.mglbryan.customer;

import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class CustomerDaoImpl implements CustomerDao {
    private static final List<Customer> customers = new ArrayList<>();

    public CustomerDaoImpl() {
        getCustomersFromCSV();
    }

    private static void getCustomersFromCSV() {
        final Scanner scanner;
        final File FILE = new File("src/main/java/com/mglbryan/customer/customers.csv");
        String[] tokens;

        try {
            scanner = new Scanner(FILE);
            while (scanner.hasNext()) {
              tokens = scanner.nextLine().split(",");
                if (!tokens[1].matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                        || !tokens[2].matches(
                        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
                    continue;
                }
                customers.add(new Customer(tokens[0], tokens[1], UUID.fromString(tokens[2])));
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void createCustomer(@NonNull final Customer customer) {
        customers.add(customer);
    }

    @Override
    public void deleteCustomer(@NonNull final Customer customer) {
        customers.remove(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }
}
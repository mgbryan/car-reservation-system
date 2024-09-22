package com.mgbryan.customer;

import com.github.javafaker.Faker;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;
import java.util.UUID;

@NoArgsConstructor
public final class CustomerDAO {
    private static Customer[] customers;

    // initialize Customer database with customers
    static {
        customers = new Customer[] {
                new Customer(new Faker().name().firstName(), new Faker().name().lastName(), UUID.randomUUID()),
                new Customer(new Faker().name().firstName(), new Faker().name().lastName(), UUID.randomUUID()),
                new Customer(new Faker().name().firstName(), new Faker().name().lastName(), UUID.randomUUID()),
                new Customer(new Faker().name().firstName(), new Faker().name().lastName(), UUID.randomUUID()),
                new Customer(new Faker().name().firstName(), new Faker().name().lastName(), UUID.randomUUID())
        };
    }


    /* CRUD OPERATIONS */
    void addCustomer(final @NonNull Customer customer) {
        customers = Arrays.copyOf(customers, customers.length + 1);
        customers[customers.length - 1] = customer;
    }

    static Customer[] getCustomers() {
        return customers;
    }

    public static Customer getCustomer(final @NonNull UUID customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) return customer;
        }
        return null;
    }

    void removeCustomer(final @NonNull UUID uuid) {
        customers = Arrays.stream(customers)
                          .filter(customer -> !customer.getCustomerID().equals(uuid))
                          .toArray(Customer[]::new);
    }
}
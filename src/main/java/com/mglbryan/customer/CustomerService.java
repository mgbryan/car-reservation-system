package com.mglbryan.customer;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@NonNull final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void createCustomer(@NonNull final Customer customer) {
        customerDao.createCustomer(customer);
    }

    public void deleteCustomer(@NonNull final Customer customer) {
        customerDao.deleteCustomer(customer);
    }

    public List<Customer> getCustomers() {
        return customerDao.getCustomers();
    }

    public Optional<Customer> findCustomerByUUID(@NonNull final UUID customerId) {
        return getCustomers()
                .stream()
                .filter(customer -> customer.getCustomerId().equals(customerId))
                .findFirst();
    }
}
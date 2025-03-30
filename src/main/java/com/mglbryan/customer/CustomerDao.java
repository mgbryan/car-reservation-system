package com.mglbryan.customer;

import lombok.NonNull;

import java.util.List;

public interface CustomerDao {
    void createCustomer(@NonNull final Customer customer);
    void deleteCustomer(@NonNull final Customer customer);
    List<Customer> getCustomers();
}
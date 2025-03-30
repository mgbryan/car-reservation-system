package com.mglbryan.customer;

import lombok.Getter;
import lombok.NonNull;
import java.util.UUID;

@Getter
public final class Customer {
    private final String name;
    private final String emailAddress;
    private final UUID customerId;

    public Customer(@NonNull final String name,
                    @NonNull final String emailAddress,
                    @NonNull final UUID customerId) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "{ NAME: " + this.getName() +
                ", EMAIL: " + this.getEmailAddress() +
                ", UUID: " + this.getCustomerId() +
                " }";
    }
}
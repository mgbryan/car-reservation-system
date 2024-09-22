package com.mgbryan.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public final @Data class Customer {
    private @NonNull String firstName;
    private @NonNull String lastName;
    private UUID customerID;

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
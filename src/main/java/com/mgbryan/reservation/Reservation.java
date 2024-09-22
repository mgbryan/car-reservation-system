package com.mgbryan.reservation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import com.mgbryan.customer.Customer;
import com.mgbryan.car.Car;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public @Data class Reservation {
    private @NonNull Customer customer;
    private @NonNull Car car;
    private @NonNull UUID reservationId;
    private @NonNull LocalDateTime dateTimeReserved;
    private @NonNull boolean isCanceled;
}
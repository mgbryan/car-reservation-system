package com.mglbryan.reservation;

import com.mglbryan.car.Car;
import com.mglbryan.customer.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public final class Reservation {
    private final Customer customer;
    private final Car car;
    private final UUID reservationId;
    private final LocalDateTime dateTimeReserved;
    private final boolean isCanceled;
}
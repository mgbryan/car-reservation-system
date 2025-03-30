package com.mglbryan.reservation;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ReservationDaoImpl implements ReservationDao {
    private static final List<Reservation> reservationList = new ArrayList<>();

    @Override
    public void createReservation(@NonNull final Reservation reservation) {
        reservationList.add(reservation);
    }

    @Override
    public void deleteReservation(@NonNull final Reservation reservation) {
        reservationList.remove(reservation);
    }

    @Override
    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservationList);
    }
}
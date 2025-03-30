package com.mglbryan.reservation;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(@NonNull final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void createReservation(@NonNull final Reservation reservation) {
        reservationDao.createReservation(reservation);
    }

    public void cancelReservation(@NonNull final Reservation reservation) {
        reservationDao.deleteReservation(reservation);
    }

    public Optional<Reservation> getReservationByUUID(@NonNull final UUID reservationId) {
        return getReservations()
                .stream()
                .filter(reservation -> reservation.getReservationId().equals(reservationId))
                .findFirst();
    }

    public List<Reservation> getReservations() {
        return reservationDao.getReservations();
    }
}
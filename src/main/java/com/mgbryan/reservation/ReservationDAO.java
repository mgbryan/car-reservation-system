package com.mgbryan.reservation;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.UUID;

public final class ReservationDAO {
    @Getter
    private static Reservation[] reservations;

    public ReservationDAO() {
        reservations = new Reservation[1];
    }

    /**
     * Returns the Reservation object whose UUID matches the input UUID.
     * @param RESERVATION_UUID The UUID of the object being sought
     * @return Reservation
     */
    public static Reservation getReservation(final @NonNull UUID RESERVATION_UUID) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equals(RESERVATION_UUID)) return reservation;
        }
        return null;
    }

    void addReservation(@NonNull Reservation reservation) {
        if (reservations[0] == null) {
            reservations[0] = reservation;
            return;
        }

        reservations = Arrays.copyOf(reservations, reservations.length + 1);
        reservations[reservations.length - 1] = reservation;
    }

    void deleteReservation(final @NonNull Reservation RESERVATION) {
        reservations = Arrays.stream(reservations)
                             .filter(reservation -> !reservation.equals(RESERVATION))
                             .toArray(Reservation[]::new);
    }
}
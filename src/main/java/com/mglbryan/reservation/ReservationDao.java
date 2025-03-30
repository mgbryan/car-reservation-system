package com.mglbryan.reservation;

import java.util.List;

public interface ReservationDao {
    void createReservation(Reservation reservation);
    void deleteReservation(Reservation reservation);
    List<Reservation> getReservations();
}

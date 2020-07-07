package mvcrest.reservations;

import mvcrest.user.User;

import java.io.IOException;
import java.util.List;

public class ReservationService {

    public List<Reservation> getReservations() throws IOException {
        return ReservationRepository.getReservations();
    }

    public List<Reservation> getUsersReservations(Integer id) throws IOException {
        return ReservationRepository.getUsersReservations(id);
    }

    public Reservation getReservationById(Integer id) throws IOException {
        return ReservationRepository.getReservationById(id);
    }

    public Reservation addReservation(Reservation reservation) throws IOException {
        return ReservationRepository.addReservation(reservation);
    }

    public Reservation makeAReservation(Reservation reservation) throws IOException {
        return ReservationRepository.makeAReservation(reservation);
    }

    public Reservation deleteUsersReservation(Integer id) throws IOException {
        return ReservationRepository.deleteUsersReservation(id);
    }


}

package mvcrest.reservations;


/*ID:Integer		- mora biti jedinstven, ne prikazuje se na frontendu
IsAvailable: Boolean	- da li je rezervacija dostupna ili je istekla (ako je prosao datum polaska)
Flight: Let		- rezervisani let
Ticket: Karta		- rezervisana karta
User: Korisnik		- korisnik koji je rezervisao kartu	*/

import mvcrest.flight.Flight;
import mvcrest.ticket.Ticket;
import mvcrest.user.User;

import java.util.concurrent.atomic.AtomicInteger;

public class Reservation {

    private Integer id;
    private String isAvailable;
    private Flight flight;
    private Ticket ticket;
    private User user;
    private static AtomicInteger idCounter = new AtomicInteger();

    @Override
    public String toString() {
        return id + "," + isAvailable + "," + flight.getId() + "," + ticket.getId() + "," + user.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId() {
        this.id = idCounter.getAndIncrement();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

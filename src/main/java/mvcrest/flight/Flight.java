package mvcrest.flight;


import mvcrest.city.City;
import mvcrest.ticket.Ticket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Flight {

    private Integer id;
    private ArrayList<Ticket> tickets;
    private City origin;
    private City destination;
    private static AtomicInteger idCount = new AtomicInteger();

    public Flight(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId() {
        this.id = idCount.getAndIncrement();
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public String ticketIdsToString(){
        String toReturn = "";
        if(tickets.size() != 0){
            for(Ticket t: tickets){
                toReturn += t.getId() + ";";
            }
            toReturn = toReturn.substring(0, toReturn.length()-1);
        }

        return toReturn;
    }

    @Override
    public String toString() {
        return id + "," + ticketIdsToString()+ "," + origin.getId() + "," + destination.getId();
    }


}

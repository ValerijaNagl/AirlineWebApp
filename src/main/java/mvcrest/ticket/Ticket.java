package mvcrest.ticket;


import mvcrest.airline.Airline;
import mvcrest.flight.Flight;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket implements Serializable {

    private Integer id;
    private String oneWay;
    private String departDate;
    private String returnDate;
    private Airline airline;
    private int flight_id;
    private Flight flight;
    private Long count;
    private static AtomicInteger idCount = new AtomicInteger();

    public Ticket(){}


    @Override
    public String toString() {
        return id + "," + oneWay + "," + departDate + "," + returnDate  +"," + count + "," + airline.getId() + "," + flight_id;
    }

    public Integer getId() {
        return id;
    }

    public String getOneWay() {
        return oneWay;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getDepartDate() {
        return departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId() {
        this.id = idCount.getAndIncrement();
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public void setOneWay(String oneWay) {
        this.oneWay = oneWay;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

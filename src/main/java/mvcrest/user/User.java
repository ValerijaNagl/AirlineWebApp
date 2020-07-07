package mvcrest.user;


import mvcrest.reservations.Reservation;
import mvcrest.ticket.Ticket;

import java.awt.print.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String JWTToken;
    private ArrayList<Reservation> bookings;
    private static AtomicInteger idCounter = new AtomicInteger();
    private mvcrest.user.TypeOfUser typeOfUser;

    public User(Integer id, String username, String password, TypeOfUser typeOfUser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bookings = new ArrayList<>();
        this.typeOfUser = typeOfUser;
    }

    public User(){
        bookings = new ArrayList<>();
    }

    public String getJWTToken() {
        return JWTToken;
    }

    public void setJWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public void setBookings(ArrayList<Reservation> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<Reservation> getBookings() {
        return bookings;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTypeOfUser(TypeOfUser typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public TypeOfUser getTypeOfUser() {
        return typeOfUser;
    }

    public String bookingIdsToString(){
        String toReturn = "";
        if(bookings.size() != 0){
            for(Reservation t: bookings){
                toReturn += t.getId() + ";";
            }
            toReturn = toReturn.substring(0, toReturn.length()-1);
        }

        return toReturn;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password  + "," + bookingIdsToString() + "," + typeOfUser.toString();
    }
}

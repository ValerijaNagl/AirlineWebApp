package mvcrest.reservations;

import mvcrest.flight.Flight;
import mvcrest.flight.FlightRepository;
import mvcrest.ticket.Ticket;
import mvcrest.ticket.TicketRepository;
import mvcrest.user.TypeOfUser;
import mvcrest.user.User;
import mvcrest.user.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class ReservationRepository {

    private static int id = 0;

//    static{
//        try {
//            File f = new File("reservations.txt");
//            System.out.println(f.getAbsolutePath());
//            BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getPath()));
//            writer.write("");
//            writer.flush();
//            generateReservations();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void generateReservations() throws  IOException {
        for (int i = 0; i < 10; i++) {
            int id_user = i%4;
            Reservation reservation = new Reservation();
            User u = new User();
            u.setId(i%4);
            Ticket t = new Ticket();
            t.setId(i%14);
            Flight f = new Flight();
            f.setId(i%3);
            reservation.setFlight(f);
            reservation.setTicket(t);
            reservation.setUser(u);
            reservation.setIsAvailable("Yes");
            addReservation(reservation);
        }
    }

    public static synchronized List<Reservation> getReservations() throws IOException {

        ArrayList<Reservation> reservations = new ArrayList<>();
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                Reservation reservation = getReservation(niz);
                reservations.add(reservation);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return reservations;
    }

    public static synchronized boolean validReservation(Reservation reservation){
        if(reservation.getIsAvailable().equals("") || reservation.getFlight() == null || reservation.getTicket() == null ||
                reservation.getUser() == null) {
            return false;
        }else{
            return true;
        }
    }

    public static synchronized Reservation addReservation(Reservation reservation) throws IOException {

        if(!validReservation(reservation)) return null;

        BufferedWriter output = null;
        reservation.setId(id);
        id++;
        String text = reservation.toString();
        try {
            File f = new File("reservations.txt");
            output = new BufferedWriter(new FileWriter(f, true));
            output.append(text);
            output.append("\n");
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                output.close();
            }
        }
        return reservation;
    }

    public static synchronized Reservation makeAReservation(Reservation reservation) throws IOException {

        if(!validReservation(reservation)) return null;

        BufferedWriter output = null;
        reservation.setId(id);
        id++;

        User u = UserRepository.addUsersReservation(reservation,reservation.getUser().getId());
        reservation.setUser(u);
        String text = reservation.toString();
        try {
            File f = new File("reservations.txt");
            output = new BufferedWriter(new FileWriter(f, true));
            output.append(text);
            output.append("\n");
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if (output != null ) {
                output.close();
            }
        }
        return reservation;
    }

    public static synchronized Reservation getReservationById(Integer id) throws IOException {
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int reservationId = Integer.parseInt(niz[0]);
                if(reservationId == id) {
                    return getReservation(niz);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return  null;
    }

    public static synchronized Reservation getUsersReservationById(Integer id) throws IOException {
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int reservationId = Integer.parseInt(niz[0]);
                if(reservationId == id) {
                    return getUsersReservation(niz);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return  null;
    }

    public static synchronized List<Reservation> getUsersReservations(Integer id) throws IOException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                String[] niz = line.split(",");
                int userId = Integer.parseInt(niz[4]);
                if(userId == id) {
                    Reservation r = getUsersReservation(niz);
                    reservations.add(r);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return  reservations;
    }


    public static Reservation deleteUsersReservation(Integer id) throws IOException {
        // brisemo rezervaciju iz baze
        // u korisnikov bazi brisemo rezervaciju
        ArrayList<Reservation> reservations = new ArrayList<>();
        Reservation r  = null;
        File f = new File("reservations.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            BufferedWriter output = new BufferedWriter(new FileWriter(f, true));
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int bookingId = Integer.parseInt(niz[0]);
                if(bookingId != id) {
                    Reservation reservation = getReservation(niz);
                    reservations.add(reservation);
                }else{
                    r = getReservation(niz);
                }
                line = br.readLine();
            }

            br.close();
            File file = new File("reservations.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Reservation reservation : reservations){
                output.append(reservation.toString());
                output.append("\n");
            }

            output.close();
            if(r != null)
            UserRepository.deleteUsersReservation(r,r.getUser().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(reservations.toString());
        return  r;
    }

    // BRISANJE REZERVACIJA KADA SE BRISE KARTA (KADA SE BRISE AVIONSKA KOMPANIJA)
    public static void deleteReservationByTicketId(Integer id) throws IOException {

        ArrayList<Reservation> reservations = new ArrayList<>();
        Reservation r  = null;
        File f = new File("reservations.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            BufferedWriter output = null;
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int ticketId = Integer.parseInt(niz[3]);
                if(ticketId != id) {
                    Reservation reservation = getReservation(niz);
                    reservations.add(reservation);
                }else{
                    r = getReservation(niz);
                }
                line = br.readLine();
            }

            br.close();
            File file = new File("reservations.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Reservation reservation : reservations){
                output.append(reservation.toString());
                output.append("\n");
            }

            output.close();

            if(r != null)
                UserRepository.deleteUsersReservation(r,r.getUser().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static Reservation getReservation(String[] niz) throws IOException {

        Reservation reservation = new Reservation();
        reservation.setId(Integer.parseInt(niz[0]));
        reservation.setIsAvailable(niz[1]);

        Flight flight = FlightRepository.getTicketsFlightById(Integer.parseInt(niz[2]));
        Ticket ticket = TicketRepository.getFlightsTicketById(Integer.parseInt(niz[3]));
        User user = UserRepository.getReservationsUserById(Integer.parseInt(niz[4]));
        user.setId(Integer.parseInt(niz[4]));
        reservation.setFlight(flight);
        reservation.setTicket(ticket);
        reservation.setUser(user);

        return reservation;
    }

    private static Reservation getUsersReservation(String[] niz)  throws IOException{
        Reservation reservation = new Reservation();
        reservation.setId(Integer.parseInt(niz[0]));
        reservation.setIsAvailable(niz[1]);

        Flight flight = FlightRepository.getTicketsFlightById(Integer.parseInt(niz[2]));
        Ticket ticket = TicketRepository.getFlightsTicketById(Integer.parseInt(niz[3]));
        User user = new User();
        user.setId(Integer.parseInt(niz[4]));
        reservation.setFlight(flight);
        reservation.setTicket(ticket);
        reservation.setUser(user);

        return reservation;

    }


}

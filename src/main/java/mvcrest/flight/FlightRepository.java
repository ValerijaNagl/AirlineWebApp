package mvcrest.flight;

import mvcrest.city.City;
import mvcrest.city.CityRepository;
import mvcrest.reservations.Reservation;
import mvcrest.ticket.Ticket;
import mvcrest.ticket.TicketRepository;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FlightRepository {

    static{
        String upis1 = "0,0;1;2,3,4\n";
        String upis2 = "1,3;4;5,1,2\n";
        String upis3 = "2,6;7,5,6\n";
        String upis4 = "3,8,7,8\n";

        BufferedWriter output = null;
        File f = new File("flights.txt");
        try {
            output = new BufferedWriter(new FileWriter(f, true));
            output.write("");
            output.flush();
            output.append(upis1);
            output.append(upis2);
            output.append(upis3);
            output.append(upis4);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private static void generateFlights() throws  IOException {
        for (int i = 0; i < 3; i++) {
            ArrayList<Ticket> tickets = new ArrayList<>();
            if(i == 0){
                tickets.add(TicketRepository.getTicketById(0));
                tickets.add(TicketRepository.getTicketById(1));
                tickets.add(TicketRepository.getTicketById(2));
                tickets.add(TicketRepository.getTicketById(3));
                tickets.add(TicketRepository.getTicketById(4));
            }else if(i == 1){
                tickets.add(TicketRepository.getTicketById(5));
                tickets.add(TicketRepository.getTicketById(6));
                tickets.add(TicketRepository.getTicketById(7));
                tickets.add(TicketRepository.getTicketById(8));
                tickets.add(TicketRepository.getTicketById(9));
            }else{
                tickets.add(TicketRepository.getTicketById(10));
                tickets.add(TicketRepository.getTicketById(11));
                tickets.add(TicketRepository.getTicketById(12));
                tickets.add(TicketRepository.getTicketById(13));
                tickets.add(TicketRepository.getTicketById(14));

            }
            Flight f = new Flight();
            f.setId();
            f.setOrigin(CityRepository.getCities().get(i));
            f.setDestination(CityRepository.getCities().get(i + 1));
            f.setTickets(tickets);
            addFlight(f);
        }
    }

    public static synchronized List<Flight> getFlights() throws IOException {

        ArrayList<Flight> flights = new ArrayList<>();
        File f = new File("flights.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                Flight flight = getFlight(niz);
                flights.add(flight);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return flights;
    }


    public static synchronized Flight addFlight(Flight flight) throws IOException {

        BufferedWriter output = null;
        flight.setId();
        String text = flight.toString();
        try {
            File f = new File("flights.txt");
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
        return flight;
    }


    public static synchronized Flight getFlightById(Integer id) throws IOException {

        File f = new File("flights.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                if(Integer.parseInt(niz[0]) == id) {
                    return getFlight(niz);
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

    public static synchronized Flight getTicketsFlightById(Integer id) throws IOException {

        File f = new File("flights.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                String[] niz = line.split(",");
                if(Integer.parseInt(niz[0]) == id) {
                    Flight flight = new Flight();
                    flight.setId(Integer.parseInt(niz[0]));
                    City origin = CityRepository.getCityById(Integer.parseInt(niz[2]));
                    City destination = CityRepository.getCityById(Integer.parseInt(niz[3]));
                    flight.setOrigin(origin);
                    flight.setDestination(destination);
                    return flight;
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

    public static synchronized boolean deleteDeletedTicketInFlight(Integer ticket_id) throws FileNotFoundException {

        if(ticket_id==null) return false;
        ArrayList<Flight> flights = new ArrayList<>();
        File f = new File("flights.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                Flight flight = getFlight(niz);
                ArrayList<Ticket> tickets = new ArrayList<>();

                for(Ticket t: flight.getTickets()) {
                    if(t.getId() != ticket_id){
                        tickets.add(t);
                    }
                }
                flight.getTickets().clear();
                flight.setTickets(tickets);

                flights.add(flight);
                line = br.readLine();
            }
            br.close();
            BufferedWriter output = null;
            File file = new File("flights.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Flight flight : flights){
                output.append(flight.toString());
                output.append("\n");
            }

            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



    private static synchronized Flight getFlight(String[] niz) throws  IOException{
        Flight flight = new Flight();
        flight.setId(Integer.parseInt(niz[0]));

        String[] karteNiz = niz[1].split(";");
        ArrayList<Ticket> tickets = new ArrayList<>();

        for (int i= 0; i<karteNiz.length; i++) {
            Ticket ticket = new Ticket();
            ticket.setId(Integer.parseInt(karteNiz[i]));
            tickets.add(ticket);
        }

        flight.setTickets(tickets);
        City origin = CityRepository.getCityById(Integer.parseInt(niz[2]));
        City destination = CityRepository.getCityById(Integer.parseInt(niz[3]));
        flight.setOrigin(origin);
        flight.setDestination(destination);
        return flight;
    }

    private static synchronized Flight getRealFlight(String[] niz) throws  IOException{
        Flight flight = new Flight();
        flight.setId(Integer.parseInt(niz[0]));

        String[] karteNiz = niz[1].split(";");
        ArrayList<Ticket> tickets = new ArrayList<>();

        for (int i= 0; i<karteNiz.length; i++) {
            Ticket ticket = TicketRepository.getFlightsTicketById(Integer.parseInt(karteNiz[i]));
            tickets.add(ticket);
        }

        flight.setTickets(tickets);
        City origin = CityRepository.getCityById(Integer.parseInt(niz[2]));
        City destination = CityRepository.getCityById(Integer.parseInt(niz[3]));
        flight.setOrigin(origin);
        flight.setDestination(destination);
        return flight;
    }


}

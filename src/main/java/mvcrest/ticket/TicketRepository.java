package mvcrest.ticket;


import mvcrest.airline.Airline;
import mvcrest.airline.AirlineRepository;
import mvcrest.flight.Flight;
import mvcrest.flight.FlightRepository;
import mvcrest.reservations.Reservation;
import mvcrest.reservations.ReservationRepository;
import mvcrest.user.User;

import javax.lang.model.type.ArrayType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TicketRepository {

    private static String[] depart_date = {"23-04-2019","23-07-2019","22-02-2019","22-11-2019","17-08-2019","16-05-2019","15-07-2019"};
    private static String[] return_date = {"09-04-2019","11-09-2019","27-03-2019","26-01-2019","26-10-2019","03-04-2019","16-06-2019"};
    private static int id = 8;

    static{
        try {
            String s1 = "0,No,26-01-2019,23-04-2019,15,0,0\n";
            String s2 = "1,No,26-10-2020,23-11-2020,10,1,1\n";
            String s3= "2,No,26-09-2020,20-10-2020,12,1,2\n";
            String s4= "3,Yes,09-04-2019,11-09-2019,12,3,0\n";
            String s5= "4,Yes,13-08-2020,20-08-2020,7,2,0\n";
            String s6= "5,Yes,13-08-2020,20-08-2020,8,2,3\n";
            String s7= "6,No,13-08-2020,20-08-2020,12,1,3\n";
            String s8= "7,Yes,13-08-2020,20-08-2020,12,3,0\n";

            BufferedWriter output = null;
            File f = new File("tickets.txt");
            output = new BufferedWriter(new FileWriter(f, true));
            output.write("");
            output.flush();
            output.append(s1);
            output.append(s2);
            output.append(s3);
            output.append(s4);
            output.append(s5);
            output.append(s6);
            output.append(s7);
            output.append(s8);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateTickets() throws ParseException, IOException {

        Random random = new Random();

        for (int i = 0; i < 15; i++) {

            Ticket ticket = new Ticket();

            ticket.setCount((long) (random.nextInt(20)+1));
            SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
            String departDate = depart_date[random.nextInt(depart_date.length)];
            String returnDate = return_date[random.nextInt(return_date.length)];
            Date d1 = sdformat.parse(departDate);
            Date d2 = sdformat.parse(returnDate);


            if(d1.compareTo(d2) < 0){
                ticket.setDepartDate(departDate);
                ticket.setReturnDate(returnDate);
            }else{
                ticket.setDepartDate(returnDate);
                ticket.setReturnDate(departDate);
            }

            if(random.nextInt(100) % 2 == 0){
                ticket.setOneWay("Yes");
            }else{
                ticket.setOneWay("No");
            }

            Flight flight = new Flight();
            if(i%2==0){
                flight.setId(1);
            }else{
                flight.setId(0);
            }

            ticket.setAirline(AirlineRepository.getAirlines().get(random.nextInt(4)));
            addTicket(ticket);
        }

    }

    public static synchronized List<Ticket> getTickets() throws IOException {

        ArrayList<Ticket> tickets = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));

        try {
            String line = br.readLine();

            while (line != null) {
                System.out.println(line);
                String[] niz = line.split(",");
                Ticket ticket =  getTicket(niz);
                tickets.add(ticket);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return tickets;
    }

    public static synchronized Ticket getTicketById(Integer id) throws IOException {
        File f = new File("tickets.txt");
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int ticketId = Integer.parseInt(niz[0]);
                if(ticketId == id) {
                    return getTicket(niz);
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

    public static synchronized  ArrayList<Ticket> getTicketsByAirlineId(Integer id) throws IOException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        File f = new File("tickets.txt");
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int airlineId = Integer.parseInt(niz[5]);
                if(airlineId == id) {
                    Ticket t = getTicket(niz);
                    tickets.add(t);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return  tickets;
    }


    public static synchronized Ticket deleteTicketById(Integer id) throws IOException {

        if(id == null) return null;
        Ticket toReturn = null;
        ArrayList<Ticket> tickets = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));

        try {
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                String[] niz = line.split(",");
                Ticket ticket =  getTicket(niz);
                if(ticket.getId() != id){
                        tickets.add(ticket);
                }else{
                    toReturn = ticket;
                }
                line = br.readLine();
            }

            br.close();
            BufferedWriter output = null;
            File file = new File("tickets.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Ticket t : tickets){
                output.append(t.toString());
                output.append("\n");
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(tickets.toString());
        }
        return toReturn;
    }


    public static synchronized Ticket deleteTicketByAirlinesId(Integer id) throws IOException {

        if(id == null) return null;

        ArrayList<Ticket> tickets = new ArrayList<>();
        Ticket toReturn = null;
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));
        try {
            String line = br.readLine();
            while (line != null) {

                String[] niz = line.split(",");
                int airline_id = Integer.parseInt(niz[5]);
                if(airline_id != id){
                    Ticket ticket = getSimpleTicket(niz);
                    tickets.add(ticket);
                }else{
                    toReturn = getSimpleTicket(niz);
                }
                line = br.readLine();
            }
            br.close();
            BufferedWriter output = null;
            File file = new File("tickets.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Ticket t : tickets){
                output.append(t.toString());
                output.append("\n");
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Karte bez aviokompanije" + tickets.toString());
        }
        System.out.println("Ticket id" + toReturn.getId());

     // brisemo kartu iz liste njenog leta kao i rezervaciju koja sadrzi obrisanu kartu
        boolean answer = FlightRepository.deleteDeletedTicketInFlight(toReturn.getId());
        ReservationRepository.deleteReservationByTicketId(toReturn.getId());

        if(answer)
            return toReturn;
        else return null;
    }

    public static synchronized boolean validTicket(Ticket ticket){
        if(ticket.getOneWay().equals("") || ticket.getFlight() == null || ticket.getFlight_id()<0 ||
        ticket.getCount() < 0 || ticket.getCount().toString().equals("") || ticket.getDepartDate().equals("")
                || ticket.getReturnDate().equals("")){
            return false;
        }else{
            try{
                Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(ticket.getDepartDate());
                Date date2=new SimpleDateFormat("dd-MM-yyyy").parse(ticket.getReturnDate());
                if(date2.getTime() < date1.getTime()){
                    return false;
                }
            }catch(ParseException e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

    }

    public static synchronized Ticket addTicket(Ticket ticket) throws IOException {

        if(!validTicket(ticket)) return null;

        BufferedWriter output = null;
        ticket.setId(id);
        id++;
        String text = ticket.toString();
        try {
            File file = new File("tickets.txt");
            output = new BufferedWriter(new FileWriter(file, true));
            output.append(text);
            output.append("\n");
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                output.close();
            }
        }
        return ticket;
    }

// METODA KOJU KORISITIMO PRI UZIMANJU LETOVA IZ BAZE; PAZIMO DA NE POZIVAMO FLIGHT REPOSITORY KAKO SE NE BI USLO U flights.txt
    public static synchronized Ticket getFlightsTicketById(Integer id) throws IOException {
        File f = new File("tickets.txt");
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int ticketId = Integer.parseInt(niz[0]);
                if(ticketId == id) {
                    Ticket ticket = new Ticket();
                    ticket.setId(Integer.parseInt(niz[0]));
                    ticket.setOneWay(niz[1]);
                    ticket.setDepartDate(niz[2]);
                    ticket.setReturnDate(niz[3]);
                    ticket.setCount(Long.parseLong(niz[4]));
                    Airline a = AirlineRepository.getAirlineById(Integer.parseInt(niz[5]));
                    ticket.setAirline(a);
                    ticket.setFlight_id(Integer.parseInt(niz[6]));
                    Flight flight = new Flight();
                    flight.setId(Integer.parseInt(niz[6]));
                    ticket.setFlight(flight);
                    return ticket;
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

    public static synchronized Ticket decrementCount(Integer id) throws IOException {
        Ticket toReturn = null;
        ArrayList<Ticket> tickets = new ArrayList<>();
        File f = new File("tickets.txt");
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int ticketId = Integer.parseInt(niz[0]);
                if(ticketId == id) {
                    toReturn = getTicket(niz);
                    long count = toReturn.getCount();
                    count--;
                    if(count>0){
                        toReturn.setCount(count);
                        tickets.add(toReturn);
                    }
                }else{
                    Ticket ticket = getTicket(niz);
                    tickets.add(ticket);
                }
                line = br.readLine();
            }
            BufferedWriter output = null;
            File file = new File("tickets.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Ticket t : tickets){
                output.append(t.toString());
                output.append("\n");
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return  toReturn;
    }

    public static synchronized Ticket changeTicket(Ticket ticket) throws IOException {

        if(!validTicket(ticket)) return null;

        Ticket toReturn = null;
        ArrayList<Ticket> tickets = new ArrayList<>();
        File f = new File("tickets.txt");
        BufferedReader br = new BufferedReader(new FileReader("tickets.txt"));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int ticketId = Integer.parseInt(niz[0]);
                if(ticketId == ticket.getId()) {
                    toReturn = ticket;
                    tickets.add(ticket);
                }else{
                    Ticket t = getTicket(niz);
                    tickets.add(t);
                }
                line = br.readLine();
            }
            BufferedWriter output = null;
            File file = new File("tickets.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Ticket t : tickets){
                output.append(t.toString());
                output.append("\n");
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return  toReturn;
    }

    private static synchronized Ticket getTicket(String[] niz) throws IOException {
        Ticket ticket = new Ticket();
        ticket.setId(Integer.parseInt(niz[0]));
        ticket.setOneWay(niz[1]);
        ticket.setDepartDate(niz[2]);
        ticket.setReturnDate(niz[3]);
        ticket.setCount(Long.parseLong(niz[4]));
        Airline a = AirlineRepository.getAirlineById(Integer.parseInt(niz[5]));
        ticket.setAirline(a);
        ticket.setFlight_id(Integer.parseInt(niz[6]));
        Flight f = FlightRepository.getTicketsFlightById(Integer.parseInt(niz[6]));
        ticket.setFlight(f);
        return ticket;
    }


    private static synchronized Ticket getSimpleTicket(String[] niz) throws IOException {
        Ticket ticket = new Ticket();
        ticket.setId(Integer.parseInt(niz[0]));
        ticket.setOneWay(niz[1]);
        ticket.setDepartDate(niz[2]);
        ticket.setReturnDate(niz[3]);
        ticket.setCount(Long.parseLong(niz[4]));
        Airline a = new Airline();
        a.setId(Integer.parseInt(niz[5]));
        ticket.setAirline(a);
        ticket.setFlight_id(Integer.parseInt(niz[6]));
        Flight f = new Flight();
        f.setId(Integer.parseInt(niz[6]));
        ticket.setFlight(f);
        return ticket;
    }

}

package mvcrest.airline;


import mvcrest.ticket.Ticket;
import mvcrest.ticket.TicketRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AirlineRepository {

    private static String[] FIRST_WORD = {"SINGAPORE","QATAR","Cathay Pacific","Qantas"};
    private static String[] SECOND_WORD = {"Airways","Airlines","Air","Aviation"};
    private static int id = 0;

    static {
        try {
            File f = new File("airlines.txt");
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getPath()));
            writer.write("");
            writer.flush();
            generateAirlines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void generateAirlines() throws IOException {

        for (int i = 0; i < 4; i++) {
            Airline airline = new Airline();
            airline.setId();
            String name = FIRST_WORD[i] + " " + SECOND_WORD[i];
            airline.setName(name);
            addAirline(airline);
        }
    }

    public static synchronized boolean validAirline(Airline airline){
        if(!airline.getName().equals("")){
            return true;
        }else{
            return false;
        }
    }

    public static synchronized boolean validAirline2(Airline airline){
        if(!airline.getName().equals("") || airline.getId()==null){
            return true;
        }else{
            return false;
        }
    }

    public static synchronized Airline addAirline(Airline airline) throws IOException {

        BufferedWriter output = null;
        airline.setId(id);
        String text = airline.toString();

        if(!validAirline(airline)) return null;

        try {
            ArrayList<Airline> airlines = new ArrayList<>();
            File f = new File("airlines.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line = br.readLine();

            while (line != null) {
                String[] niz = line.split(",");
                int airline_id = Integer.parseInt(niz[0]);
                String name = niz[1];
                // ne moze da se doda avionska kompanija sa postojecim imenom
                if(airline.getName().equals(name)){
                    return null;
                }
                line = br.readLine();
            }
            br.close();

            File f2 = new File("airlines.txt");
            output = new BufferedWriter(new FileWriter(f2, true));
            output.append(text);
            output.append("\n");
            id++;
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                output.close();
            }
        }
        return airline;
    }

    public static synchronized Airline changeAirline(Airline airline) throws IOException {

        if(!validAirline2(airline)) return null;

        ArrayList<Airline> airlines = new ArrayList<>();
        File f = new File("airlines.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));

        try {
            String line = br.readLine();

            while (line != null) {
                String[] niz = line.split(",");
                int airline_id = Integer.parseInt(niz[0]);

                if(airline_id == airline.getId()){
                    Airline a = new Airline();
                    a.setId(airline.getId());
                    a.setName(airline.getName());
                    airlines.add(a);
                }else{
                    Airline a = new Airline();
                    a.setId(Integer.parseInt(niz[0]));
                    a.setName(niz[1]);
                    airlines.add(a);
                }
                line = br.readLine();
            }

            int brojac = 0;
            for(Airline a : airlines){
                if(a.equals(airline.getName()))
                    brojac++;
            }
            // ne sme da se poklopi novo ime sa imenom vec druge postojece kompanije
            if(brojac>1) return null;


            br.close();

            BufferedWriter output = null;
            File file = new File("airlines.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Airline a : airlines){
                output.append(a.toString());
                output.append("\n");
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return airline;
    }

    public static synchronized Airline deleteAirline(Integer id) throws IOException {

        if(id == null) return null;
        Airline toReturn = null;
        ArrayList<Airline> airlines = new ArrayList<>();
        File f = new File("airlines.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));

        try {
            String line = br.readLine();

            while (line != null) {
                String[] niz = line.split(",");
                int airline_id = Integer.parseInt(niz[0]);
                if(airline_id != id){
                    Airline a = new Airline();
                    a.setId(Integer.parseInt(niz[0]));
                    a.setName(niz[1]);
                    airlines.add(a);
                }else{
                    toReturn = new Airline();
                    toReturn.setId(id);
                    toReturn.setName(niz[1]);
                }

                line = br.readLine();
            }
            br.close();

            BufferedWriter output = null;
            File file = new File("airlines.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.flush();

            for(Airline a : airlines){
                output.append(a.toString());
                output.append("\n");
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Ticket t = TicketRepository.deleteTicketByAirlinesId(id);
        if(t != null)
            return toReturn;
        else return null;
    }




    /**
     * Vraca sve avio kompanije u sistemu.
     * Ako je name dato onda ce vratiti listu svih korisnika sa tim imenom.
     * @return
     */
    public static synchronized List<Airline> getAirlines() throws IOException {
        ArrayList<Airline> airlines = new ArrayList<>();
        File f = new File("airlines.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));

        try {
            String line = br.readLine();

            while (line != null) {
                String[] niz = line.split(",");
                Airline a = new Airline();
                a.setId(Integer.parseInt(niz[0]));
                a.setName(niz[1]);
                airlines.add(a);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        return airlines;
    }


    public static synchronized Airline getAirlineById(Integer id) throws IOException {

        File f = new File("airlines.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));

        try {
            String line = br.readLine();

            while (line != null) {
                String[] niz = line.split(",");

                if(Integer.parseInt(niz[0]) == id) {
                    Airline a = new Airline();
                    a.setId(Integer.parseInt(niz[0]));
                    a.setName(niz[1]);
                    return a;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        return null;

    }


}

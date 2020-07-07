package mvcrest.user;

import mvcrest.airline.Airline;
import mvcrest.reservations.Reservation;
import mvcrest.reservations.ReservationRepository;
import mvcrest.ticket.Ticket;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserRepository {

    private static int id = 0;

    static{
        try {
            File f = new File("users.txt");
            System.out.println(f.getAbsolutePath());
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getPath()));
            writer.write("");
            writer.flush();
            generateUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateUsers() throws  IOException {
        for (int i = 0; i < 4; i++) {
            ArrayList<Reservation> reservations = new ArrayList<>();
            if(i%2==0){
                if(i==0){
                    Reservation r = new Reservation();
                    r.setId(0);
                    reservations.add(r);
                    r.setId(4);
                    reservations.add(r);
                    r.setId(8);
                    reservations.add(r);
                }else{
                    Reservation r = new Reservation();
                    r.setId(2);
                    reservations.add(r);
                    r.setId(6);
                    reservations.add(r);
                    r.setId(10);
                    reservations.add(r);

                }
                User u = new User();
                u.setPassword("pass");
                u.setUsername("name"+i);
                u.setJWTToken("");
                u.setTypeOfUser(TypeOfUser.REGULAR);
                u.setBookings(reservations);
                addUser(u);
            }else{
                if(i==1){
                    Reservation r = new Reservation();
                    r.setId(1);
                    reservations.add(r);
                    r.setId(5);
                    reservations.add(r);
                    r.setId(9);
                    reservations.add(r);
                }else{
                    Reservation r = new Reservation();
                    r.setId(3);
                    reservations.add(r);
                    r.setId(7);
                    reservations.add(r);
                }
                User u = new User();
                u.setPassword("pass");
                u.setUsername("admin"+i);
                u.setJWTToken("");
                u.setTypeOfUser(TypeOfUser.ADMIN);
                u.setBookings(reservations);
                addUser(u);
            }
        }
    }

    public static synchronized List<User> getUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>();
        File f = new File("users.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                String[] niz = line.split(",");
                User user = getUser(niz);
                users.add(user);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return users;
    }


    public static synchronized boolean validUser(User user){
        if(!(user.getUsername().equals("") || user.getPassword().equals("") || user.getTypeOfUser().equals(""))){
            return true;
        }else{
            return false;
        }
    }

    public static synchronized User addUser(User user) throws IOException {
        if(!validUser(user)) return null;
        BufferedWriter output = null;
        user.setId(id);
        id++;
        ArrayList<Reservation> bookings = new ArrayList<>();
        user.setBookings(bookings);
        user.setJWTToken("");
        String text = user.toString();
        try {
            File f = new File("users.txt");
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
        return user;
    }

    public static synchronized int getNumberOfReservations(Integer id) throws IOException {

        if(id == null) return 0;

        int numOfBookings = 0;
        File f = new File("users.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                String[] niz = line.split(",");
                int userId = Integer.parseInt(niz[0]);
                if(userId == id){
                    User user = getUser(niz);
                    numOfBookings = user.getBookings().size();
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return numOfBookings;
    }

    public static synchronized User getReservationsUserById(Integer id) throws IOException {
        File f = new File("users.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int userId = Integer.parseInt(niz[0]);

                if(userId == id) {
                    User user = new User();
                    user.setId(Integer.parseInt(niz[0]));
                    user.setUsername(niz[1]);
                    user.setPassword(niz[2]);
                    TypeOfUser typeOfUser = TypeOfUser.valueOf(niz[4]);
                    ArrayList<Reservation> bookings = new ArrayList<>();
                    user.setTypeOfUser(typeOfUser);
                    if(!niz[3].equals("")) {
                        String[] bookingsArray = niz[3].split(";");


                        for (int i = 0; i < bookingsArray.length; i++) {
                            Reservation booking = new Reservation();
                            booking.setId(Integer.parseInt(bookingsArray[i]));
                            bookings.add(booking);
                        }
                    }
                    user.setBookings(bookings);
                    return user;
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


    public static synchronized User getUserById(Integer id) throws IOException {
        File f = new File("users.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int userId = Integer.parseInt(niz[0]);
                if(userId == id) {
                    return getUser(niz);
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

    public static synchronized User deleteUserById(Integer id) throws IOException {
        File f = new File("users.txt");
        ArrayList<User>  newList = new ArrayList<>();
        User toReturn = null;
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
        BufferedWriter output = new BufferedWriter(new FileWriter(f, true));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int userId = Integer.parseInt(niz[0]);
                if(userId == id) {
                    toReturn= getUser(niz);
                }else{
                    newList.add(getUser(niz));
                }
                line = br.readLine();
            }

            br.close();

            for(User u : newList){
                output.append(u.toString());
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


    public static synchronized User findUser(String username, String password) throws IOException {

        File f = new File("users.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                if((niz[1]).toLowerCase().equals(username.toLowerCase()) && (niz[2]).equals(password)) {
                    return getUser(niz);
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


    public static synchronized User addUsersReservation(Reservation reservation, int userId) throws IOException {

        File f = new File("users.txt");
        User toReturn = null;
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
        ArrayList<User> users = new ArrayList<>();

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int user_id = Integer.parseInt(niz[0]);
                if(user_id != userId){
                    User u = getUser2(niz);
                    users.add(u);
                }else{
                    toReturn = getUser2(niz);
                    toReturn.getBookings().add(reservation);
                    users.add(toReturn);
                }
                line = br.readLine();
            }

            BufferedWriter output = null;
            output = new BufferedWriter(new FileWriter(f));
            output.write("");
            output.flush();

            for(User u : users){
                output.append(u.toString());
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

// METODA KOJU KORISTIMO KADA BRISEMO OBRISANU REZERVACIJU IZ KORISNIKOVE LISTE REZERVACIJA
    public static synchronized void deleteUsersReservation(Reservation reservation, int userId) throws IOException {

        File f = new File("users.txt");
        User toReturn = null;
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
        ArrayList<User> users = new ArrayList<>();

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int user_id = Integer.parseInt(niz[0]);
                if(user_id != userId){
                    User u = getUser2(niz);
                    users.add(u);
                }else{
                    toReturn = getUser2(niz);
                    ArrayList<Reservation> bookings = new ArrayList<>();

                    for(int i=0; i<toReturn.getBookings().size(); i++){
                        if(reservation.getId() != toReturn.getBookings().get(i).getId()){
                            Reservation booking = new Reservation();
                            booking.setId(toReturn.getBookings().get(i).getId());
                            bookings.add(booking);
                        }
                    }
                    toReturn.setBookings(bookings);
                    users.add(toReturn);
                }
                line = br.readLine();
            }

            BufferedWriter output = null;
            output = new BufferedWriter(new FileWriter(f));
            output.write("");
            output.flush();

            for(User u : users){
                output.append(u.toString());
                output.append("\n");
            }
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }

    public static synchronized User findUserByUsername(String username) throws IOException {

        File f = new File("users.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                if(username.equals(niz[1])) {
                    return getUser(niz);
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

    private static User getUser2(String[] niz) throws IOException {

        User user = new User();
        user.setId(Integer.parseInt(niz[0]));
        user.setUsername(niz[1]);
        user.setPassword(niz[2]);
        String[] bookingsArray = niz[3].split(";");
        TypeOfUser typeOfUser = TypeOfUser.valueOf(niz[4]);
        user.setTypeOfUser(typeOfUser);
        ArrayList<Reservation> bookings = new ArrayList<>();

        if(!niz[3].equals("")){
            for(int i=0; i<bookingsArray.length; i++){
                Reservation booking = new Reservation();
                booking.setId(Integer.parseInt(bookingsArray[i]));
                bookings.add(booking);
            }
        }

        user.setBookings(bookings);
        return user;
    }


    private static User getUser(String[] niz) throws IOException {

        User user = new User();
        user.setId(Integer.parseInt(niz[0]));
        user.setUsername(niz[1]);
        user.setPassword(niz[2]);
        String[] bookingsArray = niz[3].split(";");
        TypeOfUser typeOfUser = TypeOfUser.valueOf(niz[4]);
        user.setTypeOfUser(typeOfUser);
        ArrayList<Reservation> bookings = new ArrayList<>();

        if(!niz[3].equals("")){
            for(int i=0; i<bookingsArray.length; i++){
                Reservation booking = ReservationRepository.getUsersReservationById(Integer.parseInt(bookingsArray[i]));
                bookings.add(booking);
            }
        }

        user.setBookings(bookings);
        return user;
    }


}

package mvcrest.city;

import mvcrest.user.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {

    private static String[] from = {"Paris","London","Madrid","Amsterdam","Lisbon","Budapest","Prague","Kraljevo","Nis","Zagreb","Brno","Tokio","Seul","Peking","Moskva"};
//    private static String[] to = {"Belgrade","Sofia","Oslo","Berlin","Bern","Rome","Vienna","Milano","Barselona","Peskara","Torino","Buenos Ajres","Meksiko Siti","Njujork","LA"};

//    static {
//        try {
//            File f = new File("cities.txt");
//            BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getPath()));
//            writer.write("");
//            writer.flush();
//            generateCities();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    private static void generateCities() throws IOException {

        for (int i = 0; i < from.length; i++) {
            City city = new City();
            city.setId();
            String name = from[i];
            city.setName(name);
            insert(city);
        }
    }

    private static void insert(City city) throws IOException {

        BufferedWriter output = null;
        String text = city.toString();
        try {
            File f = new File("cities.txt");
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
    }

    /**
     * Vraca sve avio kompanije u sistemu.
     * Ako je name dato onda ce vratiti listu svih korisnika sa tim imenom.
     * @return
     */
    public static synchronized List<City> getCities() throws IOException {

        ArrayList<City> cities = new ArrayList<>();
        File f = new File("cities.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();

            while (line != null) {
                String[] niz = line.split(",");
                City c = new City();
                c.setId(Integer.parseInt(niz[0]));
                c.setName(niz[1]);
                cities.add(c);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;

    }

    public static synchronized City getCityById(Integer id) throws IOException {
        File f = new File("cities.txt");
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));

        try {
            String line = br.readLine();
            while (line != null) {
                String[] niz = line.split(",");
                int cityId = Integer.parseInt(niz[0]);
                if(cityId == id) {
                    return getCity(niz);
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

    public static City getCity(String[] niz){
        City city = new City();
        city.setId(Integer.parseInt(niz[0]));
        city.setName(niz[1]);
        return city;
    }




}

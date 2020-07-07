package mvcrest.airline;

import java.io.IOException;
import java.util.List;

public class AirlineService {

    public List<Airline> getAirlines() throws  IOException{
        return AirlineRepository.getAirlines();
    }

    public Airline addAirline(Airline airline) throws  IOException{
        return AirlineRepository.addAirline(airline);
    }

    public Airline changeAirline(Airline airline) throws  IOException{
        return AirlineRepository.changeAirline(airline);
    }

    public Airline deleteAirline(Integer id) throws  IOException{
        return AirlineRepository.deleteAirline(id);
    }

 }

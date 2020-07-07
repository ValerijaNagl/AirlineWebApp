package mvcrest.flight;

import mvcrest.user.User;

import java.io.IOException;
import java.util.List;

public class FlightService {

    public List<Flight> getFlights() throws IOException {
        return FlightRepository.getFlights();
    }

    public Flight getFlightById(Integer id) throws IOException {
        return FlightRepository.getFlightById(id);
    }

    public Flight addFlight(Flight flight)  throws IOException {
        return FlightRepository.addFlight(flight);
    }

}

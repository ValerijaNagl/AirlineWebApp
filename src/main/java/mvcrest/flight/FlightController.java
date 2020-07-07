package mvcrest.flight;

import mvcrest.authentication.AuthService;
import mvcrest.user.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController() {
        this.flightService = new FlightService();
    }


    /**
     * Dohvatanje svih letova
     * Putanja je rest/flights
     * <p>
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlights(@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            List<Flight> list = flightService.getFlights();
            if(list != null){
                return Response.ok(list).build();
            }
        }
        return Response.status(404,"Airline is not deleted!").build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Flight getFlightById(@PathParam("id") int id) throws IOException {
        return flightService.getFlightById(id);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Flight addFlight(Flight flight) throws IOException {
        return flightService.addFlight(flight);
    }
}

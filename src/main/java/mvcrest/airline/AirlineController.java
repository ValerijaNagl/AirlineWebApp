package mvcrest.airline;


import mvcrest.authentication.AuthService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(){
        airlineService = new AirlineService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAirlines(@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            List<Airline> a = airlineService.getAirlines();
            if(a != null){
                return Response.ok(a).build();
            }
        }
        return Response.status(404).build();
    }


    @POST
    @Path("/change")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeAirline(Airline airline,@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Airline a = airlineService.changeAirline(airline);
            if (a != null) {
                return Response.ok(a).build();
            }
        }
        return Response.status(404, "Airline is not changed!").build();
    }

    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAirline(@PathParam("id") Integer id,@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Airline a = airlineService.deleteAirline(id);
            if (a != null) {
                return Response.ok(a).build();
            }
        }
        return Response.status(404,"Airline is not deleted!").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAirline(Airline airline,@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Airline a = airlineService.addAirline(airline);
            if (a != null) {
                return Response.ok().build();
            }
        }
        return Response.status(404,"Airline is not added!").build();
    }


}

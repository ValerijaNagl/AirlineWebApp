package mvcrest.reservations;


import mvcrest.authentication.AuthService;
import mvcrest.user.User;
import mvcrest.user.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController() {
        this.reservationService = new ReservationService();
    }

//ne koristim ovaj poziv nikad
    /**
     * Dohvatanje svih rezervacija
     * Putanja je rest/users
     * <p>
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reservation> getReservations(@HeaderParam("Authorization") String auth) throws IOException {
        return reservationService.getReservations();
    }


    //VRACA SE LISTA REZERVACIJA ODREDJENOG USERA
    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersReservations(@PathParam("id") int id, @HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            List<Reservation> list =  reservationService.getUsersReservations(id);
            System.out.println(list.toString());
            if(list != null){
                return Response.ok(list).build();
            }
        }
        return Response.status(404).build();
    }


    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsersReservation(@PathParam("id") int id,@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Reservation r = reservationService.deleteUsersReservation(id);
            if (r != null) {
                return Response.ok().build();
            }
        }
        return Response.status(404).build();
    }


    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeAReservation(Reservation reservation,@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Reservation r = reservationService.makeAReservation(reservation);
            if (r != null) {
                return Response.ok().build();
            }
        }
        return Response.status(404).build();
    }


}

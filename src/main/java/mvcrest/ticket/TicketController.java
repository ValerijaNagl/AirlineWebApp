package mvcrest.ticket;

import mvcrest.authentication.AuthService;
import mvcrest.user.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("/tickets")
public class TicketController {

    private final TicketService ticketService;
    public TicketController(){
        ticketService = new TicketService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTickets(@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            List<Ticket> list = ticketService.getTickets();
            if (list != null) {
                return Response.ok(list).build();
            }
        }
        return Response.status(404,"Error while getting tickets from database!").build();
    }

    @GET
    @Path("/airline/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTicketsByAirlineId(@PathParam("id") int id) throws IOException {
        ArrayList<Ticket> tickets = (ArrayList<Ticket>) ticketService.getTicketsByAirlineId(id);
        if(tickets.size() != 0) {
            return Response.ok(tickets).build();
        }
        return Response.status(404,"Error while getting airline's tickets!").build();
    }

    @GET
    @Path("/delete/airline/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket deleteTicketByAirlinesId(@PathParam("id") Integer id) throws IOException{
        return TicketRepository.deleteTicketByAirlinesId(id);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTicket(Ticket ticket, @HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Ticket t = ticketService.addTicket(ticket);
            if (t != null) {
                return Response.ok(ticket).build();
            }
        }
        return Response.status(404,"Ticket is not added!").build();
    }

    @POST
    @Path("/change")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeTicket(Ticket ticket, @HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Ticket t = ticketService.changeTicket(ticket);
            if (t != null) {
                return Response.ok(ticket).build();
            }
        }
        return Response.status(404,"Ticket is not changed!").build();
    }

//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Ticket getTicketById(@PathParam("id") int id) throws IOException {
//        return ticketService.getTicketById(id);
//    }

    @POST
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTicketById(@PathParam("id") int id, @HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Ticket t = ticketService.deleteTicketById(id);
            if(t != null){
                return Response.ok(t).build();
            }
        }
        return Response.status(404,"Ticket is not deleted!").build();
    }

    @GET
    @Path("/decrement/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response decrementCount(@PathParam("id") int id, @HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            Ticket ticket = ticketService.decrementCount(id);
            if (ticket != null) {
                return Response.ok(ticket).build();
            }
        }
        return Response.status(404,"Error while booking a ticket!").build();
    }

}

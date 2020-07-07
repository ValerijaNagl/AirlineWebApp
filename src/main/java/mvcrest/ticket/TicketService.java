package mvcrest.ticket;

import java.io.IOException;
import java.util.List;

public class TicketService {

    public List<Ticket> getTickets() throws IOException {
        return TicketRepository.getTickets();
    }

    public List<Ticket> getTicketsByAirlineId(Integer id) throws IOException {
        return TicketRepository.getTicketsByAirlineId(id);
    }


    public Ticket addTicket(Ticket ticket) throws  IOException{
        return TicketRepository.addTicket(ticket);
    }

    public Ticket getTicketById(Integer id) throws IOException {
        return TicketRepository.getTicketById(id);
    }

    public Ticket deleteTicketById(Integer id) throws IOException {
        return TicketRepository.deleteTicketById(id);
    }


    public Ticket deleteTicketByAirlinesId(Integer id) throws IOException{
       return TicketRepository.deleteTicketByAirlinesId(id);
    }

    public Ticket decrementCount(Integer id) throws IOException {
        return TicketRepository.decrementCount(id);
    }

    public Ticket changeTicket(Ticket ticket) throws IOException {
        return TicketRepository.changeTicket(ticket);
    }
}

package mvcrest.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Cross-Origin Resource Sharing (CORS) je mehanizam
 * koji koristi dodatne header-e kao sto su ovde navedeni.
 *
 * Tim hederima obavestavamo browser da dopusti web aplikaciji
 * sa jednog domena da targetira ordedjene servere i da zatrazi resurse
 * sa drugog domena. Web aplikacija uputi cross-origin HTTP request kad
 * god uputi poziv ka nekom drugom domenu.
 *
 * Ovaj slucaj moze da nam se desi kada koristimo node da testiramo FE!
 * Node ce da operise na svom domenu i uputice pozive ka BE koji ce se
 * nalaziti na drugom domenu. BE ce odgovoriti sa odgovarajucim hederima.
 *
 * Da bi resili problem ovde koristimo ContainerResponseFilter da bismo presreli
 * svaki HTTP odgovor sa ove web aplikacije. Ovaj HTTP odgovor dopunimo sa
 * ogdgovarajucim hederima i pustamo aplikaciju da tece dalje normalnim tokom.
 */
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}

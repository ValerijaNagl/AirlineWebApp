package mvcrest;

import mvcrest.filters.CorsFilter;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

// Jersey ce 'rest' da prevede u '/rest/*'
@ApplicationPath("rest")
public class RestApp extends ResourceConfig {

    public RestApp() {

        // Ovde se navodi paket u kome smo definisali servlet-mapping
        packages("mvcrest.user");
        packages("mvcrest.ticket");
        packages("mvcrest.reservations");
        packages("mvcrest.city");
        packages("mvcrest.airline");
        packages("mvcrest.flight");
        register(CorsFilter.class);


    }
}

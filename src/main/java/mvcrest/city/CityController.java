package mvcrest.city;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(){
        cityService = new CityService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> getCities() throws IOException {
        return cityService.getCities();
    }


}

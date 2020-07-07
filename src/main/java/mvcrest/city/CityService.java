package mvcrest.city;

import java.io.IOException;
import java.util.List;

public class CityService {

    public List<City> getCities() throws  IOException{
        return CityRepository.getCities();
    }

 }

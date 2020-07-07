package mvcrest.airline;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Airline implements Serializable {

    private Integer id;
    private String name;
    private static AtomicInteger idCount = new AtomicInteger();


    @Override
    public String toString() {
        return id + "," + name;
    }

    public Airline(){

    }

    public Airline(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId() {
        this.id = idCount.getAndIncrement();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

}


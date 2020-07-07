package mvcrest.city;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class City implements Serializable {

    private Integer id;
    private String name;
    private static AtomicInteger idCount = new AtomicInteger();

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(){
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

    @Override
    public String toString() {
        return id + "," + name;
    }
}

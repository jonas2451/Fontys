package testEntities;

import dao.Entity1;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Entity1<String> {

    String id;
    String country;
    String city;
    String street;
    int number;

    public Address(String id, String country, String city, String street, int number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public Address() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNaturalId() {
        return "id";
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                '}';
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dao.Entity1;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

/**
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
@Getter
@Setter
@NoArgsConstructor
public class Address implements Entity1<String> {

    String id;
    String country;
    String city;
    String zip;
    String street;
    String number;

    /**
     *
     * @param id
     * @param country
     * @param city
     * @param zip
     * @param street
     * @param number
     */
    public Address(String id, String country, String city, String zip, String street, String number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.number = number;
    }

    @Override
    public String toString() {
        return this.street + " " + this.number + ", " + this.city + " " + this.zip + ", " + this.country;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getNaturalId() {
        return "id";
    }

}
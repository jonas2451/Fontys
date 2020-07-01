/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heidenliquids.address;

/**
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen
 * {@code 423121@student.fontys.nl}
 */
public class Address {

    private String country = null;     //TODO Country has to be added to GUI
    private String city;
    private String zip;
    private String number;
    private String street;

    /**
     * 
     * @param city 
     * @param zip
     * @param number
     * @param street 
     */
    public Address(String city, String zip, String street, String number) {
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.number = number;
        
    }

    @Override
    public String toString() {
        return "Country: " + this.country + ", City: " + this.city + ", ZIP: " + this.zip + ", Street: " + this.street + ", Number: " + this.number;
    }
    
    //getters and setters
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    
}

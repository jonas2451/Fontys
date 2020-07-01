/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dao.Entity1;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author rajinder
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteOrder implements Entity1<String>{

    private String driverid;
    private String trucklicenceno;
    private String trailerlicenceno;
    private String orderid;
    private String name;
    private boolean ishazardous;
    private boolean isunloading;
    private String country;
    private String street;
    private String number;
    private String zip;
    private String actionid;

   /* public ExecuteOrder(String OrderId, String ProductName, String City, String ZipCode, String Street, String number) {
        this.id = OrderId;
        this.name = ProductName;
        this.zip = ZipCode;
        this.street = Street;
        this.number = number;
        this.country = Country;
    }
    */


    @Override
    public String getId() {
        return this.orderid;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNaturalId() {
        return "orderid";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

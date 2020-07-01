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

import java.time.LocalDate;

/**
 *
 * @author sajee
 */
@Getter
@Setter
@NoArgsConstructor
public class Invoice implements Entity1<String> {
    private String id;
    private LocalDate invoiceDate;
    private boolean isPaid;
    private String orderId; 
    
    public Invoice(String id, LocalDate date, boolean isPaid, String order_id){
        this.id = id;
        this.invoiceDate = date;
        this.isPaid = isPaid;
        this.orderId = order_id;
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getNaturalId() {
        return "id";
    }
    
    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", invoiceDate=" + invoiceDate +
                '}';
    }
    
    
}
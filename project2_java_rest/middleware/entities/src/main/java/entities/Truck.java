/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
public class Truck {
    
    private String licenseNumber;
    private String typeOfTruck;
    private boolean status;
    private double currentWeightLoaded;
    private double maxWeight;
    private double maxTowingWeight;

    /**
     * 
     * @param licenseNumber
     * @param typeOfTruck
     * @param status
     * @param currentWeightLoaded
     * @param maxWeight
     * @param maxTowingWeight 
     */
    public Truck(String licenseNumber, String typeOfTruck, boolean status, double currentWeightLoaded, double maxWeight, double maxTowingWeight) {
        this.licenseNumber = licenseNumber;
        this.typeOfTruck = typeOfTruck;
        this.status = status;
        this.currentWeightLoaded = currentWeightLoaded;
        this.maxWeight = maxWeight;
        this.maxTowingWeight = maxTowingWeight;
    }

    @Override
    public String toString() {
        return "license number: " + this.licenseNumber + ", current weight loaded: " + this.currentWeightLoaded +
                ", maximum towing weight: " + this.maxTowingWeight + ", type of truck: " + this.typeOfTruck + 
                ", maximum weight: " + this.maxWeight + ", status: " + this.status;
    }
    
}

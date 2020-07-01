package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen {@code j.terschlusen@student.fontys.nl}
 */
@Getter
@Setter
@NoArgsConstructor
public class Trailer {
    
    private String licenseNumber;
    private double maxWeight;
    private String trailerType;
    private double currentWeightLoaded;

    public Trailer(String licenseNumber, double maxWeight, String trailerType, double currentWeightLoaded) {
        this.licenseNumber = licenseNumber;
        this.maxWeight = maxWeight;
        this.trailerType = trailerType;
        this.currentWeightLoaded = currentWeightLoaded;
    }

    @Override
    public String toString() {
        return "LicenseNumber: " + this.licenseNumber + ", Maximum Weight: " + this.maxWeight + ", Trailer type: " + this.trailerType + ", Current weight loaded: " + this.currentWeightLoaded;
    }
    
    
}

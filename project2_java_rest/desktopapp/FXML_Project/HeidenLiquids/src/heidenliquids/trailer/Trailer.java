package heidenliquids.trailer;

/**
 *
 * @author Jonas Terschlüsen - 3743918 - TIPB_jonasterschlüsen {@code j.terschlusen@student.fontys.nl}
 */
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

    public double getCurrentWeightLoaded() {
        return currentWeightLoaded;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public String getTrailerType() {
        return trailerType;
    }

    public void setCurrentWeightLoaded(double currentWeightLoaded) {
        this.currentWeightLoaded = currentWeightLoaded;
    }

    @Override
    public String toString() {
        return "LicenseNumber: " + this.licenseNumber + ", Maximum Weight: " + this.maxWeight + ", Trailer type: " + this.trailerType + ", Current weight loaded: " + this.currentWeightLoaded;
    }
    
    
}

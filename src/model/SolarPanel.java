package model;

public class SolarPanel {
    private String id;
    private double efficiency; // 0.0 to 1.0 (e.g., 0.20 for 20%)
    private double currentSunlight; // Input power in Watts
    
    public SolarPanel(String id, double efficiency) {
        this.id = id;
        this.efficiency = efficiency;
        this.currentSunlight = 0.0;
    }

    public void setCurrentSunlight(double sunlight) {
        this.currentSunlight = sunlight;
    }

    public double getConvertedEnergy() {
        return currentSunlight * efficiency;
    }

    public double getReflectedEnergy() {
        return currentSunlight * (1.0 - efficiency);
    }

    public String getId() {
        return id;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public double getCurrentSunlight() {
        return currentSunlight;
    }
}

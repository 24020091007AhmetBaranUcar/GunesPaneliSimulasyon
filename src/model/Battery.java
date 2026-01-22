package model;

public class Battery {
    private double capacity; // Total capacity in kWh (or Wh)
    private double currentCharge; // Current charge in kWh

    public Battery(double capacity) {
        this.capacity = capacity;
        this.currentCharge = 0.0;
    }

    // Charge the battery with an amount of energy
    public void charge(double amount) {
        this.currentCharge += amount;
        if (this.currentCharge > this.capacity) {
            this.currentCharge = this.capacity;
        }
    }

    // Drain energy (custom usage if needed later)
    public void drain(double amount) {
        this.currentCharge -= amount;
        if (this.currentCharge < 0) {
            this.currentCharge = 0;
        }
    }

    public double getPercentage() {
        if (capacity == 0)
            return 0;
        return (currentCharge / capacity) * 100.0;
    }

    public double getCurrentCharge() {
        return currentCharge;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
        // If new capacity is lower than current charge, clamp it?
        // Or just let it overfill visually? Let's clamp for safety.
        if (this.currentCharge > this.capacity) {
            this.currentCharge = this.capacity;
        }
    }
}

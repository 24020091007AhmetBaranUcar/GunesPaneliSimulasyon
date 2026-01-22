package simulation;

import model.Battery;
import model.SolarPanel;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class SunSimulator {
    private List<SolarPanel> panels;
    private Battery battery;
    private MainFrame frame;
    private Timer timer;
    private Random random;

    public SunSimulator(List<SolarPanel> panels, Battery battery, MainFrame frame) {
        this.panels = panels;
        this.battery = battery;
        this.frame = frame;
        this.random = new Random();

        // Timer runs every 1 second (1000ms)
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
    }

    public void start() {
        timer.start();
    }

    private void tick() {
        // Simulate sunlight changes (random fluctuations)
        // Assume base sunlight is around 800-1000 Watts/m2

        for (SolarPanel p : panels) {
            // Random sunlight between 500 and 1000 Watts
            double sunlight = 500 + (random.nextDouble() * 500);
            p.setCurrentSunlight(sunlight);

            // Charge battery with converted energy (scaled down for simulation speed)
            // Let's say 1 second = 1 minute of charging
            double energyProduced = p.getConvertedEnergy(); // Watts
            // Convert to kWh roughly for the battery (very simplified)
            // Energy = Power * Time.
            // Let's just add a fraction to the battery to see it move.
            battery.charge(energyProduced * 0.0001);
        }

        // Refresh UI
        frame.refresh();
        frame.fullBatteryMessage();
    }
}

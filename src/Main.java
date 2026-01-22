import model.Battery;
import model.SolarPanel;
import simulation.SunSimulator;
import view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Run UI in Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // 1. Create Models
            List<SolarPanel> panels = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                // Different efficiencies for variety
                // Panel 1-5, Efficiency ranges from 15% to 22%
                double eff = 0.15 + (Math.random() * 0.10);
                panels.add(new SolarPanel(String.valueOf(i), eff));
            }

            Battery battery = new Battery(100.0); // 100 kWh capacity

            // 2. Create View
            MainFrame frame = new MainFrame(panels, battery);
            frame.setVisible(true);

            // 3. Start Simulation
            SunSimulator sim = new SunSimulator(panels, battery, frame);
            sim.start();

            System.out.println("Solar Monitoring System Started...");
        });
    }
}

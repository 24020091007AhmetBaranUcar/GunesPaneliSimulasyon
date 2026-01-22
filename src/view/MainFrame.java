package view;

import model.SolarPanel;
import model.Battery;

import javax.swing.*;
import java.util.List;

public class MainFrame extends JFrame {

    private DashboardPanel dashboard;

    public MainFrame(List<SolarPanel> panels, Battery battery) {
        setTitle("Solar Panel Monitoring System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen

        dashboard = new DashboardPanel(panels, battery);
        add(dashboard);
    }

    public void refresh() {
        dashboard.updateView();
    }

    public void fullBatteryMessage() {
        dashboard.fullBatteryMessage();
    }
}

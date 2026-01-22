package view;

import model.Battery;
import model.SolarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {
    private List<SolarPanel> panels;
    private Battery battery;

    // UI Components
    private JLabel[] panelLabels;
    private JProgressBar batteryBar;
    private JLabel batteryLabel;

    public DashboardPanel(List<SolarPanel> panels, Battery battery) {
        this.panels = panels;
        this.battery = battery;

        setLayout(new BorderLayout());
        setBackground(new Color(40, 44, 52)); // Dark theme background

        // Header
        JLabel header = new JLabel("Solar Panel Monitoring System", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Center: Solar Panels Grid
        JPanel panelsGrid = new JPanel(new GridLayout(1, 5, 10, 10)); // 1 row, 5 cols
        panelsGrid.setBackground(new Color(40, 44, 52));
        panelsGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelLabels = new JLabel[panels.size()];

        for (int i = 0; i < panels.size(); i++) {
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBackground(new Color(60, 63, 65));
            p.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));

            JLabel title = new JLabel("Panel " + panels.get(i).getId());
            title.setForeground(Color.CYAN);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel stats = new JLabel("Waiting...");
            stats.setForeground(Color.WHITE);
            stats.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelLabels[i] = stats;

            p.add(Box.createVerticalStrut(10));
            p.add(title);
            p.add(Box.createVerticalStrut(10));
            p.add(stats);
            p.add(Box.createVerticalStrut(10));

            panelsGrid.add(p);
        }
        add(panelsGrid, BorderLayout.CENTER);

        // Bottom: Battery Status & Controls
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(40, 44, 52));

        // Battery Display
        JPanel batteryDisplay = new JPanel(new FlowLayout(FlowLayout.LEFT));
        batteryDisplay.setOpaque(false);

        batteryLabel = new JLabel("Battery: 0%");
        batteryLabel.setForeground(Color.GREEN);
        batteryLabel.setFont(new Font("Arial", Font.BOLD, 18));

        batteryBar = new JProgressBar(0, 100);
        batteryBar.setPreferredSize(new Dimension(200, 30));
        batteryBar.setValue(0);
        batteryBar.setStringPainted(true);

        batteryDisplay.add(batteryLabel);
        batteryDisplay.add(batteryBar);

        // Controls & Info
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsPanel.setOpaque(false);

        JLabel noteLabel = new JLabel("Note: 1 sim sec = 1 min charging");
        noteLabel.setForeground(Color.LIGHT_GRAY);
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        JLabel capLabel = new JLabel("Capacity (kWh):");
        capLabel.setForeground(Color.WHITE);

        JTextField capInput = new JTextField("100", 5);
        JButton updateBtn = new JButton("Set");

        updateBtn.addActionListener(e -> {
            try {
                double newCap = Double.parseDouble(capInput.getText());
                if (newCap > 0) {
                    battery.setCapacity(newCap);
                    JOptionPane.showMessageDialog(this, "Battery capacity set to " + newCap + " kWh");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        controlsPanel.add(noteLabel);
        controlsPanel.add(Box.createHorizontalStrut(20));
        controlsPanel.add(capLabel);
        controlsPanel.add(capInput);
        controlsPanel.add(updateBtn);

        bottomPanel.add(batteryDisplay, BorderLayout.WEST);
        bottomPanel.add(controlsPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateView() {
        // Update Panel Stats
        for (int i = 0; i < panels.size(); i++) {
            SolarPanel p = panels.get(i);
            String text = String.format("<html><center>Input: %.2f W<br>Conv: %.2f W<br>Refl: %.2f W</center></html>",
                    p.getCurrentSunlight(),
                    p.getConvertedEnergy(),
                    p.getReflectedEnergy());
            panelLabels[i].setText(text);
        }

        // Update Battery Stats
        int batPct = (int) battery.getPercentage();
        batteryBar.setValue(batPct);
        batteryLabel.setText(String.format("Battery: %.1f%%", battery.getPercentage()));
    }

    private boolean messageShown = false;

    public void fullBatteryMessage() {
        if (battery.getPercentage() >= 100) {
            if (!messageShown) {
                JOptionPane.showMessageDialog(this, "Battery is full!", "Warning", JOptionPane.WARNING_MESSAGE);
                messageShown = true;
            }
        } else {
            messageShown = false;
        }
    }
}

package SubManagements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpaceManageGUI {
    private JFrame frame;
    private JTextField shuttleNameField, fuelCapacityField, astronautCapacityField;
    private JTextArea outputArea;

    public SpaceManageGUI() {
        frame = new JFrame("Spaceship Management");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Shuttle Name:");
        shuttleNameField = new JTextField();

        JLabel fuelLabel = new JLabel("Fuel Capacity (lbs):");
        fuelCapacityField = new JTextField();

        JLabel astronautLabel = new JLabel("Astronaut Capacity:");
        astronautCapacityField = new JTextField();

        JButton submitButton = new JButton("Launch");
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchShip();
            }
        });

        frame.add(nameLabel);
        frame.add(shuttleNameField);
        frame.add(fuelLabel);
        frame.add(fuelCapacityField);
        frame.add(astronautLabel);
        frame.add(astronautCapacityField);
        frame.add(submitButton);
        frame.add(new JScrollPane(outputArea));

        frame.setVisible(true);
    }

    private void launchShip() {
        String shuttleName = shuttleNameField.getText();
        String fuelText = fuelCapacityField.getText();
        String astronautText = astronautCapacityField.getText();

        try {
            double fuelCapacity = Double.parseDouble(fuelText);
            int astronautCapacity = Integer.parseInt(astronautText);

            if (fuelCapacity > 10000) {
                outputArea.setText("The ship, '" + shuttleName + "', will be attempting to launch to the moon.\n"
                        + "There are " + astronautCapacity + " member(s) with " + fuelCapacity + " pounds of fuel to work with.");
                proceedToNextFile();
            } else {
                outputArea.setText(shuttleName + " requires more fuel to leave the planet. Please try again.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter valid numbers for fuel and astronaut capacity.");
        }
    }

    private void proceedToNextFile() {
        try {
            outputArea.append("\nProceeding to Launch sequence...");
            // Launch.launch(); // Uncomment if you have a Launch class
        } catch (Exception e) {
            outputArea.append("\nError starting Launch sequence: " + e.getMessage());
        }
    }

    public static void ship() {
        SwingUtilities.invokeLater(SpaceManageGUI::new);
    }
}

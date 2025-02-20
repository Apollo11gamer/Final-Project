package SubManagements;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SpaceManageGUI {
    private JFrame frame;
    private JTextField shuttleNameField, fuelCapacityField, astronautCapacityField;
    private JTextArea astronautNamesArea;
    private JButton launchButton;
    
    public SpaceManageGUI() {
        frame = new JFrame("Spaceship Management");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        // Shuttle Name
        JPanel shuttlePanel = new JPanel();
        shuttlePanel.add(new JLabel("Shuttle Name:"));
        shuttleNameField = new JTextField(15);
        shuttlePanel.add(shuttleNameField);
        frame.add(shuttlePanel);

        // Fuel Capacity
        JPanel fuelPanel = new JPanel();
        fuelPanel.add(new JLabel("Fuel Capacity (lbs):"));
        fuelCapacityField = new JTextField(10);
        fuelPanel.add(fuelCapacityField);
        frame.add(fuelPanel);

        // Astronaut Capacity
        JPanel astronautPanel = new JPanel();
        astronautPanel.add(new JLabel("Astronaut Capacity:"));
        astronautCapacityField = new JTextField(5);
        astronautPanel.add(astronautCapacityField);
        frame.add(astronautPanel);

        // Astronaut Names
        JPanel namesPanel = new JPanel();
        namesPanel.setLayout(new BorderLayout());
        namesPanel.add(new JLabel("Astronaut Names (comma-separated):"), BorderLayout.NORTH);
        astronautNamesArea = new JTextArea(3, 20);
        namesPanel.add(new JScrollPane(astronautNamesArea), BorderLayout.CENTER);
        frame.add(namesPanel);

        // Launch Button
        launchButton = new JButton("Launch Spaceship");
        launchButton.addActionListener(new LaunchButtonListener());
        frame.add(launchButton);

        frame.setVisible(true);
    }

    private class LaunchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String shuttleName = shuttleNameField.getText();
            double fuelCapacity;
            int astronautCapacity;
            
            try {
                fuelCapacity = Double.parseDouble(fuelCapacityField.getText());
                astronautCapacity = Integer.parseInt(astronautCapacityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numerical values for fuel and astronaut capacity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] astronautNames = astronautNamesArea.getText().split(",");
            
            if (astronautNames.length != astronautCapacity) {
                JOptionPane.showMessageDialog(frame, "Please enter exactly " + astronautCapacity + " astronaut names.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (fuelCapacity > 10000) {
                StringBuilder message = new StringBuilder();
                message.append("The ship '").append(shuttleName).append("' is launching to the moon!\n");
                message.append("Fuel: ").append(fuelCapacity).append(" lbs\n");
                message.append("Crew Members:\n");
                for (String name : astronautNames) {
                    message.append("- ").append(name.trim()).append("\n");
                }

                JOptionPane.showMessageDialog(frame, message.toString(), "Launch Successful", JOptionPane.INFORMATION_MESSAGE);
                
                proceedToLaunch();
            } else {
                JOptionPane.showMessageDialog(frame, shuttleName + " requires more fuel to leave the planet.", "Launch Failure", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void proceedToLaunch() {
        try {
            JOptionPane.showMessageDialog(frame, "Proceeding to Launch sequence...", "Launch Sequence", JOptionPane.INFORMATION_MESSAGE);
            LaunchGUI.launch(); // Assuming you have a Launch class
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error starting Launch sequence: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ship() {
        SwingUtilities.invokeLater(SpaceManageGUI::new);
    }
}

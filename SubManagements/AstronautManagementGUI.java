package SubManagements;

import javax.swing.*;

public class AstronautManagementGUI {
    public static void Space() {
        // Create a JFrame to serve as the main window
        JFrame frame = new JFrame("Astronaut Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Create a panel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        // Set the frame location and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        
        // Label for astronaut name
        JLabel nameLabel = new JLabel("Astronaut Name:");
        nameLabel.setBounds(10, 20, 120, 25);
        panel.add(nameLabel);
        
        // Text field for entering name
        JTextField nameText = new JTextField(20);
        nameText.setBounds(140, 20, 200, 25);
        panel.add(nameText);
        
        // Label for astronaut weight
        JLabel weightLabel = new JLabel("Weight (lbs):");
        weightLabel.setBounds(10, 60, 120, 25);
        panel.add(weightLabel);
        
        // Text field for entering weight
        JTextField weightText = new JTextField(20);
        weightText.setBounds(140, 60, 200, 25);
        panel.add(weightText);
        
        // Button to submit the details
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(140, 100, 100, 25);
        panel.add(submitButton);
        
        // Action listener to process input
        submitButton.addActionListener(o_ -> {
            String name = nameText.getText().trim();
            String weightStr = weightText.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double weight;
            try {
                weight = Double.parseDouble(weightStr);
                if (weight <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid positive number for weight.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Display the entered details
            JOptionPane.showMessageDialog(panel, "Astronaut Details:\nName: " + name + "\nWeight: " + weight + " lbs");
            
            // Close current frame and proceed to LaunchGUI
            SwingUtilities.windowForComponent(panel).dispose(); // Close the current frame
            // Ensure LaunchGUI has a static launch method
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AstronautManagementGUI::Space);
    }
}

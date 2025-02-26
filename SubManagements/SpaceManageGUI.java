package SubManagements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpaceManageGUI {
    
    private JFrame frame;
    private JTextField astronautCountField;
    private JButton nextButton;

    public SpaceManageGUI() {
        frame = new JFrame("BAISD Astronaut Control Panel");
        frame.setSize(350, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(300, 100)); // Set minimum size

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Number of Astronauts:"));
        astronautCountField = new JTextField(5);
        inputPanel.add(astronautCountField);
        frame.add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int count = Integer.parseInt(astronautCountField.getText());
                    if (count > 0) {
                        new AstronautDetailsGUI(count);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(nextButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Center and display
        frame.setLocationRelativeTo(null);
        astronautCountField.requestFocusInWindow(); // Focus on the input field
        frame.setVisible(true);
    }

    public static void ship() {
        SwingUtilities.invokeLater(SpaceManageGUI::new);
    }

    public static void main(String[] args) {
        ship(); // Start the GUI
    }
}

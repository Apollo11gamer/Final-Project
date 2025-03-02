package SubManagements;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SpaceManageGUI {

    private JFrame frame;
    private JTextField astronautCountField;
    private JButton nextButton;

    public SpaceManageGUI() {
        frame = new JFrame("BAISD Astronaut Control Panel");
        frame.setSize(400, 200);  // Increased size for a more spacious design
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));  // Added margin between components
        frame.setMinimumSize(new Dimension(350, 150));
        frame.getContentPane().setBackground(new Color(18, 18, 18)); // Space-like dark background

        // Panel for input section with custom styling
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(18, 18, 18));
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));  // Centering and adding spacing

        JLabel astronautLabel = new JLabel("Number of Astronauts:");
        astronautLabel.setForeground(new Color(174, 214, 255));  // Light blue text for labels
        astronautLabel.setFont(new Font("Orbitron", Font.PLAIN, 16));  // Futuristic font

        astronautCountField = new JTextField(5);
        astronautCountField.setFont(new Font("Orbitron", Font.PLAIN, 14));  // Consistent font
        astronautCountField.setBackground(new Color(50, 50, 50));  // Dark gray background
        astronautCountField.setForeground(new Color(174, 214, 255));  // Cyan text
        astronautCountField.setBorder(BorderFactory.createLineBorder(new Color(174, 214, 255), 2));  // Cyan border

        inputPanel.add(astronautLabel);
        inputPanel.add(astronautCountField);
        frame.add(inputPanel, BorderLayout.CENTER);

        // Panel for button with sleek styling
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(18, 18, 18));

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Orbitron", Font.BOLD, 16));
        nextButton.setBackground(new Color(50, 50, 50));  // Dark gray background
        nextButton.setForeground(new Color(174, 214, 255));  // Cyan text
        nextButton.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));  // Dark border
        nextButton.setFocusPainted(false);
        nextButton.setEnabled(false);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int count = Integer.parseInt(astronautCountField.getText().trim());
                    if (count > 0) {
                        new AstronautDetailsGUI(count);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Enter a valid positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(209, 167, 241));  // Light purple hover effect
                nextButton.setBorder(BorderFactory.createLineBorder(new Color(209, 167, 241), 2));  // Light purple border
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(50, 50, 50));  // Reset to dark gray
                nextButton.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));  // Reset to dark border
            }
        });

        buttonPanel.add(nextButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Enable or disable next button based on input
        astronautCountField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableNextButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableNextButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableNextButton();
            }

            private void enableNextButton() {
                try {
                    int count = Integer.parseInt(astronautCountField.getText().trim());
                    nextButton.setEnabled(count > 0);
                } catch (NumberFormatException e) {
                    nextButton.setEnabled(false);
                }
            }
        });

        frame.setLocationRelativeTo(null);  // Center the window
        astronautCountField.requestFocusInWindow();  // Focus on input field when window opens
        frame.setVisible(true);
    }

    public static void ship() {
        SwingUtilities.invokeLater(SpaceManageGUI::new);
    }
}

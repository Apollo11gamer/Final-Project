package SubManagements;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import javax.swing.*;

public class DOBDetectorGUI {

    public static void DOB() {
        SwingUtilities.invokeLater(DOBDetectorGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the frame
        JFrame frame = new JFrame("DOB Detector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(5, 1, 10, 10));  // Added spacing between components
        frame.setResizable(false);

        // Set the background color to dark
        frame.getContentPane().setBackground(new Color(0, 0, 0));  // Black background

        // Create labels and text fields with appropriate font and styling
        JLabel promptLabel = new JLabel("Enter your Date of Birth:", SwingConstants.CENTER);
        promptLabel.setForeground(Color.CYAN);  // Set the label text color to cyan
        Font labelFont = new Font("Orbitron", Font.BOLD, 16);
        promptLabel.setFont(labelFont);

        JTextField dobField = new JTextField();
        dobField.setFont(new Font("Orbitron", Font.PLAIN, 14));
        dobField.setBackground(new Color(50, 50, 50));  // Dark gray background for text field
        dobField.setForeground(Color.CYAN);  // Cyan text color

        // Create buttons with custom styles
        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);

        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setForeground(Color.CYAN);  // Cyan text for results
        resultLabel.setFont(new Font("Orbitron", Font.PLAIN, 14));

        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputDOB = dobField.getText().trim();
                LocalDate birthDate = parseDate(inputDOB);
                if (birthDate != null) {
                    int age = calculateAge(birthDate);
                    resultLabel.setText("Valid DOB: " + birthDate + ", Age: " + age);
                    JOptionPane.showMessageDialog(frame, "Proceeding to Email Verifier...");
                    // Open the Email Verifier after DOB is valid
                    EmailVerifierGUI.email();  // Assuming this method exists and is properly defined
                    frame.setVisible(false);  // Close DOB window after verification
                } else {
                    resultLabel.setText("Invalid Date of Birth. Try again.");
                }
            }
        });

        // Add components to the frame
        frame.add(promptLabel);
        frame.add(dobField);
        frame.add(submitButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }

    private static void styleButton(JButton button) {
        button.setFont(new Font("Orbitron", Font.BOLD, 14));
        button.setBackground(new Color(50, 50, 50));  // Dark gray background
        button.setForeground(Color.CYAN);  // Cyan text for button
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));  // Dark border

        // Add a hover effect (pulse-like behavior)
        button.setRolloverEnabled(true);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);

        // Pulse effect via background color transition
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(209, 167, 241));  // Light purple
                button.setBorder(BorderFactory.createLineBorder(new Color(209, 167, 241), 2));  // Light purple border
                button.setFont(button.getFont().deriveFont(16f));  // Slightly increase font size
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));  // Dark gray background
                button.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));  // Dark border
                button.setFont(button.getFont().deriveFont(14f));  // Reset font size
            }
        });
    }

    private static LocalDate parseDate(String dob) {
        dob = dob.replaceAll("[./]", "-");
        if (!Pattern.matches("\\d{1,4}[-]\\d{1,2}[-]\\d{1,4}", dob)) {
            return null;
        }
        String[] dateFormats = {"yyyy-MM-dd", "MM-dd-yyyy", "dd-MM-yyyy", "MM/dd/yyyy", "dd/MM/yyyy", "yyyy/MM/dd"};
        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(dob, formatter);
            } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    private static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

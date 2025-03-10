package SubManagements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class PhoneNumberVerifierGUI {
    private static final String PHONE_REGEX = "^(\\+\\d{1,3}\\s?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private static final int MAX_ATTEMPTS = 3;
    private int attempts = 0;

    private JFrame frame;
    private JTextField phoneInputField;
    private JLabel resultLabel;
    private JButton verifyButton;

    public PhoneNumberVerifierGUI() {
        frame = new JFrame("Phone Number Verifier");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1, 10, 10));  // Added spacing between components
        frame.setResizable(false);

        // Set the background color to dark
        frame.getContentPane().setBackground(new Color(0, 0, 0));  // Black background

        // Create the label with cyan text and a spacey font
        resultLabel = new JLabel("Enter a phone number to verify:", SwingConstants.CENTER);
        resultLabel.setForeground(Color.CYAN);
        Font labelFont = new Font("Orbitron", Font.BOLD, 16);
        resultLabel.setFont(labelFont);

        // Create the text field with dark background and cyan text
        phoneInputField = new JTextField();
        phoneInputField.setFont(new Font("Orbitron", Font.PLAIN, 14));
        phoneInputField.setBackground(new Color(50, 50, 50));  // Dark gray background for text field
        phoneInputField.setForeground(Color.CYAN);  // Cyan text color

        // Create the verify button with custom styles
        verifyButton = new JButton("Verify");
        styleButton(verifyButton);

        // Add the action listener to the verify button
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyPhoneNumber();
            }
        });

        // Add components to the frame
        frame.add(resultLabel);
        frame.add(phoneInputField);
        frame.add(verifyButton);

        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
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

    private void verifyPhoneNumber() {
        String phoneNumber = phoneInputField.getText().trim();

        if (isValidPhoneNumber(phoneNumber)) {
            resultLabel.setText("Valid phone number!");
            proceedToNextFile();
        } else {
            attempts++;
            resultLabel.setText("Invalid phone number! Attempts: " + attempts);
            if (attempts >= MAX_ATTEMPTS) {
                logInvalidAttempt(phoneNumber);
                JOptionPane.showMessageDialog(frame, "Too many invalid attempts. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private void logInvalidAttempt(String phoneNumber) {
        try (FileWriter writer = new FileWriter("invalid_attempts.log", true)) {
            writer.write("Invalid phone number attempt: " + phoneNumber + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing to log file.", "Logging Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void proceedToNextFile() {
        JOptionPane.showMessageDialog(frame, "Proceeding to Spaceship Management.", "Success", JOptionPane.INFORMATION_MESSAGE);
        SpaceManageGUI.ship();  // Assuming this method exists in your code
    }

    public static void phone() {
        SwingUtilities.invokeLater(PhoneNumberVerifierGUI::new);
    }
}

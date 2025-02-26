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
        frame.setLayout(new GridLayout(3, 1));

        phoneInputField = new JTextField();
        resultLabel = new JLabel("Enter a phone number to verify:", SwingConstants.CENTER);
        verifyButton = new JButton("Verify");

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyPhoneNumber();
            }
        });

        frame.add(resultLabel);
        frame.add(phoneInputField);
        frame.add(verifyButton);

        frame.setVisible(true);
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
        SpaceManageGUI.ship();
    }

    public static void phone() {
        SwingUtilities.invokeLater(PhoneNumberVerifierGUI::new);
    }
}
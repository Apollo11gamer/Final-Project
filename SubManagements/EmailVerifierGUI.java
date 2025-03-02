package SubManagements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class EmailVerifierGUI {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private JFrame frame;
    private JTextField emailInputField;
    private JLabel resultLabel;
    private JButton verifyButton;

    public EmailVerifierGUI() {
        frame = new JFrame("Email Verifier");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1, 10, 10));
        frame.setResizable(false);

        // Set the background color to dark
        frame.getContentPane().setBackground(new Color(0, 0, 0));

        // Create the label with cyan text and a spacey font
        resultLabel = new JLabel("Enter your email to verify:", SwingConstants.CENTER);
        resultLabel.setForeground(Color.CYAN);
        Font labelFont = new Font("Orbitron", Font.BOLD, 16);
        resultLabel.setFont(labelFont);

        // Create the text field with dark background and cyan text
        emailInputField = new JTextField();
        emailInputField.setFont(new Font("Orbitron", Font.PLAIN, 14));
        emailInputField.setBackground(new Color(50, 50, 50));
        emailInputField.setForeground(Color.CYAN);

        // Create the verify button with custom styles
        verifyButton = new JButton("Verify");
        styleButton(verifyButton);

        // Add the action listener to the verify button
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyEmail();
            }
        });

        // Add components to the frame
        frame.add(resultLabel);
        frame.add(emailInputField);
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

    private void verifyEmail() {
        String email = emailInputField.getText().trim();

        if (isValidEmail(email)) {
            resultLabel.setText("Valid email!");
            JOptionPane.showMessageDialog(frame, "Proceeding to Phone number verifier...");
            // Proceed to the next part of your application (for example, moving to Spaceship Management)
            PhoneNumberVerifierGUI.phone();  // Assuming this method exists in your code
        } else {
            resultLabel.setText("Invalid email format!");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static void email() {
        SwingUtilities.invokeLater(EmailVerifierGUI::new);
    }
}

package password;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Random;
import SubManagements.EmailVerifier;
import password.PasswordStorage;
import password.PasswordStorage.User;

public class PasswordGUI {
    private static final String LOG_FILE = "login_attempts_log.txt";
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea outputArea;
    
    public PasswordGUI() {
        frame = new JFrame("Password Manager");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        frame.add(userLabel);
        frame.add(usernameField);
        frame.add(passLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(registerButton);
        frame.add(new JScrollPane(outputArea));

        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (PasswordStorage.userExists(username)) {
            try {
                String storedPassword = PasswordStorage.getPassword(username);
                if (storedPassword.equals(password)) {
                    outputArea.setText("Welcome " + username + "!");
                    verifyEmail();
                } else {
                    outputArea.setText("Invalid password!");
                    logFailedAttempt(username);
                }
            } catch (Exception e) {
                outputArea.setText("Error during login: " + e.getMessage());
            }
        } else {
            outputArea.setText("Username not found!");
            logFailedAttempt(username);
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String serialNumber = generateSerialNumber();

        try {
            PasswordStorage.savePassword(username, password, serialNumber);
            outputArea.setText("Registration successful!");
        } catch (Exception e) {
            outputArea.setText("Error registering: " + e.getMessage());
        }
    }

    private void verifyEmail() {
        String email = JOptionPane.showInputDialog(frame, "Enter your email for verification:");
        if (email != null && !email.isEmpty()) {
            EmailVerifier.Email();
        } else {
            outputArea.setText("Email verification failed. Please try again.");
        }
    }

    private void logFailedAttempt(String username) {
        try {
            File logFile = new File(LOG_FILE);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write("Failed login attempt for username: " + username + " at " + timestamp);
                writer.newLine();
            }
        } catch (IOException e) {
            outputArea.setText("Error writing to log file: " + e.getMessage());
        }
    }

    private String generateSerialNumber() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return timestamp + "-" + randomNumber;
    }

    public static void pass() {
        new PasswordGUI();
    }
}

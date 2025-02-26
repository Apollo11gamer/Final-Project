import SubManagements.DOBDetectorGUI;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class EntryPointGUI {
    private static final String USER_DATA_FILE = "user_data.txt";
    private JFrame frame;
    private JButton createUserButton, loginButton, adminButton, exitButton;
    
    public EntryPointGUI() {
        frame = new JFrame("Password Manager");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));
        
        createUserButton = new JButton("Create User");
        loginButton = new JButton("Login");
        adminButton = new JButton("Admin");
        exitButton = new JButton("Exit");
        
        createUserButton.addActionListener(e -> createUser());
        loginButton.addActionListener(e -> loginUser());
        adminButton.addActionListener(e -> accessAdmin());
        exitButton.addActionListener(e -> System.exit(0));
        
        frame.add(createUserButton);
        frame.add(loginButton);
        frame.add(adminButton);
        frame.add(exitButton);
        
        checkExistingUser();
        
        frame.setVisible(true);
    }
    
    private void checkExistingUser() {
        File file = new File(USER_DATA_FILE);
        if (file.exists()) {
            createUserButton.setVisible(false);
        }
    }
    
    private void createUser() {
        String username = JOptionPane.showInputDialog(frame, "Enter username:");
        String password = JOptionPane.showInputDialog(frame, "Enter password:");
        if (username != null && password != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
                writer.write(username + "," + password);
                JOptionPane.showMessageDialog(frame, "User created successfully!");
                createUserButton.setVisible(false);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving user data.");
            }
        }
    }
    
    private void loginUser() {
    String username = JOptionPane.showInputDialog(frame, "Enter username:");
    String password = JOptionPane.showInputDialog(frame, "Enter password:");
    
    if (validateUser(username, password)) {
        JOptionPane.showMessageDialog(frame, "Login successful!");

        // Open DOBDetectorGUI
        SwingUtilities.invokeLater(() -> {
            DOBDetectorGUI dobDetector = new DOBDetectorGUI(); // Ensure this class exists
            DOBDetectorGUI.DOB();
        });

        // Optionally hide or close the current frame
        frame.setVisible(false);
    } else {
        JOptionPane.showMessageDialog(frame, "Invalid credentials.");
    }
}

    
    private void accessAdmin() {
        String password = JOptionPane.showInputDialog(frame, "Enter admin password:");
        if ("Kruskie25!".equals(password)) {
            JOptionPane.showMessageDialog(frame, "Admin access granted.");
            AdminGUI.Admin();
        } else {
            JOptionPane.showMessageDialog(frame, "Access denied.");
        }
    }
    
    private boolean validateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                return parts[0].equals(username) && parts[1].equals(password);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading user data.");
        }
        return false;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EntryPointGUI::new);
    }
}
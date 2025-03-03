import SubManagements.DOBDetectorGUI;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class EntryPointGUI {
    private static final String USER_DATA_FILE = "user_data.txt";
    private final JFrame frame;
    private final JButton createUserButton, loginButton, adminButton, exitButton;

    public EntryPointGUI() {
        frame = new JFrame("Password Manager");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1, 10, 10));  // Added spacing between buttons
        frame.setResizable(true);

        // Set a futuristic font with a spacey feel
        Font buttonFont = new Font("Orbitron", Font.BOLD, 14);

        // Initialize buttons with cosmic styles and dark gray backgrounds
        createUserButton = new JButton("Create User");
        styleButton(createUserButton, buttonFont);

        loginButton = new JButton("Login");
        styleButton(loginButton, buttonFont);

        adminButton = new JButton("Admin");
        styleButton(adminButton, buttonFont);

        exitButton = new JButton("Exit");
        styleButton(exitButton, buttonFont);
        exitButton.setForeground(Color.RED);  // Red for alerting exit

        // Adding action listeners for each button
        createUserButton.addActionListener(_ -> createUser());
        loginButton.addActionListener(_ -> loginUser());
        adminButton.addActionListener(_ -> accessAdmin());
        exitButton.addActionListener(_ -> System.exit(0));

        // Add the buttons to the frame
        frame.add(createUserButton);
        frame.add(loginButton);
        frame.add(adminButton);
        frame.add(exitButton);

        checkExistingUser();

        // Set a "spacey" background gradient with a dark starry sky feel
        frame.getContentPane().setBackground(new Color(0, 0, 0));  // Black background
        frame.setVisible(true);
    }

    private void styleButton(JButton button, Font font) {
        button.setFont(font);
        button.setBackground(new Color(50, 50, 50));  // Dark gray background
        button.setForeground(Color.CYAN);  // Light blue for a futuristic feel
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));  // Dark border

        // Add a hover effect (pulse-like behavior)
        button.setRolloverEnabled(true);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);

        // Pulse effect via background color transition
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(209, 167, 241));  // Light purple (#D1A7F1)
                button.setBorder(BorderFactory.createLineBorder(new Color(209, 167, 241), 2));  // Light purple border
                button.setFont(button.getFont().deriveFont(16f));  // Increase font size slightly for effect
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));  // Dark gray background
                button.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));  // Dark border
                button.setFont(button.getFont().deriveFont(14f));  // Reset font size
            }
        });
    }

    private void checkExistingUser() {
        File file = new File(USER_DATA_FILE);
        if (file.exists() && file.length() > 0) { // Check if file exists and has content
            createUserButton.setVisible(false);  // Hide 'Create User' if user already exists
        }
    }
    

    private void createUser() {
        // Create user dialog
        String username = JOptionPane.showInputDialog(frame, "Enter username:");
        if (username == null) {
            // If the user closes the dialog or cancels it, return to the main menu
            return;
        }
        
        String password = JOptionPane.showInputDialog(frame, "Enter password:");
        if (password == null) {
            // If the user closes the dialog or cancels it, return to the main menu
            return;
        }

        // Saving user credentials
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            writer.write(username + "," + password);
            JOptionPane.showMessageDialog(frame, "User created successfully!");
            createUserButton.setVisible(false);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving user data.");
        }
    }

    private void loginUser() {
        String username = JOptionPane.showInputDialog(frame, "Enter username:");
        if (username == null) {
            return; // Cancel or close behavior
        }

        String password = JOptionPane.showInputDialog(frame, "Enter password:");
        if (password == null) {
            return; // Cancel or close behavior
        }

        if (validateUser(username, password)) {
            JOptionPane.showMessageDialog(frame, "Login successful!");

            // Open DOBDetectorGUI
            SwingUtilities.invokeLater(() -> {
                DOBDetectorGUI.DOB(); // Ensure this class exists
            });

            // Optionally hide or close the current frame
            frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid credentials.");
        }
    }

    private void accessAdmin() {
        String password = JOptionPane.showInputDialog(frame, "Enter admin password:");
        if (password == null) {
            return; // Cancel or close behavior
        }

        // Check if the password matches "Kruskie25!"
        if ("Kruskie25!".equals(password)) {
            JOptionPane.showMessageDialog(frame, "Admin access granted.");
            AdminGUI.Admin();  // Ensure AdminGUI class exists and is properly implemented
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
        // Ensuring the "Orbitron" font is available for a futuristic look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        SwingUtilities.invokeLater(EntryPointGUI::new);
    }
}

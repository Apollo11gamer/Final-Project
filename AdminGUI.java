import java.awt.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class AdminGUI {
    private static final String ADMIN_USERNAME = "Admin";
    private static String ADMIN_PASSWORD_HASH;
    private static final String ASTRONAUTS_FILE = "astronauts.txt";

    private final JFrame frame;
    private final JTextArea displayArea;
    private final JTextField nameField;
    private final Map<String, String> astronautDatabase = new HashMap<>();

    public AdminGUI() {
        loadAdminCredentials();
        if (!authenticateAdmin()) {
            JOptionPane.showMessageDialog(null, "Access Denied!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        loadAstronautsFromFile();

        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
        frame.setLayout(new GridLayout(7, 1, 10, 10));

        Font buttonFont = new Font("Orbitron", Font.BOLD, 14);

        // Entry Box - More Recognizable
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.BOLD, 16));
        nameField.setBorder(BorderFactory.createTitledBorder("Enter Astronaut Name"));

        // Display Box - Shows Astronaut List
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Astronauts List"));

        JButton addButton = createStyledButton("Add Astronaut", buttonFont);
        JButton removeButton = createStyledButton("Remove Astronaut", buttonFont);
        JButton editButton = createStyledButton("Edit Astronaut", buttonFont);
        JButton changePasswordButton = createStyledButton("Change Password", buttonFont);
        JButton exitButton = createStyledButton("Exit", buttonFont);
        exitButton.setForeground(Color.RED);

        addButton.addActionListener(e -> addAstronaut());
        removeButton.addActionListener(e -> removeAstronaut());
        editButton.addActionListener(e -> editAstronaut());
        changePasswordButton.addActionListener(e -> changePassword());
        exitButton.addActionListener(e -> System.exit(0));

        frame.add(nameField);
        frame.add(scrollPane);
        frame.add(addButton);
        frame.add(removeButton);
        frame.add(editButton);
        frame.add(changePasswordButton);
        frame.add(exitButton);

        frame.getContentPane().setBackground(Color.BLACK);
        displayAstronautData();

        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }

    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.BLACK); // Black button
        button.setForeground(Color.CYAN); // Cyan text
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2)); // Thin cyan border
    
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 0, 100)); // Slightly purple on hover
                button.setFont(button.getFont().deriveFont(16f));
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK); // Reset to black
                button.setFont(button.getFont().deriveFont(14f));
            }
        });
    
        return button;
    }
    
    private void loadAdminCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("admin_credentials.txt"))) {
            ADMIN_PASSWORD_HASH = reader.readLine().trim();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading admin credentials file!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private boolean authenticateAdmin() {
        String username = JOptionPane.showInputDialog("Enter Admin Username:");
        if (!ADMIN_USERNAME.equals(username)) return false;

        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, "Enter Admin Password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) return false;

        String password = new String(passwordField.getPassword());
        return hashPassword(password).equals(ADMIN_PASSWORD_HASH);
    }

    private void loadAstronautsFromFile() {
        astronautDatabase.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(ASTRONAUTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                astronautDatabase.put(line.trim(), line.trim());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading astronauts file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveAstronautsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ASTRONAUTS_FILE))) {
            for (String astronaut : astronautDatabase.values()) {
                writer.write(astronaut + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving astronauts file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAstronaut() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name field is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        astronautDatabase.put(name, name);
        saveAstronautsToFile();
        displayAstronautData();
    }

    private void removeAstronaut() {
        String name = nameField.getText().trim();
        if (!astronautDatabase.containsKey(name)) {
            JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        astronautDatabase.remove(name);
        saveAstronautsToFile();
        displayAstronautData();
    }

    private void editAstronaut() {
        String name = nameField.getText().trim();
        if (!astronautDatabase.containsKey(name)) {
            JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String newName = JOptionPane.showInputDialog("Enter new name:");
        astronautDatabase.put(newName, astronautDatabase.remove(name));
        saveAstronautsToFile();
        displayAstronautData();
    }

    private void changePassword() {
        String newPassword = JOptionPane.showInputDialog("Enter New Password:");
        ADMIN_PASSWORD_HASH = hashPassword(newPassword);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("admin_credentials.txt"))) {
            writer.write(ADMIN_PASSWORD_HASH);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error updating credentials file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAstronautData() {
        displayArea.setText("");
        astronautDatabase.values().forEach(astronaut -> displayArea.append(astronaut + "\n"));
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void Admin() {
        SwingUtilities.invokeLater(AdminGUI::new);
    }
}

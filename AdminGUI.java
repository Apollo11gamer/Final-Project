import java.awt.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;

public class AdminGUI {
    private static String ADMIN_USERNAME;
    private static String ADMIN_PASSWORD_HASH;
    private static SecretKey secretKey;

    private JFrame frame;
    private JTextArea displayArea;
    private JTextField nameField;
    private final Map<String, Astronaut> astronautDatabase = new HashMap<>();

    public AdminGUI() {
        loadAdminCredentials();
        authenticateAdmin();
    }

    private void displayAstronautData() {
        try {
            File file = new File("astronauts.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            displayArea.setText(content.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading astronaut details.", "Read Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAdminCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("admin_credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("admin_username=")) {
                    ADMIN_USERNAME = line.substring("admin_username=".length());
                } else if (line.startsWith("admin_password_hash=")) {
                    ADMIN_PASSWORD_HASH = line.substring("admin_password_hash=".length());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading admin credentials file!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void authenticateAdmin() {
        createInitialAdminCredentials();
        String username = JOptionPane.showInputDialog("Enter Admin Username:");
        String password = JOptionPane.showInputDialog("Enter Admin Password:");

        String hashedPassword = hashPassword(password);

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD_HASH.equals(hashedPassword)) {
            createGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Access Denied!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private static void createInitialAdminCredentials() {
        String username = "Admin";
        String password = "Kruskie25!"; 

        String hashedPassword = hashPassword(password);
        String encryptedPassword = encryptPassword(password);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("admin_credentials.txt"))) {
            writer.write("admin_username=" + username + "\n");
            writer.write("admin_password_hash=" + hashedPassword + "\n");
            writer.write("admin_password_encrypted=" + encryptedPassword + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing credentials file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String encryptPassword(String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String decryptPassword(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decoded = Base64.getDecoder().decode(encryptedPassword);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void initializeSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void createGUI() {
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setLayout(new BorderLayout());

        // Adding a stylish background color and border
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(6, 2, 10, 10)); // Added space between components
        controlPanel.setBackground(new Color(230, 230, 230));

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));

        // Styled buttons
        JButton addButton = createStyledButton("Add Astronaut");
        JButton removeButton = createStyledButton("Remove Astronaut");
        JButton editButton = createStyledButton("Edit Astronaut");
        JButton changePasswordButton = createStyledButton("Change Password");

        addButton.addActionListener(o -> addAstronaut());
        removeButton.addActionListener(o -> removeAstronaut());
        editButton.addActionListener(o -> editAstronaut());
        changePasswordButton.addActionListener(o -> changePassword());

        controlPanel.add(new JLabel("Name:"));
        controlPanel.add(nameField);
        controlPanel.add(addButton);
        controlPanel.add(removeButton);
        controlPanel.add(editButton);
        controlPanel.add(changePasswordButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        displayAstronautData();

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 150, 250)); // Blue background for a modern look
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside buttons
        return button;
    }

    private void addAstronaut() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name field is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int serialNumber = generateSerialNumber();
        String astronautData = "Serial: " + serialNumber + ", Name: " + name;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("astronauts.txt", true))) {
            writer.write(astronautData);
            writer.newLine();
            JOptionPane.showMessageDialog(frame, "Astronaut added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            displayAstronautData();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing to astronauts.txt!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        clearFields();
    }

    private int generateSerialNumber() {
        Random random = new Random();
        return random.nextInt(100000);
    }

    private void removeAstronaut() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name field is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = new File("astronauts.txt");
        File tempFile = new File("temp_astronauts.txt");
        BufferedReader reader = null;
        BufferedWriter writer = null;

        boolean astronautFound = false;

        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(name)) {
                    astronautFound = true;
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }

            if (!astronautFound) {
                JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            reader.close();
            writer.close();

            if (file.delete()) {
                if (tempFile.renameTo(file)) {
                    JOptionPane.showMessageDialog(frame, "Astronaut successfully removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    displayAstronautData();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error renaming the temp file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Error deleting the original file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading or writing the astronaut file!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void editAstronaut() {
        String name = nameField.getText().trim();
        if (astronautDatabase.get(name) == null) {
            JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String password = JOptionPane.showInputDialog("Enter Admin Password to Edit:");
        if (!ADMIN_PASSWORD_HASH.equals(hashPassword(password))) {
            JOptionPane.showMessageDialog(frame, "Incorrect Password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newName = JOptionPane.showInputDialog("Enter new name:");
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Astronaut astronaut = astronautDatabase.get(name);
        astronaut.setName(newName);
        displayData();
        clearFields();
    }

    private void changePassword() {
        String currentPassword = JOptionPane.showInputDialog("Enter Current Password:");
        if (!ADMIN_PASSWORD_HASH.equals(hashPassword(currentPassword))) {
            JOptionPane.showMessageDialog(frame, "Incorrect current password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newPassword = JOptionPane.showInputDialog("Enter New Password:");
        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "New password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newHashedPassword = hashPassword(newPassword);
        ADMIN_PASSWORD_HASH = newHashedPassword;

        String newEncryptedPassword = encryptPassword(newPassword);
        updateAdminCredentialsFile(newEncryptedPassword);

        JOptionPane.showMessageDialog(frame, "Password changed successfully!");
    }

    private void updateAdminCredentialsFile(String newEncryptedPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("admin_credentials.txt"))) {
            writer.write("admin_username=" + ADMIN_USERNAME + "\n");
            writer.write("admin_password_hash=" + ADMIN_PASSWORD_HASH + "\n");
            writer.write("admin_password_encrypted=" + newEncryptedPassword + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error updating credentials file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayData() {
        displayArea.setText("");
        for (Astronaut astronaut : astronautDatabase.values()) {
            displayArea.append(astronaut + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
    }

    public static void Admin() {
        initializeSecretKey();
        SwingUtilities.invokeLater(AdminGUI::new);
    }
}

class Astronaut {
    private String name;

    public Astronaut(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }
}

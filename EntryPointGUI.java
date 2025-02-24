import java.awt.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import password.PasswordGUI;

public class EntryPointGUI {
    private static final String USER_DATA_FILE = "userdata.enc";
    private static final String SECRET_KEY_FILE = "secret.key";
    private static final String ADMIN_PASS_FILE = "AdminPass.enc"; // File to store encrypted admin password

    public static void main(String[] args) {
        // Initialize the admin password on first run (only if the file doesn't exist)
        initializeAdminPassword();
        
        JFrame frame = new JFrame("BAISD Astronaut Control Panel");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        if (!doesUserExist()) {
            JButton createUserButton = new JButton("Create User");
            createUserButton.addActionListener(e -> createUser());
            panel.add(createUserButton);
        }

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> PasswordGUI.pass());

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(e -> adminAccess());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(loginButton);
        panel.add(adminButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void initializeAdminPassword() {
        // Check if the admin password file exists, if not create it
        File adminPassFile = new File(ADMIN_PASS_FILE);
        if (!adminPassFile.exists()) {
            try {
                String defaultAdminPassword = "Kruskie25!";
                SecretKey secretKey = generateOrLoadKey(); // Load or generate encryption key
                String encryptedPassword = encrypt(defaultAdminPassword, secretKey);

                // Write encrypted password to file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_PASS_FILE))) {
                    writer.write(encryptedPassword);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean doesUserExist() {
        return new File(USER_DATA_FILE).exists();
    }

    private static void createUser() {
        System.out.println("Create user function triggered.");
    
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            return;
        }
    
        // Debug: Check if username already exists
        if (isUsernameTaken(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.");
            return;
        }
    
        // Prompt for password
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;
    
        String password = new String(passwordField.getPassword());
        if (password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty.");
            return;
        }
    
        try {
            SecretKey secretKey = generateOrLoadKey(); // Load or generate encryption key
            String encryptedData = encrypt(username + ":" + password, secretKey);
    
            // Write encrypted user data to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
                writer.write(encryptedData);
                writer.newLine();
            }
    
            JOptionPane.showMessageDialog(null, "User created successfully!");
            System.out.println("User created and saved.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean isUsernameTaken(String username) {
        System.out.println("Checking if username exists: " + username);
        
        if (!new File(USER_DATA_FILE).exists()) {
            return false; // No user file means no users exist yet
        }
    
        try {
            SecretKey secretKey = generateOrLoadKey();
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE));
            String line;
    
            while ((line = reader.readLine()) != null) {
                try {
                    String decryptedData = decrypt(line, secretKey);
                    String storedUsername = decryptedData.split(":")[0];
    
                    System.out.println("Decrypted username from file: " + storedUsername);
    
                    if (storedUsername.equals(username)) {
                        reader.close();
                        return true;
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid encrypted entry.");
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error checking username: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private static void adminAccess() {
        if (!doesUserExist()) {
            JOptionPane.showMessageDialog(null, "No users found!");
            return;
        }

        // Prompt for admin password
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, "Enter Admin Password:", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        String adminPassword = new String(passwordField.getPassword());
        String storedEncryptedPassword;

        // Load the encrypted admin password from file
        try {
            SecretKey secretKey = generateOrLoadKey();
            storedEncryptedPassword = loadFromFile(ADMIN_PASS_FILE);
            String decryptedAdminPassword = decrypt(storedEncryptedPassword, secretKey);

            if (!adminPassword.equals(decryptedAdminPassword)) {
                JOptionPane.showMessageDialog(null, "Access Denied! Incorrect password.");
                return;
            }

            // Open AdminGUI after successful login
            JOptionPane.showMessageDialog(null, "Admin Access Granted.");
            SwingUtilities.invokeLater(() -> new AdminGUI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading admin password: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static SecretKey generateOrLoadKey() throws Exception {
        File keyFile = new File(SECRET_KEY_FILE);
        if (keyFile.exists()) {
            byte[] keyBytes = Base64.getDecoder().decode(loadFromFile(SECRET_KEY_FILE));
            return new SecretKeySpec(keyBytes, "AES");
        } else {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();
            saveToFile(SECRET_KEY_FILE, Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            return secretKey;
        }
    }

    private static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    private static void saveToFile(String filename, String data) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(data);
        }
    }

    private static String loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.readLine();
        }
    }
}

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import password.PasswordGUI;

public class EntryPointGUI {
    private static final String USER_DATA_FILE = "userdata.enc";
    private static final String SECRET_KEY_FILE = "secret.key";
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("BAISD Astronaut Control Panel");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1)); // Adjusted grid layout

        // Conditionally add "Create User" button if no user exists
        if (!doesUserExist()) {
            JButton createUserButton = new JButton("Create User");
            createUserButton.addActionListener(e -> createUser());
            panel.add(createUserButton);
        }

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> PasswordGUI.pass());

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(e -> AdminGUI.Admin());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(loginButton);
        panel.add(adminButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static boolean doesUserExist() {
        return new File(USER_DATA_FILE).exists();
    }

    private static void createUser() {
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username == null || username.isEmpty()) return;

        try {
            SecretKey secretKey = generateOrLoadKey();
            String encryptedData = encrypt(username, secretKey);
            saveToFile(USER_DATA_FILE, encryptedData);
            JOptionPane.showMessageDialog(null, "User created successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating user.");
            e.printStackTrace();
        }
    }

    private static void loginUser() {
        JOptionPane.showMessageDialog(null, "Login functionality not implemented yet.");
    }

    private static void adminAccess() {
        if (!doesUserExist()) {
            JOptionPane.showMessageDialog(null, "No users found!");
            return;
        }

        try {
            SecretKey secretKey = generateOrLoadKey();
            String decryptedData = decrypt(loadFromFile(USER_DATA_FILE), secretKey);
            JOptionPane.showMessageDialog(null, "Admin Access Granted.\nUser Data: " + decryptedData);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing admin data.");
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
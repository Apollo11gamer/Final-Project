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

    public static void main(String[] args) {
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

    private static boolean doesUserExist() {
        return new File(USER_DATA_FILE).exists();
    }

    private static void createUser() {
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            return;
        }

        if (isUsernameTaken(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.");
            return;
        }

        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        String password = new String(passwordField.getPassword());
        if (password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty.");
            return;
        }

        try {
            SecretKey secretKey = generateOrLoadKey();
            String encryptedData = encrypt(username + ":" + password, secretKey);

            // Append instead of overwriting
            try (FileWriter writer = new FileWriter(USER_DATA_FILE, true)) {
                writer.write(encryptedData + "\n");
            }

            JOptionPane.showMessageDialog(null, "User created successfully!");
            // No need to restart application here; just update UI if necessary.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean isUsernameTaken(String username) {
        try {
            SecretKey secretKey = generateOrLoadKey();
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                String decryptedData = decrypt(line, secretKey);
                String storedUsername = decryptedData.split(":")[0];
                if (storedUsername.equals(username)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error checking username: " + e.getMessage());
        }
        return false;
    }

    private static void adminAccess() {
        if (!doesUserExist()) {
            JOptionPane.showMessageDialog(null, "No users found!");
            return;
        }

        try {
            SecretKey secretKey = generateOrLoadKey();
            StringBuilder decryptedData = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                decryptedData.append(decrypt(line, secretKey)).append("\n");
            }
            reader.close();
            JOptionPane.showMessageDialog(null, "Admin Access Granted.\nUser Data:\n" + decryptedData.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing admin data: " + e.getMessage());
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

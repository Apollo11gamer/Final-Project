package password;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.*;
import java.util.Base64;

public class PasswordStorage {
    private static final String FILE_PATH = "users_encrypted.txt"; // Encrypted file path
    private static final Map<String, User> userDatabase = new HashMap<>();
    private static final String SECRET_KEY = "1234567890123456"; // 16-byte secret key (128 bits)

    // Load users from encrypted file at startup
    static {
        loadPasswords();
    }

    // Save a new password entry (username, password, serial number)
    public static void savePassword(String username, String password, String serialNumber) {
        // Ensure that if the username already exists, it will be overwritten
        userDatabase.put(username, new User(username, password, serialNumber));
        saveToFile();  // Persist changes to the file
    }

    // Retrieve a password for a given username
    public static String getPassword(String username) {
        User user = userDatabase.get(username);
        return (user != null) ? user.password : null;
    }

    // Check if a user exists by their username
    public static boolean userExists(String username) {
        return userDatabase.containsKey(username);
    }

    // Remove a password entry by username
    public static boolean removePassword(String username) {
        if (userDatabase.remove(username) != null) {
            saveToFile();  // Persist changes after removal
            return true;
        }
        return false;
    }

    // Get all users in the system
    public static Map<String, User> getAllUsers() {
        return userDatabase;
    }

    // Save all users to the encrypted file
    static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Convert each user to a CSV-like string and encrypt before writing
            for (User user : userDatabase.values()) {
                String userData = user.username + "," + user.password + "," + user.serialNumber;
                String encryptedData = encrypt(userData, SECRET_KEY);
                writer.write(encryptedData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    // Load users from the encrypted file into the system
    public static void loadPasswords() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Decrypt the data before parsing
                String decryptedData = decrypt(line, SECRET_KEY);
                String[] parts = decryptedData.split(",");
                if (parts.length == 3) {
                    userDatabase.put(parts[0], new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }

    // AES encryption method
    private static String encrypt(String data, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    // AES decryption method
    private static String decrypt(String encryptedData, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }

    // User class to store username, password, and serial number
    static class User {
        String username;
        String password;
        String serialNumber;

        User(String username, String password, String serialNumber) {
            this.username = username;
            this.password = password;
            this.serialNumber = serialNumber;
        }
    }
}

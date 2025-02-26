package password;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

public class PasswordStorage {
    private static final String FILE_PATH = "users_encrypted.txt"; // Encrypted file path
    private static final String FILE_NAME = "passwords.txt";
    private static Map<String, String> passwordMap = new HashMap<>();
    private static final Map<String, User> userDatabase = new HashMap<>();
    private static SecretKey secretKey;

    // Load users from encrypted file at startup
    static {
        try {
            secretKey = KeyManagement.generateOrLoadKey(); // Load or generate the AES key
            loadPasswords();
        } catch (Exception e) {
            System.out.println("Error initializing key management: " + e.getMessage());
        }
    }

    // Save a new password entry (username, password, serial number)
    public static void savePassword(String username, String password, String serialNumber) {
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

    // Save all users to the encrypted file
    static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : userDatabase.values()) {
                String userData = user.username + "," + user.password + "," + user.serialNumber;
                String encryptedData = KeyManagement.encrypt(userData, secretKey); // Use KeyManagement for encryption
                writer.write(encryptedData);
                writer.newLine();
            }
        } catch (Exception e) {
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
                String decryptedData = KeyManagement.decrypt(line, secretKey); // Use KeyManagement for decryption
                String[] parts = decryptedData.split(",");
                if (parts.length == 3) {
                    userDatabase.put(parts[0], new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading users from file: " + e.getMessage());
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

    public static Map<String, User> getAllUsers() {
        return new HashMap<>(userDatabase);
    }

        // Remove a user from storage
public static boolean removePassword(String username) throws IOException {
    if (userDatabase.containsKey(username)) {
        userDatabase.remove(username);
        saveAllPasswords(); // Update file
        return true;
    }
    return false;
}

// Save all passwords back to file after removal
private static void saveAllPasswords() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (Map.Entry<String, String> entry : passwordMap.entrySet()) {
            writer.write(entry.getKey() + ":" + entry.getValue());
            writer.newLine();
        }
    }
    
}
}

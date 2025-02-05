package password;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PasswordStorage {
    private static final String FILE_NAME = "passwords.txt";
    private static Map<String, String> passwordMap = new HashMap<>();

    // Load passwords from file
    public static void loadPasswords() {
        passwordMap.clear(); // Clear before loading to avoid duplicates
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    passwordMap.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No stored passwords found.");
        } catch (IOException e) {
            System.out.println("Error loading passwords: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Check if any users exist
    public static boolean hasUsers() {
        return !passwordMap.isEmpty(); // Fixed incorrect reference
    }

    // Save password to file
    public static void savePassword(String username, String password) {
        try {
            String encryptedPassword = EncryptionUtil.encrypt(password);
            passwordMap.put(username, encryptedPassword);

            // Append new user to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write(username + ":" + encryptedPassword);
                writer.newLine();
            }

            System.out.println("Password saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving password: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Retrieve password
    public static String getPassword(String username) {
        try {
            String encryptedPassword = passwordMap.get(username);
            return encryptedPassword != null ? EncryptionUtil.decrypt(encryptedPassword) : null;
        } catch (Exception e) {
            System.out.println("Error retrieving password: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Remove a user from storage
public static boolean removePassword(String username) {
    if (passwordMap.containsKey(username)) {
        passwordMap.remove(username);
        saveAllPasswords(); // Update file
        return true;
    }
    return false;
}

// Save all passwords back to file after removal
private static void saveAllPasswords() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (Map.Entry<String, String> entry : passwordMap.entrySet()) {
            writer.write(entry.getKey() + ":" + entry.getValue());
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving passwords: " + e.getMessage());
    }
}

// Get all usernames
public static String[] getAllUsers() {
    return passwordMap.keySet().toArray(new String[0]);
}
public static boolean userExists(String username) {
    return getPassword(username) != null;  // Returns true if the user exists
}


}

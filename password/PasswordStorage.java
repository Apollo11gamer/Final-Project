package password;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PasswordStorage {
    private static final String FILE_NAME = "passwords.txt";
    private static Map<String, String> passwordMap = new HashMap<>();

    // Load passwords from file
    public static void loadPasswords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    passwordMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("No stored passwords found.");
        }
    }

    // Save password to file
    public static void savePassword(String site, String password) throws Exception {
        String encryptedPassword = EncryptionUtil.encrypt(password);
        passwordMap.put(site, encryptedPassword);
        
        // Write the entire passwordMap to the file (overwrite mode)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : passwordMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        }
    }

    // Retrieve password
    public static String getPassword(String site) throws Exception {
        String encryptedPassword = passwordMap.get(site);
        return encryptedPassword != null ? EncryptionUtil.decrypt(encryptedPassword) : "No password found!";
    }
}

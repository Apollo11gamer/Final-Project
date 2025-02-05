package password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AdminStorage {
    // Predefined admin username
    private static final String ADMIN_USERNAME = "Admin";
    // Predefined hashed password (Use hashed version instead of plaintext)
    private static final String ADMIN_PASSWORD_HASH = hashPassword("NotH3r3!");

    // Hashes the password using SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Verify admin credentials
    public static boolean verifyAdmin(String username, String password) {
        return username.equalsIgnoreCase(ADMIN_USERNAME) && hashPassword(password).equals(ADMIN_PASSWORD_HASH);
    }
}

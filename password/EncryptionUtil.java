package password;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class EncryptionUtil {
    private static final String KEY_FILE = "secret.key";
    private static SecretKey secretKey;

    // Load or Generate Key
    static {
        try {
            File file = new File(KEY_FILE);
            if (file.exists()) {
                // Load the key from file
                try (FileInputStream fis = new FileInputStream(KEY_FILE)) {
                    byte[] keyBytes = fis.readAllBytes();
                    secretKey = new SecretKeySpec(keyBytes, "AES");
                }
            } else {
                // Generate new key and save it
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(256);
                secretKey = keyGen.generateKey();
                try (FileOutputStream fos = new FileOutputStream(KEY_FILE)) {
                    fos.write(secretKey.getEncoded());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Encrypt Password
    public static String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt Password
    public static String decrypt(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }
}

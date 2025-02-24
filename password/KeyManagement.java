package password;

import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyManagement {
    private static final String SECRET_KEY_FILE = "secret_key.txt"; // Path for storing the secret key

    // Generate or load the AES secret key
    public static SecretKey generateOrLoadKey() throws Exception {
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

    // Encrypt data using the provided secret key
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt data using the provided secret key
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    // Save data to a specified file
    private static void saveToFile(String filename, String data) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(data);
        }
    }

    // Load data from a specified file
    private static String loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.readLine();
        }
    }
}

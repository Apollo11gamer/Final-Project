package SubManagements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PhoneNumberVerifier {
    // Regular expression for common phone number formats
    private static final String PHONE_REGEX = "^(\\+\\d{1,3}\\s?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // Maximum number of attempts
    private static final int MAX_ATTEMPTS = 3;

    /**
     * Verifies if the given phone number is valid.
     *
     * @param phoneNumber The phone number to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    /**
     * Logs the invalid attempt to a file.
     *
     * @param phoneNumber The invalid phone number to log.
     */
    public static void logInvalidAttempt(String phoneNumber) {
        try (FileWriter writer = new FileWriter("invalid_attempts.log", true)) {
            writer.write("Invalid phone number attempt: " + phoneNumber + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }

    public static void Phone() {
        Scanner scanner = new Scanner(System.in);
        String inputPhoneNumber;
        int attempts = 0;

        // Allow up to MAX_ATTEMPTS
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Enter a phone number to verify: ");
            inputPhoneNumber = scanner.nextLine();

            if (isValidPhoneNumber(inputPhoneNumber)) {
                System.out.println("Valid phone number! ✅");
                proceedToNextFile();
                break; // Exit the loop if valid
            } else {
                System.out.println("Invalid phone number! ❌ Please try again.");
                attempts++;
            }

            // If the user has reached the max number of attempts
            if (attempts == MAX_ATTEMPTS) {
                System.out.println("Too many invalid attempts. Exiting the program.");
                logInvalidAttempt(inputPhoneNumber); // Log the 3rd invalid attempt
                System.exit(0); // Exit the program
            }
        }
    }

        private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Space ship management...");
            SpaceshipManagement.Ship(); // Ensure this class and method exist
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
}

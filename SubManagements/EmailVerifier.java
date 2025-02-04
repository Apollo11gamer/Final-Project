package SubManagements;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailVerifier {
    
    public static void Email() {
        Scanner kbd = new Scanner(System.in);

        // Regex pattern for validating email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        System.out.println("Enter an email address to verify:");

        while (true) {
            String email = kbd.nextLine();

            // Check if the email matches the pattern
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                System.out.println("Valid email address: " + email);
                proceedToNextFile();  // Call method AFTER printing valid email
                break;  // Exit loop after processing the valid email
            } else {
                System.out.println("Invalid email address. Please try again.");
            }
        }

        kbd.close(); // Close scanner after input processing
    }

    private static void proceedToNextFile() {
        try {
            // Assuming AstronautManagement class is in the same package and has a static method named Space()
            System.out.println("Proceeding to Date of birth verifier...");
            DOBDetector.DOB();
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
}

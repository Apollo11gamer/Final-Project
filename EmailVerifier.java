import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailVerifier {
    
    public static void Email() {
        Scanner scanner = new Scanner(System.in);

        // Regex pattern for validating email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        System.out.println("Enter an email address to verify:");

        while (true) {
            String email = scanner.nextLine();

            // Check if the email matches the pattern
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                System.out.println("Valid email address: " + email);
                break;
            } else {
                System.out.println("Invalid email address. Please try again.");
            }
        }

    }
}

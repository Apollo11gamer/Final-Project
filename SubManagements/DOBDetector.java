package SubManagements;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DOBDetector {

    public static void DOB() {
        Scanner scanner = new Scanner(System.in);
        // Prompts the user to enter their date of birth on the following formats, then stores it in inputDOB while trimming any whitespace.
        System.out.print("Enter your Date of Birth (any format e.g., YYYY-MM-DD, DD/MM/YYYY, MM-DD-YYYY): ");
        String inputDOB = scanner.nextLine().trim();

        // Calculates the age of the user based on the current date.
        LocalDate birthDate = parseDate(inputDOB);
        // If birth date is not null, the birth date and the age calculated will be printed. If the birth date is invalid, a message will tell that the input is invalid.
        if (birthDate != null) {
            int age = calculateAge(birthDate);
            System.out.println("Valid Date of Birth: " + birthDate);
            System.out.println("Your age is " + age);
            proceedToNextFile();
        } else {
            System.out.println("Invalid Date of Birth. Please enter a valid date.");
        }
        scanner.close(); // Close scanner to avoid resource leaks
    }

    private static LocalDate parseDate(String dob) {
        // Normalize the input (replace various separators with "-")
        dob = dob.replaceAll("[./]", "-"); 

        // Ensure input follows a valid pattern (basic numeric check)
        if (!Pattern.matches("\\d{1,4}[-]\\d{1,2}[-]\\d{1,4}", dob)) {
            return null; // Reject malformed input
        }

        // List of possible date formats
        String[] dateFormats = {
            "yyyy-MM-dd", "MM-dd-yyyy", "dd-MM-yyyy",
            "MM/dd/yyyy", "dd/MM/yyyy", "yyyy/MM/dd"
        };

        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(dob, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }
        return null; // If none match, return null (invalid date)
    }

    // Calculates the user's age.
    private static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    // Moved out of DOB() method. Continued to Space() method.
    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Astronaut manager...");
            AstronautManagement.Space();
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
    
    public static void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

package SubManagements;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DOBDetector {

    // Regex pattern for DD/MM/YYYY or MM/DD/YYYY formats
    private static final String DOB_REGEX = "^(0[1-9]|[12][0-9]|3[01])[-/](0[1-9]|1[0-2])[-/](\\d{4})$";

    public static void DOB() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Date of Birth (DD/MM/YYYY or MM/DD/YYYY): ");
        String inputDOB = scanner.nextLine();

        if (isValidDOB(inputDOB)) {
            System.out.println("Valid Date of Birth: " + inputDOB);
            proceedToNextFile();
        } else {
            System.out.println("Invalid Date of Birth. Please enter a valid date.");
        }
        scanner.close();
    }

    private static boolean isValidDOB(String dob) {
        // Check if the input matches the regex pattern
        Pattern pattern = Pattern.compile(DOB_REGEX);
        Matcher matcher = pattern.matcher(dob);
        if (!matcher.matches()) {
            return false; // Invalid format
        }

        // Extract day, month, and year
        String[] parts = dob.split("[-/]");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        // Validate using LocalDate
        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate today = LocalDate.now();

            // Ensure the date is not in the future and the person is under 150 years old
            return !(birthDate.isAfter(today) || year < 1875);
            
        } catch (DateTimeParseException e) {
            return false; // Invalid date (e.g., February 30)
        }
    }

    private static void proceedToNextFile() {
        try {
            // Assuming AstronautManagement class is in the same package and has a static method named Space()
            System.out.println("Proceeding to Astronaut manager...");
            AstronautManagement.Space();
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
}

package SubManagements;
import java.util.Scanner;

public class AstronautManagement {
    private static final Scanner kbd = new Scanner(System.in); // Use a single Scanner instance

    public static void Space() {
        String name;
        double weight;

        System.out.println("What is the name of the astronaut?");
        name = kbd.nextLine().trim();

        System.out.println("What is the weight (in lbs) of the astronaut?");
        while (true) {
            if (kbd.hasNextDouble()) {
                weight = kbd.nextDouble();
                kbd.nextLine(); // Consume the newline character
                break;
            } else {
                System.out.println("Invalid input! Please enter a valid weight:");
                kbd.next(); // Consume invalid input
            }
        }

        System.out.println("\nAstronaut Details:");
        System.out.println("Name: " + name);
        System.out.println("Weight: " + weight + " lbs");
        System.out.println();
        proceedToNextFile();
    }

    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Phone number verifier...");
            PhoneNumberVerifier.Phone(); // Ensure this class and method exist
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
}

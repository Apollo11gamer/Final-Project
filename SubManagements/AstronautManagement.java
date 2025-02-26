package SubManagements;
import java.util.Scanner;

public class AstronautManagement {
    private static final Scanner kbd = new Scanner(System.in); // Use a single Scanner instance

    public static void Space() {
        String name; // String for the name of the astronaut.
        double weight; // Double for the weight of the astronaut in pounds.

        // Prompts the user to enter a name for the astronaut, then stores the input in name while trimming any whitespace.
        System.out.println("What is the name of the astronaut?");
        name = kbd.nextLine().trim();

        // Prompts the user to enter an amount of pounds the astronaut weighs, then stores the input in weight.
        // If the amount entered is a double value, it will go through. If it is not, a message will tell the user that the input is invalid.
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

        // Prints astronaut details.
        System.out.println("\nAstronaut Details:");
        System.out.println("Name: " + name);
        System.out.println("Weight: " + weight + " lbs");
        System.out.println();
        proceedToNextFile();
    }

    // Moved out of Space() method. Continued to Ship() method.
    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Phone number verifier...");
            PhoneNumberVerifierGUI.phone(); // Ensure this class and method exist
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
}

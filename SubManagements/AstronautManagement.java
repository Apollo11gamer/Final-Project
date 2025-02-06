package SubManagements;
import java.util.Scanner;

public class AstronautManagement {
    public static void Space() {
        String name;
        double weight;
        Scanner kbd = new Scanner(System.in);

        System.out.println("What is the name of the astronaut?");
        name = kbd.nextLine();

        System.out.println("What is the weight (in lbs) of the astronaut?");
        while (!kbd.hasNextDouble()) {
            System.out.println("Invalid input! Please enter a valid weight:");
            kbd.next(); // Consume invalid input
        }
        weight = kbd.nextDouble();
        kbd.nextLine(); // Consume the newline character

        System.out.println("Astronaut Details:");
        System.out.println("Name: " + name);
        System.out.println("Weight: " + weight + " lbs");

        kbd.close(); // Close scanner to prevent resource leaks
        proceedToNextFile();
    }

    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Space ship management...");
            SpaceshipManagement.Space();
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
}

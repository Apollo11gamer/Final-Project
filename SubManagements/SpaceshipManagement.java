package SubManagements;

import java.util.Scanner;

public class SpaceshipManagement {

    public static void Ship() {
        String shuttleName;
        double fuelCapacity;
        int astronautCapacity;
        Scanner kbd = new Scanner(System.in);

        System.out.println("What is the name of the shuttle?");
        shuttleName = kbd.nextLine();

        System.out.println("What is the maximum amount of fuel (pounds of fuel) for the spaceship?");
        fuelCapacity = kbd.nextDouble();

        System.out.println("What is the maximum amount of astronauts that can be a part of the crew?");
        astronautCapacity = kbd.nextInt();
        kbd.nextLine(); // Consume newline left-over from nextInt()

        String[] astronautNames = new String[astronautCapacity];

        if (astronautCapacity > 1) {
            System.out.println("Please enter the names of the " + astronautCapacity + " astronauts:");
            for (int i = 0; i < astronautCapacity; i++) {
                System.out.print("Astronaut " + (i + 1) + ": ");
                astronautNames[i] = kbd.nextLine();
            }
        } else if (astronautCapacity == 1) {
            System.out.println("Please enter the name of the astronaut:");
            System.out.print("Astronaut 1: ");
            astronautNames[0] = kbd.nextLine();
        }

        if (fuelCapacity > 10000) {
            System.out.println("\nThe ship, '" + shuttleName + "', will be attempting to launch to the moon.");
            System.out.println("There are " + astronautCapacity + " member(s) aboard with " + fuelCapacity + " pounds of fuel.");

            if (astronautCapacity > 0) {
                System.out.println("Crew Members: ");
                for (String name : astronautNames) {
                    System.out.println("- " + name);
                }
            }

            proceedToNextFile();
        } else {
            System.out.println(shuttleName + " requires more fuel to leave the planet. Please try again.");
            System.exit(0);
        }
    }

    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Launch sequence...");
            Launch.launch();
        } catch (Exception e) {
            System.out.println("Error starting Launch sequence: " + e.getMessage());
        }
    }

    public static void ship() {
        Ship(); // Calling the Ship method
    }
}

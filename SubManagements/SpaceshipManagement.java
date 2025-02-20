package SubManagements;

import java.util.Scanner;

public class SpaceshipManagement {

    public static void Ship() {
        String shuttleName; // String for the name of the spaceship.
        double fuelCapacity; // Double for the pounds of fuel the spaceship can hold.
        int astronautCapacity; // Integer for the amount of astronauts the spaceship can hold.
        Scanner kbd = new Scanner(System.in);

        // Prompts for the name of the spaceship, then stores the answer in shuttleName.
        System.out.println("What is the name of the shuttle?");
        shuttleName = kbd.nextLine();

        // Prompts for the maximum amount of fuel the spaceship can hold, then stores the answer in fuelCapacity.
        System.out.println("What is the maximum amount of fuel(pounds of fuel) for the spaceship?");
        fuelCapacity = kbd.nextDouble();

        // Prompts for the maximum amount of astronauts the spaceship can hold, then stores the answer in astronautCapacity.
        System.out.println("What is the maximum amount of astronauts that can be a part of the crew?");
        astronautCapacity = kbd.nextInt();

        kbd.close();

        // If fuelCapacity is greater than 10,000 pounds of fuel, then it can launch. If it is not, a message will tell that you need more fuel.
        if (fuelCapacity > 10000) {
            System.out.println("The ship, '" + shuttleName + "', will be attempting to launch to the moon. There are " + astronautCapacity + " member(s) with " + fuelCapacity + " pounds of fuel to work with.");
            proceedToNextFile();
        } else {
            System.out.println(shuttleName + " requires more fuel to leave the planet, please try again.");
        }
    }

    // Moved outside of Ship() method. Continuing to launch() method.
    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Launch sequence...");
            Launch.launch();
        } catch (Exception e) {
            System.out.println("Error starting Lauch sequence: " + e.getMessage());
        }
    }

    public static void ship() {
        Ship();  // Calling the Ship method in main.
    }
}

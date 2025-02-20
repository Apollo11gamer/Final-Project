package SubManagements;

import java.util.Scanner;

public class SpaceshipManagement {

    public static void Ship() {
        String shuttleName;
        double fuelCapacity;
        int astronautCapacity;
        Scanner kbd = new Scanner(System.in);


        System.out.println("What is the shuttle's name?");
        shuttleName = kbd.next();

        System.out.println("What is the maximum amount of fuel(pounds of fuel) for the spaceship?");
        fuelCapacity = kbd.nextDouble();

        System.out.println("What is the maximum amount of astronauts that can be a part of the crew?");
        astronautCapacity = kbd.nextInt();

        kbd.close();

        if (fuelCapacity > 10000) {
            System.out.println("The ship, '" + shuttleName + "', will be attempting to launch to the moon. There are " + astronautCapacity + " member(s) with " + fuelCapacity + " pounds of fuel to work with.");
            proceedToNextFile();
        } else {
            System.out.println(shuttleName + " requires more fuel to leave the planet, please try again.");
        }
    }

    // Moved outside of Ship() method
    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Launch sequence...");
            Launch.launch();
        } catch (Exception e) {
            System.out.println("Error starting Lauch sequence: " + e.getMessage());
        }
    }

    public static void ship() {
        Ship();  // Calling the Ship method in main
    }
}

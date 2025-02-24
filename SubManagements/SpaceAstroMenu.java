package SubManagements;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SpaceAstroMenu {

    public static void main(String[] args) {
    MENU();
    }

    public static void MENU() {

    Scanner kbd = new Scanner(System.in);

    System.out.println("\nSpaceship and Astronaut Menu");
    System.out.println("\nASTRONAUT OPTIONS");
    System.out.println("1. Create astronauts");
    System.out.println("2. Edit astronauts");
    System.out.println("3. Remove astronauts");
    System.out.println("\nSPACESHIP OPTIONS");
    System.out.println("4. Create spaceships");
    System.out.println("5. Prepare spaceships");

    System.out.print("\nEnter choice: ");

        // Validate integer input
        while (!kbd.hasNextInt()) {
        System.out.println("\nInvalid input! Please enter a number between 1-4.");
        kbd.nextLine(); // Consume invalid input
        }

        int choice = kbd.nextInt();
        kbd.nextLine(); // Consume newline
        
        try {
        switch (choice) {
        case 1 -> createAstronaut();
        case 2 -> System.out.println("You are editing an astronaut");
        case 3 -> System.out.println("You are removing an astronaut");
        case 4 -> System.out.println("You are creating a spaceship");
        case 5 -> System.out.println("You are preparing a spaceship to launch");
        }
        }
        catch (Exception e) {
        System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        e.printStackTrace();
        }

    }

    public static void createAstronaut() {
    String name = ""; // String for the name of the astronaut.
    double weight = 0; // Double for the weight of the astronaut in pounds.
    Scanner kbd = new Scanner(System.in);

    // Prompts the user to enter a name for the astronaut, then stores the input in name while trimming any whitespace.
    System.out.println("What is the name of the astronaut?");
    name = kbd.nextLine().trim();
    while(name.isEmpty()) {
    System.out.println("Invalid input! Do not enter an empty space. Enter a name:"); 
    name = kbd.nextLine().trim();
    }

    // Prompts the user to enter an amount of pounds the astronaut weighs, then stores the input in weight.
    // If the amount entered is a double value, it will go through. If it is not, a message will tell the user that the input is invalid.
    System.out.println("What is the weight (in lbs) of the astronaut?");
    while(!kbd.hasNextDouble()) {
    System.out.println("Invalid input! Please enter an integer/decimal number:");
    kbd.next(); // Consume invalid input    
    } 
    weight = kbd.nextDouble();
    kbd.nextLine(); // Consume the newline character

    System.out.println("Successfully made an astronaut!");

    saveAstronautInfo(name, weight);
    MENU();

    }

    private static void saveAstronautInfo(String name, double weight) {
    String filePath = "astronautsInfo.txt";

    try (BufferedWriter author = new BufferedWriter(new FileWriter(filePath, true))) {
    author.write(name + ", " + weight);
    author.newLine();
    System.out.println("Astronaut data saved.");
    }
    catch (IOException e) {
        System.out.println("Error saving astronaut details." + e.getMessage());
    }
    }

}
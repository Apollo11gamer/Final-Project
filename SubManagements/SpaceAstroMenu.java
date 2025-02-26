package SubManagements;
import java.util.Scanner;

import password.PasswordStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class SpaceAstroMenu {
    
    private static final String astronautsFile = "astronautsInfo.txt";
    SpaceAstroMenu object = new SpaceAstroMenu();
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
            case 2 -> editAstronautsFile(astronautsFile);
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

    public static void saveAstronautInfo(String name, double weight) {

        try (BufferedWriter author = new BufferedWriter(new FileWriter(astronautsFile, true))) {
            author.write(name + ", " + weight);
            author.newLine();
            System.out.println("Astronaut data saved.");
        }
        catch (IOException e) {
            System.out.println("Error saving astronaut details." + e.getMessage());
        }

    }

    public static void editAstronautsFile(String astronautsFile) throws IOException{

        Scanner kbd = new Scanner(System.in);

        System.out.println("\nHere is the list of astronauts and their details to edit.");
        try (BufferedReader reader = new BufferedReader(new FileReader(astronautsFile))) {
            String astronautLine = "";
            int astronautNumber = 1;
            // Read and print each line of the file
            while ((astronautLine = reader.readLine()) != null) {
                System.out.println(astronautNumber + ". " + astronautLine);
                astronautNumber++;
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., file not found or access issues)
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        int editChoice = 0;

        System.out.println("Enter the name of the astronaut you want to edit.");
        String astronautChoice = kbd.nextLine().trim();

        // Check if the user exists
        if (!astronautExists(astronautChoice)) {
            System.out.println("Astronaut not found!");
            return; // Exit if the user doesn't exist
        }
        else if (astronautExists(astronautChoice)) {
            System.out.println("Astronaut was found!");
            System.out.println("What do you want to edit?");
            System.out.println("1. Name");
            System.out.println("2. Weight");
            editChoice = kbd.nextInt();
        }

            switch (editChoice) {
                case 1 -> returnName();
                case 2 -> returnWeight();
            }
            
            MENU(); // Go back to menu if the user does exist
        
    }

    public static boolean astronautExists(String astronautChoice) {
        try (BufferedReader reader = new BufferedReader(new FileReader(astronautsFile))) {
         String chosenAstronaut;
        while((chosenAstronaut = reader.readLine()) != null) {
        // Split the line by ", " to separate the name and weight
         String[] astronautDetails = chosenAstronaut.split(", ");
         String astronautName = astronautDetails[0]; // First part is the name
        if (astronautName.equalsIgnoreCase(astronautChoice)){
            return true;
        }
        }
        } 
        catch (IOException e) {
            System.out.println("Error checking astronaut: " + e.getMessage());
        }
            return false; // If astronaut not found
        }

    public static String returnName() {
     String newName;
     Scanner kbd = new Scanner(System.in);
        System.out.print("Enter the new name:");
        newName = kbd.nextLine();
        return newName;
    }

    public static double returnWeight() {
     double newWeight;
     Scanner kbd = new Scanner(System.in);
        System.out.print("Enter the new weight:");
        newWeight = kbd.nextDouble();
        return newWeight;
    }
        
    }
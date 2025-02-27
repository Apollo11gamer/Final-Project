package SubManagements;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import password.Password;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.FileReader;

public class SpaceAstroMenu {
    
    public static final String astronautsFile = "astronautsInfo.txt";
    public static final String spaceshipsFile = "spaceshipsInfo.txt";
    
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
        System.out.println("\nEXIT MENU");
        System.out.println("6. Exit");

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
            case 3 -> removeAstronaut();
            case 4 -> createSpaceship();
            case 5 -> prepareLaunch();
            case 6 -> Password.pass();
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
            author.write(name + " | " + weight);
            author.newLine();
            System.out.println("Astronaut data saved.");
        }
        catch (IOException e) {
            System.out.println("Error saving astronaut details." + e.getMessage());
        }

    }

    public static void editAstronautsFile(String astronautsFile) throws IOException{

        Scanner kbd = new Scanner(System.in);

        StringBuilder fileContent = new StringBuilder();
        String astronautLine = "";
        int astronautNumber = 1;

        System.out.println("\nHere is the list of astronauts and their details to edit.");
        try (BufferedReader reader = new BufferedReader(new FileReader(astronautsFile))) {
            // Read and print each line of the file
            while ((astronautLine = reader.readLine()) != null) {
                fileContent.append(astronautLine).append("\n");
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

        String newName = "";
        double newWeight = 0;
        String updatedAstronaut = "";

            try {
                switch (editChoice) {
                case 1 : newName = returnName();
                updatedAstronaut = updateAstronautInfo(astronautChoice, newName, null);
                break;
                case 2 : newWeight = returnWeight();
                updatedAstronaut = updateAstronautInfo(astronautChoice, null, newWeight);
                break;
                default : System.out.println("Invalid choice.");
                break;
            }
            }
            catch (Exception e) {
                System.out.println("\nAn unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
            
             MENU(); // Go back to menu if the astronaut does exist (after astronaut is edited)
        
    }

    public static boolean astronautExists(String astronautChoice) {
        try (BufferedReader reader = new BufferedReader(new FileReader(astronautsFile))) {
         String chosenAstronaut;
        while((chosenAstronaut = reader.readLine()) != null) {
        // Split the line by ", " to separate the name and weight
         String[] astronautDetails = chosenAstronaut.split(" | ");
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

    public static String updateAstronautInfo(String oldName, String newName, Double newWeight) {
     String updatedAstronaut = "";

    try (BufferedReader reader = new BufferedReader(new FileReader(astronautsFile))) {
     String line;
    while ((line = reader.readLine()) != null) {
    String[] astronautDetails = line.split(" | ");
    String currentName = astronautDetails[0];
    double currentWeight = Double.parseDouble(astronautDetails[1]);
    if (currentName.equalsIgnoreCase(oldName)) {
        if (newName != null) {
            currentName = newName;
        }
        if (newWeight != null) {
            currentWeight = newWeight;
        }
        updatedAstronaut = currentName + " | " + currentWeight;
        break;
    }
    }
    }
    catch (IOException e) {
        System.out.println("Error message: " + e.getMessage());
    }
    return updatedAstronaut;
    }
        
    public static void saveUpdatedAstronautInfo(StringBuilder fileContent, String updatedAstronaut) {
    try(BufferedWriter author = new BufferedWriter(new FileWriter(astronautsFile, true))) {
    String updatedFileContent = fileContent.toString().replaceFirst("(?m)^" + updatedAstronaut.split(" | ")[0] + ", .+", updatedAstronaut);
    author.write(updatedFileContent);
    System.out.println("Astronaut details updated successfully.");
    }
    catch (IOException e) {
        System.out.println("Error saving updated astronaut details: " + e.getMessage());
    }
    }

    public static void removeAstronaut(String spaceshipsFile) throws IOException {

    }

    public static void createSpaceship() {

        String spaceshipName; // String for the name of the astronaut.
        double spaceshipFuelCapacity; // Double for the weight of the astronaut in pounds.
        int spaceshipCrewCapacity;
        double spaceshipFuel;
        String spaceshipAstronaut;
        Scanner kbd = new Scanner(System.in);
   
       // Prompts the user to enter a name for the astronaut, then stores the input in name while trimming any whitespace.
           System.out.println("What is the name of the spaceship you want to load with fuel and astronauts?");
           spaceshipName = kbd.nextLine().trim();
       while(spaceshipName.isEmpty()) {
           System.out.println("Invalid input! Do not enter an empty space. Enter a spaceship name:"); 
           spaceshipName = kbd.nextLine().trim();
       }
   
       // Prompts the user to enter an amount of pounds the astronaut weighs, then stores the input in weight.
       // If the amount entered is a double value, it will go through. If it is not, a message will tell the user that the input is invalid.
           System.out.println("How many pounds of fuel can be loaded into the spaceship?");
       while(!kbd.hasNextDouble()) {
           System.out.println("Invalid input! Please enter an integer/decimal number:");
           kbd.next(); // Consume invalid input    
       } 
        spaceshipFuelCapacity = kbd.nextDouble();
        kbd.nextLine(); // Consume the newline character

            System.out.println("How many crew members can be loaded into the spaceship?");
        while(!kbd.hasNextInt()) {
            System.out.println("Invalid input! Please enter an integer/decimal number:");
            kbd.next(); // Consume invalid input    
        } 
         spaceshipCrewCapacity = kbd.nextInt();
         kbd.nextLine(); // Consume the newline character
   
           System.out.println("Successfully made an astronaut!");
   
        saveSpaceshipInfo(spaceshipName, spaceshipFuelCapacity, spaceshipCrewCapacity);
        MENU();

    }

    public static void saveSpaceshipInfo(String spaceshipName, double spaceshipFuelCapacity, int spaceshipCrewCapacity) {
    
    try (BufferedWriter author = new BufferedWriter(new FileWriter(spaceshipsFile, true))) {
        author.write(spaceshipName + " | " + spaceshipFuelCapacity + " | " + spaceshipCrewCapacity);
        author.newLine();
        System.out.println("Spaceship data saved.");
    }
    catch (IOException e) {
        System.out.println("Error saving astronaut details." + e.getMessage());
    }

    }

    public static void removeAstronaut() {

    }

    public static void prepareLaunch() {

    }
    
    }
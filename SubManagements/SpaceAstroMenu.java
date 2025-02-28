package SubManagements;
import java.util.Scanner;
import password.Password;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class SpaceAstroMenu {

    public static final String astronautsFile = "astronautsInfo.txt";
    public static final String spaceshipsFile = "spaceshipsInfo.txt";
    public static final String spaceshipsLaunchPreparation = "spaceshipsContain.txt";

    public static void main(String[] args) {

        MENU();
        
    }

    /**
     * Displays the main menu and handles user input for the options.
     */
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

            System.out.println("\nInvalid input! Please enter a number between 1-6.");
            kbd.nextLine(); // Consume invalid input

        }

        int choice = kbd.nextInt();
        kbd.nextLine(); // Consume newline

        try {

        switch (choice) {

            case 1 -> createAstronaut();
            case 2 -> editAstronautsFile();
            case 3 -> removeAstronaut();
            case 4 -> createSpaceship();
            case 5 -> prepareLaunch();
            case 6 -> Password.pass();
            default -> System.out.println("Invalid choice.");

        }

        } 
        
        catch (Exception e) {

            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * Creates a new astronaut by gathering input and saving it to the file.
     */
    public static void createAstronaut() {

        Scanner kbd = new Scanner(System.in);

        System.out.println("What is the name of the astronaut?");
        String name = kbd.nextLine().trim();

        while (name.isEmpty()) {

            System.out.println("Invalid input! Do not enter an empty space. Enter a name:");
            name = kbd.nextLine().trim();

        }

        System.out.println("What is the weight (in lbs) of the astronaut?");
        double weight;

        while (!kbd.hasNextDouble()) {

            System.out.println("Invalid input! Please enter a valid weight (integer or decimal).");
            kbd.next(); // Consume invalid input

        }

        weight = kbd.nextDouble();

        saveAstronautInfo(name, weight);
        System.out.println("Successfully created astronaut!");
        MENU();

    }

    /**
     * Saves astronaut information into the file.
     */
    public static void saveAstronautInfo(String name, double weight) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(astronautsFile, true))) {

            writer.write(name + ", " + weight);
            writer.newLine();
            System.out.println("Astronaut data saved.");

        } 
        
        catch (IOException e) {

            System.out.println("Error saving astronaut details: " + e.getMessage());

        }
    }

    /**
     * Edits an existing astronaut's details.
     */
    public static void editAstronautsFile() throws IOException {

        Scanner kbd = new Scanner(System.in);
        List<String> astronautList = loadAstronauts();

        if (astronautList.isEmpty()) {

            System.out.println("No astronauts to edit.");
            return;

        }

        System.out.println("\nHere is the list of astronauts to edit.");
        
        astronautList.forEach(System.out::println);

        System.out.println("Enter the name of the astronaut you want to edit:");
        String astronautChoice = kbd.nextLine().trim();

        if (!astronautExists(astronautChoice)) {

            System.out.println("Astronaut not found!");

            return;
        }

        System.out.println("What do you want to edit?");
        System.out.println("1. Name");
        System.out.println("2. Weight");

        int editChoice = kbd.nextInt();
        kbd.nextLine(); // Consume newline

        String updatedAstronaut = "";
        String newName;
        double newWeight;

        try {

            switch (editChoice) {

            case 1 : newName = returnName();
            updatedAstronaut = updateAstronautInfo(astronautChoice, newName, null);
            saveUpdatedAstronautInfo(updatedAstronaut);
            break;
            case 2 : newWeight = returnWeight();
            updatedAstronaut = updateAstronautInfo(astronautChoice, null, newWeight);
            saveUpdatedAstronautInfo(updatedAstronaut);
            break;
            default : System.out.println("\nInvalid choice.");
            break;

        }

        }

        catch (Exception e) {

            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();

        }

        System.out.println("\nAstronaut details updated successfully.");
        MENU();

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


    /**
     * Loads astronauts from the file into a list.
     */
    public static List<String> loadAstronauts() {

        List<String> astronautList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(astronautsFile))) {

            String line;

            while ((line = reader.readLine()) != null) {

                astronautList.add(line);

            }
            
        } 

        catch (IOException e) {

            System.out.println("Error reading astronaut details: " + e.getMessage());

        }

        return astronautList;

    }

    /**
     * Checks if an astronaut exists in the file.
     */
    public static boolean astronautExists(String astronautChoice) {

        return loadAstronauts().stream().anyMatch(astronaut -> astronaut.startsWith(astronautChoice));

    }

    public static String updateAstronautInfo(String oldName, String newName, Double newWeight) {

        String updatedAstronaut = "";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(astronautsFile))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] astronautDetails = line.split(", ");
                String currentName = astronautDetails[0];  // Extract the current name
                double currentWeight = Double.parseDouble(astronautDetails[1]);  // Extract the current weight
                
                // Check if the current astronaut matches the one to be updated
                if (currentName.equalsIgnoreCase(oldName)) {
                    // Update the name if newName is not null
                    if (newName != null) {
                        currentName = newName;
                    }
                    // Update the weight if newWeight is not null
                    if (newWeight != null) {
                        currentWeight = newWeight;
                    }
                    
                    // Construct the updated astronaut string with the new name and/or weight
                    updatedAstronaut = currentName + ", " + currentWeight;
                    break;  // We found and updated the astronaut, so break out of the loop
                }

            }

        } catch (IOException e) {

            System.out.println("Error updating astronaut info: " + e.getMessage());

        }
    
        return updatedAstronaut;
    }
    
    /**
     * Saves the updated astronaut details into the file.
     */
    public static void saveUpdatedAstronautInfo(String updatedAstronaut) {

        List<String> astronautList = loadAstronauts();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(astronautsFile))) {

            for (String astronaut : astronautList) {

                if (astronaut.startsWith(updatedAstronaut.split(",")[0])) {

                    writer.write(updatedAstronaut);

                } 
                
                else {

                    writer.write(astronaut);

                }

                writer.newLine();
            }

        } 
        
        catch (IOException e) {

            System.out.println("Error updating astronaut details: " + e.getMessage());

        }

    }

    /**
     * Removes an astronaut from the file.
     */
    public static void removeAstronaut() throws IOException {

        Scanner kbd = new Scanner(System.in);
        System.out.println("Enter the name of the astronaut to remove:");
        String astronautChoice = kbd.nextLine().trim();

        if (!astronautExists(astronautChoice)) {

            System.out.println("\nAstronaut not found!");
            return;

        }

        List<String> astronautList = loadAstronauts();
        astronautList.removeIf(astronaut -> astronaut.startsWith(astronautChoice));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(astronautsFile))) {

            for (String astronaut : astronautList) {

                writer.write(astronaut);
                writer.newLine();

            }

            System.out.println("\nAstronaut removed successfully.");

        } 
        
        catch (IOException e) {
        
            System.out.println("Error removing astronaut: " + e.getMessage());

        }

        MENU();
    }

    /**
     * Creates a new spaceship by gathering input and saving it to the file.
     */
    public static void createSpaceship() {

        Scanner kbd = new Scanner(System.in);

        System.out.println("What is the name of the spaceship?");
        String spaceshipName = kbd.nextLine().trim();

        System.out.println("How many pounds of fuel can be loaded into the spaceship?");
        double spaceshipFuelCapacity = kbd.nextDouble();

        System.out.println("How many crew members can be loaded into the spaceship?");
        int spaceshipCrewCapacity = kbd.nextInt();

        saveSpaceshipInfo(spaceshipName, spaceshipFuelCapacity, spaceshipCrewCapacity);
        System.out.println("\nSuccessfully created spaceship!");
        MENU();

    }

    /**
     * Saves spaceship information into the file.
     */
    public static void saveSpaceshipInfo(String spaceshipName, double spaceshipFuelCapacity, int spaceshipCrewCapacity) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(spaceshipsFile, true))) {

            writer.write(spaceshipName + ", " + spaceshipFuelCapacity + ", " + spaceshipCrewCapacity);
            writer.newLine();
            System.out.println("Spaceship data saved.");

        } 
        
        catch (IOException e) {

            System.out.println("Error saving spaceship details: " + e.getMessage());

        }
    }

    // Prepares a spaceship for launch (fuel and astronauts).
    
    public static void prepareLaunch() {

        
        Scanner kbd = new Scanner(System.in);

        System.out.println("\nHere is the list of astronauts to edit.");
        List<String> spaceshipList = loadSpaceships();
        spaceshipList.forEach(System.out::println);

        System.out.println("Enter the name of the spaceship you want to load with fuel and crew members:");
        String spaceshipChoice = kbd.nextLine().trim();

        if (!spaceshipExists(spaceshipChoice)) {

            System.out.println("\nSpaceship not found!");

            return;
        }

        System.out.println("\nSpaceship was found!");
        System.out.println("How many pounds of fuel do you want to load into the spaceship?");
        double currentFuel = kbd.nextDouble();

        while(currentFuel != kbd.nextDouble()) {

            System.out.println("Invalid fuel amount! Enter an integer/decimal.");
            kbd.next();
            currentFuel = kbd.nextDouble();
            break;

        }

        kbd.nextLine();
        System.out.println("What crew members do you want to board the spaceship? (List the members and separate with commas)");
        String crewList = kbd.nextLine();
        String[] currentCrew = crewList.split(", ");

        saveSpaceshipLoadInfo(spaceshipChoice, currentFuel, currentCrew);


    }

    public static void saveSpaceshipLoadInfo(String spaceshipName, double spaceshipFuel, String[] spaceshipCrew) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(spaceshipsLaunchPreparation, true))) {

            String crewString = String.join(", ", spaceshipCrew);
            writer.write(spaceshipName + ", " + spaceshipFuel+ ", " + crewString);
            writer.newLine();
            System.out.println("Spaceship loading data saved.");

        } 
        
        catch (IOException e) {

            System.out.println("Error saving spaceship details: " + e.getMessage());

        }
    }

    public static List<String> loadSpaceships() {

        List<String> spaceshipList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(spaceshipsFile))) {

            String line;
            while ((line = reader.readLine()) != null) {

                spaceshipList.add(line);

            }
        } 

        catch (IOException e) {

        System.out.println("An error occurred: " + e.getMessage());

        }

        return spaceshipList;

    }

    public static boolean spaceshipExists(String spaceshipChoice) {

        return loadSpaceships().stream().anyMatch(spaceship -> spaceship.startsWith(spaceshipChoice));

    }

}
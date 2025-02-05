package password;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import SubManagements.EmailVerifier;

public class Password {
    private static boolean isFirstTime = true; // Tracks if user is registering for the first time
    private static final String LOG_FILE = "login_attempts_log.txt";  // Log file path

    public static void pass() {
        Scanner scanner = new Scanner(System.in);

        // Ensure PasswordStorage class has a static method
        PasswordStorage.loadPasswords();

        if (PasswordStorage.hasUsers()) { // Check if users exist
            isFirstTime = false;
        }

        while (true) {
            System.out.println("\nPassword Manager");

            if (isFirstTime) {
                System.out.println("1. Create login info");
            }

            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("4. Admin Access");
            System.out.print("Enter choice: ");

            // Validate integer input
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1-4.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                if (isFirstTime && choice == 1) {
                    savePassword(scanner);
                    isFirstTime = false; // Disable registration after first time
                } else if (!isFirstTime && choice == 1) {
                    System.out.println("User already registered. Please login.");
                } else {
                    switch (choice) {
                        case 2 -> login(scanner);
                        case 3 -> {
                            System.out.println("Exiting...");
                            System.exit(0);
                            return; // Exit method without closing scanner
                        }
                        case 4 -> adminAccess(scanner);
                        default -> System.out.println("Invalid choice! Please enter a valid option.");
                    }
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void savePassword(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            PasswordStorage.savePassword(username, password);
            System.out.println("Registration successful! You can now log in.");
        } catch (Exception e) {
            System.out.println("Error saving password: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean login(Scanner scanner) {
        int usernameAttempts = 3;  // Retry limit for username
        int passwordAttempts = 3;  // Retry limit for password

        // Loop for username retries
        while (usernameAttempts > 0) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            // Debugging: print username being entered
            System.out.println("Checking username: " + username);

            // Check if the username exists before proceeding
            if (!PasswordStorage.userExists(username)) {
                usernameAttempts--;
                System.out.println("Username not found! You have " + usernameAttempts + " attempt(s) left.");
                if (usernameAttempts <= 0) {
                    System.out.println("Too many failed attempts. GOODBYE! :)");
                    logFailedAttempt("Username " + "'" + username + "'"); // Log failed attempt for username
                    System.exit(0);  // Exit after too many failed attempts
                    return false;  // Lockout after too many failed username attempts
                }
                continue;  // Allow retry for username
            }

            // If username is valid, move to password check
            while (passwordAttempts > 0) {
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                try {
                    String storedPassword = PasswordStorage.getPassword(username);

                    if (storedPassword != null && storedPassword.equals(password)) {
                        System.out.println("Welcome " + username + "!");
                        proceedToNextFile();
                        return true;  // Login successful
                    } else {
                        passwordAttempts--;
                        System.out.println("Invalid Username or password. You have " + passwordAttempts + " attempt(s) left.");
                        if (passwordAttempts == 0) {
                            System.out.println("Too many failed attempts. GOODBYE!:)");
                            logFailedAttempt("Password " + "'" + password + "'"); // Log failed attempt for password
                            return false;  // Lockout after too many failed password attempts
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred while retrieving the password: " + e.getMessage());
                    e.printStackTrace();
                    return false;  // Login failed due to an error
                }
            }
            break;  // Exit the username loop if password is validated
        }

        return false;  // Default return value if loop exits (this shouldn't happen)
    }

    // Method to log the failed attempt with the timestamp
    private static void logFailedAttempt(String type) {
        try {
            File logFile = new File(LOG_FILE);
            if (!logFile.exists()) {
                logFile.createNewFile();  // Create the file if it doesn't exist
            }

            // Get the current timestamp
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Log to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write("Failed " + type + " attempt at " + timestamp);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    

    private static void adminAccess(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        // Ensure only the Admin username is allowed
        if (!username.equalsIgnoreCase("Admin")) {
            System.out.println("Invalid username! Admin access is restricted.");
            return;
        }

        System.out.print("Enter Admin password: ");
        String password = scanner.nextLine();

        if (AdminStorage.verifyAdmin(username, password)) {
            System.out.println("Admin authentication successful!");
            adminMenu(scanner);
        } else {
            System.out.println("Invalid admin credentials! Access denied.");
        }
    }

    // Admin menu to add/remove users
    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. View Users");
            System.out.println("4. Exit Admin Mode");
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1-4.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addUser(scanner);
                case 2 -> removeUser(scanner);
                case 3 -> viewUsers();
                case 4 -> {
                    System.out.println("Exiting Admin Mode...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }

    // Add a new user
    private static void addUser(Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String newPassword = scanner.nextLine();

        try {
            PasswordStorage.savePassword(newUsername, newPassword);
            System.out.println("User added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
    


    // Remove a user
    private static void removeUser(Scanner scanner) {
        System.out.print("Enter username to remove: ");
        String usernameToRemove = scanner.nextLine();

        if (PasswordStorage.removePassword(usernameToRemove)) {
            System.out.println("User removed successfully!");
            System.out.println("Restarting the program...");
            
        } else {
            System.out.println("User not found.");
        }
    }

    // View all stored users
    private static void viewUsers() {
        System.out.println("Registered Users:");
        for (String user : PasswordStorage.getAllUsers()) {
            System.out.println("- " + user);
        }
    }

    private static void proceedToNextFile() {
        try {
            System.out.println("Proceeding to Email verification...");
            EmailVerifier.Email();
        } catch (Exception e) {
            System.out.println("Error starting EmailVerifier: " + e.getMessage());
        }
    }
}

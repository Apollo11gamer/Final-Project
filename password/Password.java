package password;

import java.util.Scanner;
import SubManagements.EmailVerifier;

public class Password {
    private static boolean isFirstTime = true; // Tracks if user is registering for the first time

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
                System.out.println("1. Create login info: ");
            }
            
            System.out.println("2. Login: ");
            System.out.println("3. Exit: ");
            System.out.println("4. Admin Access: ");
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
                            scanner.close();
                            System.out.close(); // Close scanner before exiting
                            return; // Exit method
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

    private static void login(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            String storedPassword = PasswordStorage.getPassword(username);

            if (storedPassword != null && storedPassword.equals(password)) {
                System.out.println("Welcome " + username + "!");
                proceedToNextFile();
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving the password: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void adminAccess(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
    
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

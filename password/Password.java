package password;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import SubManagements.EmailVerifier;

public class Password {
    private static final String LOG_FILE = "Failed_login_attempts_log.txt";

    public static void pass() {
        Scanner scanner = new Scanner(System.in);

        while (true) { // Infinite loop to restart the program on exit
            PasswordStorage.loadPasswords();
            boolean hasUsers = PasswordStorage.hasUsers();

            System.out.println("\nMain Menu");
            if (!hasUsers) System.out.println("1. Create login info");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("4. Admin Access");
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1-4.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                if (!hasUsers && choice == 1) {
                    savePassword(scanner);
                } else if (hasUsers && choice == 1) {
                    System.out.println("User already registered. Please login.");
                } else {
                    switch (choice) {
                        case 2 -> login(scanner);
                        case 3 -> {
                            System.out.println("Returning to main menu...");
                            continue; // Restart menu instead of exiting
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
            System.out.println("Registration successful! Returning to main menu...");
        } catch (Exception e) {
            System.out.println("Error saving password: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void login(Scanner scanner) {
        int usernameAttempts = 3, passwordAttempts = 3;

        while (usernameAttempts > 0) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            if (!PasswordStorage.userExists(username)) {
                usernameAttempts--;
                System.out.println("Username not found! Attempts left: " + usernameAttempts);
                if (usernameAttempts == 0) {
                    System.out.println("Too many failed attempts. Returning to main menu...");
                    logFailedAttempt("Username " + username);
                    return;
                }
                continue;
            }

            while (passwordAttempts > 0) {
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                try {
                    if (PasswordStorage.getPassword(username).equals(password)) {
                        System.out.println("Welcome " + username + "!");
                        proceedToNextFile();
                        return;
                    } else {
                        passwordAttempts--;
                        System.out.println("Invalid password. Attempts left: " + passwordAttempts);
                        if (passwordAttempts == 0) {
                            System.out.println("Too many failed attempts. Returning to main menu...");
                            logFailedAttempt("Password " + password);
                            return;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error retrieving password: " + e.getMessage());
                    return;
                }
            }
        }
    }

    private static void logFailedAttempt(String type) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("Failed " + type + " attempt at " + timestamp);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    private static void adminAccess(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();

        if (!username.equalsIgnoreCase("Admin")) {
            System.out.println("Invalid username! Returning to main menu...");
            return;
        }

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (AdminStorage.verifyAdmin(username, password)) {
            System.out.println("Admin authentication successful!");
            adminMenu(scanner);
        } else {
            System.out.println("Invalid credentials! Returning to main menu...");
        }
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. View Users");
            System.out.println("4. Exit Admin Mode");
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Enter a number between 1-4.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addUser(scanner);
                case 2 -> removeUser(scanner);
                case 3 -> viewUsers();
                case 4 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter a valid option.");
            }
        }
    }

    private static void addUser(Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String newPassword = scanner.nextLine();

        try {
            PasswordStorage.savePassword(newUsername, newPassword);
            System.out.println("User added successfully! Returning to admin menu...");
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    private static void removeUser(Scanner scanner) {
        System.out.print("Enter username to remove: ");
        String usernameToRemove = scanner.nextLine();

        if (PasswordStorage.removePassword(usernameToRemove)) {
            System.out.println("User removed successfully! Returning to main menu...");
        } else {
            System.out.println("User not found! Returning to admin menu...");
        }
    }

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

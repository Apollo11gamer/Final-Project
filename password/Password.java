package password;

import java.util.Scanner;
import SubManagements.EmailVerifier;

public class Password {
    public static void pass() {
        Scanner scanner = new Scanner(System.in);

        // Ensure PasswordStorage class has a static method
        PasswordStorage.loadPasswords();

        while (true) {
            System.out.println("\nPassword Manager");
            System.out.println("1. Save Password");
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
                switch (choice) {
                    case 1 -> savePassword(scanner);
                    case 2 -> login(scanner);
                    case 3 -> {
                        System.out.println("Exiting...");
                        scanner.close(); // Close scanner before exiting
                        return; // Exit method
                    }
                    case 4 -> adminAccess(scanner);
                    default -> System.out.println("Invalid choice! Please enter a number between 1-4.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void savePassword(Scanner scanner) {
        System.out.print("Enter Username: ");
        String site = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            PasswordStorage.savePassword(site, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Password saved successfully!");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter Username: ");
        String site = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
    
        try {
            String storedPassword = PasswordStorage.getPassword(site);
    
            if (storedPassword != null && storedPassword.equals(password)) {
                System.out.println("Welcome " + site + "!");
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

        if (!username.equalsIgnoreCase("Admin")) {
            System.out.println("Admin access is only available for 'Admin'.");
            return;
        }

        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Admin password: ");
            String password = scanner.nextLine();

            if (password.equals("NotH3r3!")) {
                System.out.println("Admin authentication successful. Nice Caulk!");
                return; // Exit after success
            } else {
                attempts--;
                System.out.println("Incorrect Admin password! " + attempts + " attempts remaining.");
            }
        }
        System.out.println("Too many failed attempts. Admin access denied.");
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

package password;

import java.util.Scanner;

public class Password {
    public static void pass() {
        Scanner scanner = new Scanner(System.in);
        PasswordStorage.loadPasswords();

        while (true) {
            System.out.println("\nPassword Manager");
            System.out.println("1. Save Password");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("4. Admin Access");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                if (choice == 1) {
                    // Save a new password
                    System.out.print("Enter Username: ");
                    String site = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    PasswordStorage.savePassword(site, password);
                    System.out.println("Password saved!");

                } else if (choice == 2) {
                    // Login
                    System.out.print("Enter Username: ");
                    String site = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    String storedPassword = PasswordStorage.getPassword(site);
                    if (storedPassword != null && storedPassword.equals(password)) {
                        System.out.println("Welcome " + site);
                        proceedToNextFile();  // Proceed to AstronautManagement after successful login
                    } else {
                        System.out.println("Invalid username or password.");
                    }

                } else if (choice == 3) {
                    // Exit
                    System.out.println("Exiting...");
                    break;

                } else if (choice == 4) {
                    // Admin Access
                    System.out.print("Enter Username: ");
                    String site = scanner.nextLine();

                    if (site.equals("Admin")) {
                        boolean adminAuthenticated = false;

                        // Repeat until correct admin password is entered
                        while (!adminAuthenticated) {
                            System.out.print("Enter Admin password: ");
                            String password = scanner.nextLine();

                            if (password.equals("NotH3r3!")) {
                                adminAuthenticated = true;
                                System.out.println("Nice Caulk");
                                // Exit program after successful admin authentication
                                System.exit(0);
                            } else {
                                System.out.println("Incorrect Admin password! Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Admin access is only available for the site 'Admin'.");
                    }

                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Close the scanner when done
        scanner.close();
    }

    // Method to proceed to the next file (AstronautManagement)
    private static void proceedToNextFile() {
        try {
            // Assuming AstronautManagement class is in the same package and has a static main method
            System.out.println("Proceeding to Email verification...");
            System.out.close();
        } catch (Exception e) {
            System.out.println("Error starting AstronautManagement: " + e.getMessage());
        }
    }
}

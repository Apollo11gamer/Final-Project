package PasswordFunction;
import java.util.Scanner;

public class Password {
    public static void pass() {
        Scanner scanner = new Scanner(System.in);
        PasswordStorage.loadPasswords();

        while (true) {
            System.out.println("\n Password Manager");
            System.out.println("1. Save Password");
            System.out.println("2. Retrieve Password");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                if (choice == 1) {
                    System.out.print("Enter site name: ");
                    String site = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    PasswordStorage.savePassword(site, password);
                    System.out.println("Password saved!");
                } else if (choice == 2) {
                    System.out.print("Enter site name: ");
                    String site = scanner.nextLine();
                    String password = PasswordStorage.getPassword(site);
                    System.out.println("Password: " + password);
                } else if (choice == 3) {
                    System.out.println("Exiting...");
                    System.out.close();
                    break;
             
                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                e.printStackTrace();
               
            }
        }
        
    }
}
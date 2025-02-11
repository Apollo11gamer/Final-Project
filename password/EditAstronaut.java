package password;
import java.util.Scanner;

public class EditAstronaut {

    // Method to edit user details
    public static void editUserInfo(Scanner scanner) {
        // Prompt to enter the username of the user to edit
        System.out.print("Enter the username of the user you want to edit: ");
        String username = scanner.nextLine();

        // Check if the user exists
        if (!PasswordStorage.userExists(username)) {
            System.out.println("User not found!");
            return; // Exit if the user doesn't exist
        }

        // Retrieve the current user details
        PasswordStorage.User user = PasswordStorage.getAllUsers().get(username);
        System.out.println("Current details for " + username + ":");
        System.out.println("Password: " + user.password);
        System.out.println("Serial Number: " + user.serialNumber);

        // Ask if the user wants to change the password
        System.out.print("Do you want to change the password? (y/n): ");
        String changePassword = scanner.nextLine();

        // Change password if user agrees
        if (changePassword.equalsIgnoreCase("y")) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            user.password = newPassword; // Update password in the user object
            System.out.println("Password updated successfully!");
        }

        // Ask if the user wants to change the serial number
        System.out.print("Do you want to change the serial number? (y/n): ");
        String changeSerial = scanner.nextLine();

        // Change serial number if user agrees
        if (changeSerial.equalsIgnoreCase("y")) {
            System.out.print("Enter new serial number: ");
            String newSerial = scanner.nextLine();
            user.serialNumber = newSerial; // Update serial number in the user object
            System.out.println("Serial number updated successfully!");
        }

        // Save updated user information to the storage
        try {
            PasswordStorage.saveToFile();  // Ensure user data is saved to the file
            System.out.println("User details have been updated successfully!");
        } catch (Exception e) {
            System.out.println("Error saving user details: " + e.getMessage());
        }
    }
}

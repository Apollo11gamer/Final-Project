package SubManagements;
import java.util.Scanner;

public class AstronautManagement {
    public static void Space() {

String Name;
String DOB;
String Address;
String Email;
double Pay_Rate;
double Weight;
String Status;
String Kin;
String Reset = "\u001B[0m";
String Red = "\u001B[31m"; 
Scanner kbd = new Scanner(System.in);


System.out.println("What is the name of the astronaut?");
    Name = kbd.nextLine();

System.out.println("What is the weight (in lbs) of the astronaut?");
    Weight = kbd.nextDouble();
        proceedToNextFile();
    }


    private static void proceedToNextFile() {
        try {
            // Assuming AstronautManagement class is in the same package and has a static method named Space()
            System.out.println("Proceeding to Space ship management...");
            SpaceshipManagement.Space();
        } catch (Exception e) {
            System.out.println("Error proceeding to the next file: " + e.getMessage());
        }
    }
}
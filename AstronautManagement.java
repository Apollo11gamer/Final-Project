import java.util.Scanner;

import SubManagements.EmailVerifier;
import SubManagements.SpaceshipManagement;

public class AstronautManagement {
    public static void Astro() {

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

        System.out.println("What is the Date of Birth of the astronaut?");
        DOB = kbd.nextLine();

        EmailVerifier.Email();

        System.out.println("What is the weight (in lbs) of the astronaut?");
        Weight = kbd.nextDouble();

        SpaceshipManagement.Space();

        LaunchSequence.Countdown();

        System.out.close();
    }
}
import java.util.Scanner;

public class AstronautManagement {




    public static void main(String[] args) {

String Name;
String DOB;
String Address;
String Email;
double Pay_Rate;
double Weight;
String Status;
String Kin;
Scanner kbd = new Scanner(System.in);
    
System.out.println("What is the name of the astronaut?");
    Name = kbd.nextLine();
System.out.println("What is the Date of Birth of the astronaut?");
    DOB = kbd.nextLine();
System.out.println("What is the Email of the astronaut?");
    Email = kbd.nextLine();
    String input = Email;
    String regex = "(?=.*[@]).*";
    
    if (input.matches(regex)) {
        System.out.println("The input contains the required characters.");
    } else {
        System.out.println("The input does not contain the required characters.");
    }
    System.out.println("What is the weight of the astronaut?");
    Weight = kbd.nextDouble();

}
}
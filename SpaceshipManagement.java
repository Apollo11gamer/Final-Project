import java.util.Scanner;


public class SpaceshipManagement {
    
public static void main(String[] args) {

String shuttle;
double fuel;
int astronauts;
Scanner kbd = new Scanner(System.in);


System.out.println("What is the name of the shuttle?");
    shuttle = kbd.nextLine();


System.out.println("How much fuel will be added?");
    fuel = kbd.nextDouble();


System.out.println("How many astronauts will be a part of the crew?");
    astronauts = kbd.nextInt();

    kbd.close();

    if (fuel > 40000) {
        System.out.println("The ship " + shuttle + " will be attempting to launch to the moon. There are " + astronauts + " members with " + fuel + " pounds of fuel to work with." );
    }
    // If the fuel isn't enough, then the prompt will reset // 
    else {
        System.out.println(shuttle + " requires more fuel to leave the planet, please try again.");
    }

    System.out.close();
}
}

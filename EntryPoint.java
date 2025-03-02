import SubManagements.AstronautManagement;
import SubManagements.DOBDetector;
import SubManagements.EmailVerifier;
import SubManagements.Launch;
import SubManagements.SpaceshipManagement;
import password.Password;


public class EntryPoint{


public static void main(String[] args) {

    // Prints out a welcome message for the user.
    System.out.println("===============================================");
    System.out.println(" | Welcome to BAISD Astronaut Control Panel |");
    System.out.println("===============================================");

    // Provides the user with a menu to log in to make astronauts and spaceships, and to exercise admin controls.
    Password.pass();
    // Verifies if the email is written correctly.
    EmailVerifier.Email();
    // Verifies if the date of birth of the astronaut is correctly written.
    DOBDetector.DOB();
    // Lets the user create an astronaut with details.
    AstronautManagement.Space();
    // Lets the user create an spaceship with details.
    SpaceshipManagement.Ship();
    // Launches the user's spaceship into outer space, has the astronauts proceed with a spacewalk, then returns back to Earth.
    Launch.launch();

System.exit(0);
}
}
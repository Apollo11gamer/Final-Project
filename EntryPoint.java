import SubManagements.AstronautManagement;
import SubManagements.DOBDetector;
import SubManagements.EmailVerifier;
import SubManagements.SpaceshipManagement;
import password.Password;


public class EntryPoint{


public static void main(String[] args) {

    System.out.println("=========================================");
    System.out.println("|  🚀 Welcome to BAISD Astronaut Control Panel |");
    System.out.println("=========================================");

    Password.pass();
    EmailVerifier.Email();
    DOBDetector.DOB();
    AstronautManagement.Space();
    SpaceshipManagement.Ship();




System.exit(0);
}
}
import SubManagements.SpaceshipManagement;
import SubManagements.AstronautManagement;
import SubManagements.DOBDetector;
import SubManagements.EmailVerifier;
import SubManagements.Launch;
import SubManagements.SpaceshipManagement;
import password.Password;


public class EntryPoint{


public static void main(String[] args) {

    System.out.println("===============================================");
    System.out.println("|  🚀 Welcome to BAISD Astronaut Control Panel |");
    System.out.println("===============================================");

    Password.pass();
    EmailVerifier.Email();
    DOBDetector.DOB();
    AstronautManagement.Space();
<<<<<<< HEAD
    SpaceshipManagement.Space();
=======
    SpaceshipManagement.Ship();
    Launch.launch();

>>>>>>> 4f56f32dfcf1924af58674235e645f1f8eef84e4



System.exit(0);
}
}
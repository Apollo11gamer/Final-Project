import SubManagements.AstronautManagement;
import SubManagements.DOBDetector;
import SubManagements.EmailVerifier;
import SubManagements.SpaceshipManagement;
import password.Password;

public class EntryPoint{


public static void main(String[] args) {

    Password.pass();
    EmailVerifier.Email();
    DOBDetector.DOB();
    AstronautManagement.Space();
    SpaceshipManagement.Ship();




System.out.close();
}
}
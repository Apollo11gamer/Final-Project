import SubManagements.AstronautManagement;
import SubManagements.DOBDetector;
import SubManagements.EmailVerifier;
import SubManagements.Launch;
import SubManagements.SpaceshipManagement;
import password.Password;
import javax.swing.SwingUtilities;


public class EntryPoint{


public static void main(String[] args) {
    // Launch the GUI
    SwingUtilities.invokeLater(() -> new EntryPointGUI());
}
}
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
    SpaceshipManagement.Ship();
    Launch.launch();




System.exit(0);
}
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import SubManagements.SpaceshipManagement;
import SubManagements.AstronautManagement;
import SubManagements.DOBDetector;
import SubManagements.EmailVerifier;
import SubManagements.Launch;
import password.Password;


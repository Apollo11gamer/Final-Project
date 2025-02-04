import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class LaunchSequence {

// Constants
    // This is the gravity effect for increasing and decreasing speed by 9.81 m/s^2 each second.
    private static final double GRAVITY_EFFECT = 9.81;
    // This is the altitude where reaching over or to 70000 meters allows for a successful launch to spacewalk.
    private static final int SUCCESSFUL_LAUNCH_ALTITUDE = 70000;
    // This is the maximum speed for returning to Earth, which is 3000 m/s.
    private static final int RETURN_MAXIMUM_SPEED = 3000;
    // This is the altitude where reaching below 10000 meters allows for the parachute to deploy.
    private static final int PARACHUTE_ALTITUDE = 10000;
    // This is the maximum speed reduction during parachute deployment.
    private static final int PARACHUTE_SPEED = 7;

// Variables
    // This is the altitude variable (measures in meters).
    double altitude = 0;
    // This is the speed variable (measures in meters per second).
    double speed = 0;
    // This is the fuel variable of the spacecraft being launched (in pounds).
    double fuel = 1000;
    // This is the boolean variable to activate the spacewalk when turned true.
    private boolean spacewalkStarted = false;

    public static void main(String[] args) {
    


    }

}


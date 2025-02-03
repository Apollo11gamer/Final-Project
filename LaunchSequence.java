import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class LaunchSequence {

    public static void main(String[] args) {
    // Countdown from 10 to launch off
    // m/s = fuelBurned x 30
    // altitude = 0 meters, to altitude = 70,000 meters
    // once 70,000 meters is reached, 30 second timer starts for spacewalk
    // When altitude reaches 0 meters, display that the ship has landed
    tenSecondCountdown();
    simulateFlight();
    }
    }

    public static void tenSecondCountdown() {
    Timer timer = new Timer();

    timer.scheduleAtFixedRate(new TimerTask() {
    int time = 10;
    int countDown = time;

    public void run() {
    System.out.println(countDown);

    if (countDown <= 0) {
    System.out.println("LAUNCHED");
    timer.cancel();
    }
    countDown--;
    }
    }, 0, 1000);
    
    }

    public static void simulateFlight() {
        //Simulation of rocket flight mechanics after countdown
        Timer flightTimer = new Timer();

        // Start flight simulation
        flightTimer.scheduleAtFixedRate(new TimerTask() {
        public void run() {

        double currentFuel = 100;
        double fuelBurned = 10;
        double speed = 100;
        double altitude = 0;
        double currentSpeed = speed;
        int spacewalkTime = 30; // Spacewalk time

        // Decrease fuel
        currentFuel -= fuelBurned;
        // Increase speed based on fuel burn
        currentSpeed += fuelBurned * 30;

        // Update altitude (simple model: altitude increases with speed)
        altitude += currentSpeed;

        System.out.printf("Altitude: %.2f meters, Speed: %.2f m/s, Remaining fuel: %.2f pounds\n", altitude, currentSpeed, currentFuel);

        // Check for spacewalk if altitude reaches 70,000 meters
        if (altitude >= 70000 && spacewalkTime > 0) {
        System.out.println("Altitude reached 70,000 meters. Spacewalk starts!");
        System.out.println("Spacewalk remaining time: " + spacewalkTime + " seconds.");
        spacewalkTime--;
        }

        // Spacewalk completed after 30 seconds
        if (spacewalkTime <= 0 && altitude >= 70000) {
        System.out.println("Spacewalk completed.");
        //Stop the spacewalk timer after it's done
        }

        // Check for return to Earth (altitude decreasing with gravity)
        if (altitude >= 70000) {
        // Simulate descent (increased speed due to gravity)
        currentSpeed += 9.81; // gravity increases speed during descent
        }

        // If altitude reaches 0, simulate landing
        if (altitude <= 0) {
        System.out.println("The ship has landed safely. Astronauts may exit.");
        flightTimer.cancel();
        }
    }
}, 0, 1000); 
// Simulate flight every second   
}
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class LaunchSequence {

    public static void Countdown() {

        // Flight and fuel
        double altitude = 0;
        double fuelBurned = 0;
        double speed;
        double currentFuel;
        Scanner kbd = new Scanner(System.in);

        System.out.println("Enter currentFuel: ");
        currentFuel = kbd.nextDouble();

        System.out.println("Enter fuelBurned: ");
        fuelBurned = kbd.nextDouble();

        currentFuel =- fuelBurned;
        speed = fuelBurned * 30;

        // 10 Second Countdown
        int time = 10;
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
        int countDown = time;

        public void run() {
        System.out.println(countDown);
        

        if (countDown <= 0) {
        System.out.println("LAUNCH");
        timer.cancel();
        }
        countDown--;
        }
        }, 0, 1000);
        

        if (altitude >= 70000) {

        // 30 Second Timer
        int spacewalkTime = 30;
        Timer spacewalkTimer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
        int spacewalkCountdown = spacewalkTime;

        public void run() {
        System.out.println(spacewalkCountdown);

        if (spacewalkCountdown <= 0) {
        System.out.println("SPACEWALKED");
        spacewalkTimer.cancel();
        }
        spacewalkCountdown--;
        }
        }, 0, 1000);

        }
        else if (altitude < 70000) {
        System.out.println("Spacecraft did not make it.");
        }

    }

    // code that may be helpful:

    // public static void simulateFlight(double currentFuel, double fuelBurned, double speed) {
        // Simulation of rocket flight mechanics after countdown
        // double altitude = 0;
        // double currentSpeed = speed;
        // int spacewalkTime = 30; // Spacewalk time
        // Timer flightTimer = new Timer();

        // Start flight simulation
        // flightTimer.scheduleAtFixedRate(new TimerTask() {
        // public void run() {
        // Decrease fuel
        // currentFuel -= fuelBurned;
        // Increase speed based on fuel burn
        // currentSpeed += fuelBurned * 30;

        // Update altitude (simple model: altitude increases with speed)
        //altitude += currentSpeed;

        // System.out.printf("Altitude: %.2f meters, Speed: %.2f m/s, Remaining fuel: %.2f pounds\n", altitude, currentSpeed, currentFuel);

        // Check for spacewalk if altitude reaches 70,000 meters
        // if (altitude >= 70000 && spacewalkTime > 0) {
        // System.out.println("Altitude reached 70,000 meters. Spacewalk starts!");
        // System.out.println("Spacewalk remaining time: " + spacewalkTime + " seconds.");
        // spacewalkTime--;
        // }

        // Spacewalk completed after 30 seconds
        // if (spacewalkTime <= 0 && altitude >= 70000) {
        // System.out.println("Spacewalk completed.");
        // Stop the spacewalk timer after it's done
        // }

        // Check for return to Earth (altitude decreasing with gravity)
        // if (altitude >= 70000) {
        // Simulate descent (increased speed due to gravity)
        // currentSpeed += 9.81; // gravity increases speed during descent
        // }

        // If altitude reaches 0, simulate landing
        // if (altitude <= 0) {
        // System.out.println("The ship has landed safely. Astronauts may exit.");
        // flightTimer.cancel();
        // }
    // }
// }, 0, 1000); 
// Simulate flight every second
    
    }

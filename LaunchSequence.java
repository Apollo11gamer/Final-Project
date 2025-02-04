import java.util.Timer;
import java.util.TimerTask;

public class LaunchSequence {
    
    static double currentFuel = 1000;
    static double fuelBurned = 10;
    static double speed = 100;
    static double altitude = 0;
    static double currentSpeed = speed;
    static int spacewalkTime = 30; // Spacewalk duration
    static boolean inSpacewalk = false;
    
    public static void Launch() {
        tenSecondCountdown();
        
        try {
            Thread.sleep(11000); // Wait for countdown to finish before launching
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        simulateFlight();
    }

    public static void tenSecondCountdown() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int countDown = 10;

            public void run() {
                if (countDown > 0) {
                    System.out.println(countDown);
                } else {
                    System.out.println("LAUNCHED");
                    timer.cancel();
                }
                countDown--;
            }
        }, 0, 1000);
    }

    public static void simulateFlight() {
        Timer flightTimer = new Timer();

        flightTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (currentFuel > 0) {
                    // Burn fuel and increase speed
                    currentFuel -= fuelBurned;
                    currentSpeed += fuelBurned * 100;
                } else {
                    System.out.println("Out of fuel! Coasting...");
                }

                // Update altitude
                altitude += currentSpeed;

                System.out.printf("Altitude: %.2f meters, Speed: %.2f m/s, Remaining fuel: %.2f pounds\n", altitude, currentSpeed, currentFuel);

                // Check for spacewalk
                if (altitude >= 70000 && !inSpacewalk) {
                    System.out.println("Altitude reached 70,000 meters. Spacewalk starts!");
                    inSpacewalk = true;
                }

                if (inSpacewalk) {
                    if (spacewalkTime > 0) {
                        System.out.println("Spacewalk remaining time: " + spacewalkTime + " seconds.");
                        spacewalkTime--;
                    } else {
                        System.out.println("Spacewalk completed. Preparing for descent.");
                        inSpacewalk = false;
                    }
                }

                // Simulate descent after spacewalk
                if (!inSpacewalk && altitude > 0) {
                    currentSpeed -= 9.81; // Gravity slows ascent and speeds up descent
                    altitude -= currentSpeed; // Reduce altitude

                    if (altitude <= 0) {
                        altitude = 0;
                        System.out.println("The ship has landed safely. Astronauts may exit.");
                        flightTimer.cancel();
                    }
                }
            }
        }, 0, 1000); // Update every second
    }
}

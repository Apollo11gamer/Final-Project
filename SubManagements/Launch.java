package SubManagements;
public class Launch {

    // Constants for the rocket launch parameters
    private static final double FUEL_BURN_RATE = 1; // Burn rate per second
    private static final double INITIAL_FUEL = 100.0; // Initial fuel in liters
    private static final double ROCKET_MASS = 500.0; // Rocket mass in kg
    private static final double THRUST = 20000.0; // Rocket thrust in Newtons
    private static final double GRAVITY = 9.81; // Earth's gravity (m/s^2)
    private static final double SPACEWALK_ALTITUDE = 70000.0; // Altitude at which spacewalk begins
    private static final double PARACHUTE_DEPLOY_ALTITUDE = 10000.0; // Altitude to deploy parachute

    // Variables for tracking the rocket's current state
    private static double currentFuel = INITIAL_FUEL;
    private static double currentSpeed = 10.0;
    private static double currentAltitude = 0.0;
    private static boolean parachuteDeployed = false;

    // Method to perform countdown and launch simulation
    public void initiateLaunch() {
        // Countdown from 10 to 0
        System.out.println("Initiating countdown in...");
        System.out.println("T-");
        for (int i = 10; i >= 0; i--) {
            System.out.println(i + "...");
            try {
                Thread.sleep(1000); // Wait for 1 second before each countdown step
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Begin launch sequence
        System.out.println("Launch initiated!");

        // Simulate the rocket launch with fuel burn
        boolean spacewalkStarted = false;
        long spacewalkStartTime = 0;

        while (currentFuel > 0) {
            // Only increase altitude and speed if below spacewalk altitude
            if (currentAltitude < SPACEWALK_ALTITUDE) {
                double acceleration = (THRUST / ROCKET_MASS) - GRAVITY; // Calculate acceleration
                currentSpeed += acceleration; // Increase speed
                currentAltitude += currentSpeed; // Increase altitude based on speed

                // Burn fuel based on the burn rate
                currentFuel -= FUEL_BURN_RATE;
                
                // Output the current rocket status
                System.out.printf("Fuel: %.2f L | Speed: %.2f m/s | Altitude: %.2f m%n", currentFuel, currentSpeed, currentAltitude);
            } else {
                currentSpeed = 0; // Stop increasing speed
                currentAltitude = SPACEWALK_ALTITUDE; // Keep altitude at 70,000 meters

                if (!spacewalkStarted) {
                    spacewalkStarted = true;
                    spacewalkStartTime = System.currentTimeMillis();
                    System.out.println("Rocket has reached 70,000 meters! Spacewalk countdown started.");
                }

                // Check if spacewalk has completed (30 seconds)
                long timePassed = (System.currentTimeMillis() - spacewalkStartTime) / 1000;
                if (timePassed < 30) {
                    System.out.println("Spacewalk Timer: " + (30 - timePassed) + " seconds remaining.");
                } else {
                    System.out.println("Spacewalk complete. Beginning descent.");
                    break;
                }
            }

            // Wait for the next second
            try {
                Thread.sleep(1000); // 1-second intervals
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Begin descent after spacewalk
        System.out.println("Rocket starting descent...");

        while (currentAltitude > 0) {
            double descentAcceleration = -GRAVITY; // Gravity pulls the rocket down
            currentSpeed += descentAcceleration; // Increase downward speed
            currentAltitude += currentSpeed; // Decrease altitude

            // Deploy parachute when below 10,000 meters
            if (currentAltitude <= PARACHUTE_DEPLOY_ALTITUDE && !parachuteDeployed) {
                parachuteDeployed = true;
                currentSpeed *= 0.2; // Reduce speed by 80%
                System.out.println("Parachute deployed! Slowing descent...");
            }

            if (currentAltitude <= 0) {
                currentAltitude = 0;
                System.out.println("Rocket has landed safely.");
                break;
            }

            System.out.printf("Descending... Speed: %.2f m/s | Altitude: %.2f m%n", currentSpeed, currentAltitude);

            try {
                Thread.sleep(1000); // 1-second intervals
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Final output after the rocket lands
        System.out.printf("Final speed: %.2f m/s | Final altitude: %.2f m%n", currentSpeed, currentAltitude);
        System.exit(0);
    }

    public static void launch() {
        // Create a new instance of the Launch class and initiate the launch sequence
        Launch launch = new Launch();
        launch.initiateLaunch();
    }
}

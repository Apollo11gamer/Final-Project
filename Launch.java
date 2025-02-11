public class Launch {

    // Constants for the rocket launch parameters
    private static final double FUEL_BURN_RATE = 0.5; // Burn rate per second
    private static final double INITIAL_FUEL = 100.0; // Initial fuel in liters
    private static final double ROCKET_MASS = 500.0; // Rocket mass in kg
    private static final double THRUST = 20000.0; // Rocket thrust in Newtons
    private static final double GRAVITY = 9.81; // Earth's gravity (m/s^2)
    private static final double SPACEWALK_ALTITUDE = 70000.0; // Altitude at which spacewalk begins (70,000 meters)

    // Variables for tracking the rocket's current state
    private static double currentFuel = INITIAL_FUEL;
    private static double currentSpeed = 5.0;
    private static double currentAltitude = 0.0;

    // Method to perform countdown and launch simulation
    public void initiateLaunch() {
            // Countdown from 10 to 0
            System.out.println("Initiating countdown...");
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
                // Update speed and altitude based on current fuel and thrust
                double acceleration = (THRUST / ROCKET_MASS) - GRAVITY; // Calculate acceleration
                currentSpeed += acceleration; // Increase speed
                currentAltitude += currentSpeed; // Increase altitude based on speed
    
                // Burn fuel based on the burn rate
                currentFuel -= FUEL_BURN_RATE;
                
                // Output the current rocket status
                System.out.printf("Fuel: %.2f L | Speed: %.2f m/s | Altitude: %.2f m%n", currentFuel, currentSpeed, currentAltitude);
    
                // Check if the rocket has reached spacewalk altitude
                if (!spacewalkStarted && currentAltitude >= SPACEWALK_ALTITUDE) {
                    spacewalkStarted = true;
                    spacewalkStartTime = System.currentTimeMillis();
                    System.out.println("Rocket has reached 70,000 meters! Spacewalk countdown started.");
                }
    
                // If spacewalk has started, check the time passed for 30 seconds
                if (spacewalkStarted) {
                    long timePassed = (System.currentTimeMillis() - spacewalkStartTime) / 1000; // Time in seconds
                    if (timePassed < 30) {
                        System.out.println("Spacewalk Timer: " + (30 - timePassed) + " seconds remaining.");
                    } else {
                        System.out.println("Spacewalk complete.");
                        break; // End the loop after 30 seconds
                    }
                }
    
                // Wait for the next second
                try {
                    Thread.sleep(1000); // 1-second intervals
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
            // Final output after the rocket's fuel runs out or the spacewalk completes
            if (currentFuel <= 0) {
                System.out.println("Fuel exhausted. Launch sequence complete.");
            }
            System.out.printf("Final speed: %.2f m/s | Final altitude: %.2f m%n", currentSpeed, currentAltitude);
        }
    
        public static void launch() {
            // Create a new instance of the Launch class and initiate the launch sequence
            Launch launch = new Launch();
            launch.initiateLaunch();
    }
}

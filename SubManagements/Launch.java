package SubManagements;

public class Launch {
    // Constants for the rocket launch parameters
    private static final double FUEL_BURN_RATE = 1;
    private static final double INITIAL_FUEL = 100.0;
    private static final double ROCKET_MASS = 500.0;
    private static final double THRUST = 20000.0;
    private static final double GRAVITY = 9.81;
    private static final double SPACEWALK_ALTITUDE = 70000.0;
    private static final double PARACHUTE_DEPLOY_ALTITUDE = 10000.0;

    double currentFuel = INITIAL_FUEL;
    private double currentSpeed = 10.0;
    private double currentAltitude = 0.0;
    private boolean parachuteDeployed = false;

    public void initiateLaunch() {
        System.out.println("Initiating countdown...\r");
        for (int i = 10; i >= 0; i--) {
            System.out.printf("T-" + i + "... \r");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Launch!\r");

        long spacewalkStartTime = 0;

        while (currentFuel > 0 && currentAltitude < SPACEWALK_ALTITUDE) {
            double acceleration = (THRUST / ROCKET_MASS) - GRAVITY;
            currentSpeed += acceleration;
            currentAltitude += currentSpeed;
            currentFuel -= FUEL_BURN_RATE;

            System.out.printf("Fuel: %.2f Lbs | Speed: %.2f m/s | Altitude: %.2f \r", currentFuel, currentSpeed, currentAltitude);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Rocket has reached 70,000 meters! Spacewalk countdown started.\r");
        spacewalkStartTime = System.nanoTime();

        while ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000 < 30) {
            long timeRemaining = 30 - ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000);
            System.out.printf("Spacewalk Timer: " + timeRemaining + " seconds remaining. \r");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Spacewalk complete. Beginning descent.\r");
        descend();
    }

    private void descend() {
        System.out.println("Rocket starting descent...\r");
    
        while (currentAltitude > 0) {
            if (!parachuteDeployed && currentAltitude <= PARACHUTE_DEPLOY_ALTITUDE) {
                parachuteDeployed = true;
                System.out.println("Parachute deployed! Rapidly slowing descent...\r");
            }
    
            if (parachuteDeployed) {
                currentSpeed *= .93; // Stronger parachute effect
            } else {
                currentSpeed -= GRAVITY * 0.7; // Deceleration in free fall
            }
    
            if (currentAltitude <= 500) {
                // Further slow down as we approach the ground
                currentSpeed *= 0.85;
            }
    
            if (currentAltitude <= 100) {
                // Gentle descent for soft landing
                currentSpeed *= 0.7;
            }
    
            if (currentSpeed < 0.5) {
                currentSpeed = 0.5; // Ensure we don't stop mid-air
            }
    
            if (currentAltitude - currentSpeed <= 0) {
                currentSpeed = currentAltitude; // Match speed to altitude for a perfect landing
            }
    
            currentAltitude -= currentSpeed;
    
            System.out.printf("Descending... Speed: %.2f m/s | Altitude: %.2f \r", currentSpeed, currentAltitude);
    
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    
        // Ensure perfect landing with zero speed
        currentSpeed = 0;
        currentAltitude = 0;
        System.out.printf("Rocket has landed safely.\r");
        System.out.printf("Final speed: %.2f m/s | Final altitude: %.2f \r", currentSpeed, currentAltitude);
    }

    public static void launch() {
        new Launch().initiateLaunch();
    }
}

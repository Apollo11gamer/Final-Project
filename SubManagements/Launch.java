package SubManagements;

public class Launch {
    // Constants for the rocket launch parameters.
    private static final double FUEL_BURN_RATE = 1; // Pounds of fuel burned per second.
    private static final double INITIAL_FUEL = 100.0;
    private static final double ROCKET_MASS = 500.0; // Mass of rocket that effects launch.
    private static final double THRUST = 20000.0; // Thrust of rocket.
    private static final double GRAVITY = 9.81; // Gravity effect that affects the spaceship in space.
    private static final double SPACEWALK_ALTITUDE = 70000.0; // The 70,000 meters needed to reach spacewalk.
    private static final double PARACHUTE_DEPLOY_ALTITUDE = 10000.0; // The 10,000 meters to descend down to, to deploy a parachute.

    private double currentFuel = INITIAL_FUEL;
    private double currentSpeed = 10.0;
    private double currentAltitude = 0.0;
    private boolean parachuteDeployed = false;

    // Initiates the launch count down from 10 to 0 seconds.
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

        // Prints the fuel(lbs), speed(m/s), and altitude(meters) of the spaceship as it launches into space.
        // Once currentAltitude is greater than SPACEWALK_ALTITUDE(70,000 meters), the spacewalk will be initiated.
        while (currentFuel > 0 && currentAltitude < SPACEWALK_ALTITUDE) {
            double acceleration = (THRUST / ROCKET_MASS) - GRAVITY;
            currentSpeed += acceleration;
            currentAltitude += currentSpeed;
            currentFuel -= FUEL_BURN_RATE;

            System.out.printf("Fuel: %.2f Lbs | Speed: %.2f m/s | Altitude: %.2f \r", currentFuel, currentSpeed, currentAltitude);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Prints the message that the spaceship has reached 70,000 meters.
        System.out.println("Rocket has reached 70,000 meters! Spacewalk countdown started.\r");
        spacewalkStartTime = System.nanoTime();

        // The process of the spacewalk starts here. A countdown goes into effect from 30 to 0 seconds.
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

        // Prints the message that the spacewalk is complete. Continues to the descend method.
        System.out.println("Spacewalk complete. Beginning descent.");
        descend();
    }

    private void descend() {
        System.out.println("Rocket starting descent...");
    
        // Prints the speed(m/s) and altitude(meters) of the spaceship as it returns back to Earth.
        // The descending process goes on until currentAltitude reaches down to PARACHUTE_DEPLOY_ALTITUDE(10,000 meters).
        while (currentAltitude > 0) {
            if (!parachuteDeployed && currentAltitude <= PARACHUTE_DEPLOY_ALTITUDE) {
                parachuteDeployed = true;
                System.out.println("\nParachute deployed! Rapidly slowing descent...\r");
            }
    
            if (parachuteDeployed) {
                currentSpeed *= .93; // Stronger parachute effect.
            } else {
                currentSpeed -= GRAVITY * 0.7; // Deceleration in free fall.
            }
    
            if (currentAltitude <= 500) {
                // Further slow down as we approach the ground.
                currentSpeed *= 0.85;
            }
    
            if (currentAltitude <= 100) {
                // Gentle descent for soft landing.
                currentSpeed *= 0.7;
            }
    
            if (currentSpeed < 0.5) {
                currentSpeed = 0.5; // Ensure we don't stop mid-air.
            }
    
            if (currentAltitude - currentSpeed <= 0) {
                currentSpeed = currentAltitude; // Match speed to altitude for a perfect landing.
            }
    
            currentAltitude -= currentSpeed;
    
            System.out.printf("Descending... Speed: %.2f m/s | Altitude: %.2f \r", currentSpeed, currentAltitude);
    
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    
        // Ensure perfect landing with zero speed.
        currentSpeed = 0;
        currentAltitude = 0;
        // Prints the message that the rocket has landed with the final speed and altitude shown alongside the message.
        System.out.printf("Rocket has landed safely. Final speed: %.2f m/s | Final altitude: %.2f", currentSpeed, currentAltitude);
    
    }

    // Launch method used to initiate the space launch.
    public static void launch() {
        new Launch().initiateLaunch();
    }
}

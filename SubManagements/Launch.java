package SubManagements;

public class Launch {
    // Constants for the rocket launch parameters
    private static final double FUEL_BURN_RATE = 1.0;
    private static final double INITIAL_FUEL = 100.0;
    private static final double ROCKET_MASS = 500.0;
    private static final double THRUST = 20000.0;
    private static final double GRAVITY = 9.81;
    private static final double SPACEWALK_ALTITUDE = 70000.0;
    private static final double PARACHUTE_DEPLOY_ALTITUDE = 10000.0;

    private double currentFuel = INITIAL_FUEL;
    private double currentSpeed = 10.0;
    private double currentAltitude = 0.0;
    private boolean parachuteDeployed = false;

    public void initiateLaunch() {
        System.out.println("Initiating countdown...");
        for (int i = 10; i >= 0; i--) {
            System.out.print("\rT-" + i + "...  ");
            System.out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("\nLaunch!");

        long spacewalkStartTime = 0;

        while (currentFuel > 0 && currentAltitude < SPACEWALK_ALTITUDE) {
            double acceleration = (THRUST / ROCKET_MASS) - GRAVITY;
            currentSpeed += acceleration;
            currentAltitude += currentSpeed;
            currentFuel = Math.max(0, currentFuel - FUEL_BURN_RATE);

            System.out.printf("\rFuel: %6.2f L | Speed: %7.2f m/s | Altitude: %8.2f m  ", 
                currentFuel, currentSpeed, currentAltitude);
            System.out.flush();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("\nRocket has reached 70,000 meters! Spacewalk countdown started.");
        spacewalkStartTime = System.nanoTime();

        while ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000 < 30) {
            long timeRemaining = 30 - ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000);
            System.out.print("\rSpacewalk Timer: " + timeRemaining + " seconds remaining.  ");
            System.out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("\nSpacewalk complete. Beginning descent.");
        descend();
    }

    private void descend() {
        System.out.println("Rocket starting descent...");

        while (currentAltitude > 0) {
            if (!parachuteDeployed && currentAltitude <= PARACHUTE_DEPLOY_ALTITUDE) {
                parachuteDeployed = true;
                System.out.println("\nParachute deployed! Rapidly slowing descent...");
            }

            if (parachuteDeployed) {
                currentSpeed *= 0.93;
            } else {
                currentSpeed -= GRAVITY * 0.7;
            }

            if (currentAltitude <= 500) {
                currentSpeed *= 0.85;
            }

            if (currentAltitude <= 100) {
                currentSpeed *= 0.7;
            }

            if (currentSpeed < 0.5) {
                currentSpeed = 0.5;
            }

            if (currentAltitude - currentSpeed <= 0) {
                currentSpeed = currentAltitude;
            }

            currentAltitude -= currentSpeed;

            System.out.printf("\rDescending... Speed: %7.2f m/s | Altitude: %8.2f m  ", 
                currentSpeed, currentAltitude);
            System.out.flush();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("\nRocket has landed safely.");
        System.out.printf("Final speed: %.2f m/s | Final altitude: %.2f m%n", currentSpeed = 0, currentAltitude);
    }

    public static void launch() {
        new Launch().initiateLaunch();
    }
}

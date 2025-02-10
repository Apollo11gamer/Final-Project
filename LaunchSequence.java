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


// This is the main method that goes through the launching sequence;
    public static void main(String[] args) throws InterruptedException {

    // This LaunchSequence object is created: launchingSequence.
    LaunchSequence launchingSequence = new LaunchSequence();
    // This launchingSequence object uses the launchCountdown() method.
    launchingSequence.beginLaunch();
    
    }


// This beginLaunch() method proceeds with the launchCountdown() and beginFlight() methods to enter the launch sequence.
    public void beginLaunch() throws InterruptedException {
    launchCountdown();
    beginFlight();
    }

// This launchCountdown() method proceeds with the launch sequence countdown.
    public void launchCountdown() throws InterruptedException {
    
    // This for loop prints the seconds of the countdown from 10 to 0.
    for (int i = 10; i >= 0; i--) {
    System.out.println(i + "...");
    // This Thread.sleep() method pauses 1 second between countdown steps.
    Thread.sleep(1000);  
    }
    // This prints the string, "LAUNCH - - > ," after the countdown is finished.
    System.out.println("LAUNCH - - > ");

    }


// This beginFlight() method proceeds with the flight of the spaceship into the atmosphere.
    public void beginFlight() throws InterruptedException {
    // 
    while (altitude < SUCCESSFUL_LAUNCH_ALTITUDE) {
    processFlight();
    }
    //
    beginSpacewalk();
    returnToEarth();

    }


// This processFlight method
    public void processFlight() throws InterruptedException {

    double fuelBurned = 2; 
    double speedIncrease = fuelBurned * 30;
    fuel -= fuelBurned;
    // Update speed and altitude based on the fuel burned
    speed += speedIncrease;
    // 1 second interval
    altitude += speed * 1;  
    System.out.println("Altitude: " + altitude + " meters, Speed: " + speed + " m/s, Fuel: " + fuel + " lbs");
    if (altitude >= SUCCESSFUL_LAUNCH_ALTITUDE && !spacewalkStarted) {
    spacewalkStarted = true;
    }
    // Simulate a brief delay by waiting 1 second per iteration to simulate time passing.
    Thread.sleep(500);

    }


// This beginSpacewalk() method
    public void beginSpacewalk() throws InterruptedException {

    if (spacewalkStarted) {
    System.out.println("Astronauts have landed. Now astronauts will proceed with a spacewalk.");
    // This Threat.sleep() method will make the spacewalk last for 30 seconds.
    Thread.sleep(30000);  
    }
    System.out.println("The spacewalk is now complete. Hurray!");

    }


// This returnToEarth() method
    public void returnToEarth() throws InterruptedException {
    
    // Ascending phase - gravity slows the rocket
    while (altitude > 10000) {
    ascendingGravityEffect();
    }
    // Descending phase - gravity accelerates the rocket
    while (altitude > 0) {
    descendingGravityEffect();
    // If speed exceeds 3000 m/s below 70,000 meters, the ship burns up
    if (altitude < SUCCESSFUL_LAUNCH_ALTITUDE && speed > RETURN_MAXIMUM_SPEED) {
    System.out.println("(!) The ship has burned up due to high speed during re-entry! (!)");
    return;
    }
    // Deploy parachutes below 10,000 meters
    if (altitude <= PARACHUTE_ALTITUDE && speed >= PARACHUTE_SPEED) {
    parachuteDeployment();
    }
    }
    // Landing
    System.out.println("The ship has landed safely. Astronauts may exit.");
    
    }


// This ascendingGravityEffect() method
    public void ascendingGravityEffect() throws InterruptedException {

    speed -= GRAVITY_EFFECT;
    // Prevent negative speed
    if (speed < 0) speed = 0;  
    altitude -= speed;
    System.out.println("Ascending - - > Altitude: " + altitude + " meters, Speed: " + speed + " m/s");
    // Simulate time passing in seconds.
    Thread.sleep(500);  
    
    }


// This descendingGravityEffect() method
    public void descendingGravityEffect() throws InterruptedException {

    speed += GRAVITY_EFFECT;
    altitude -= speed;
    System.out.println("Descending - - > Altitude: " + altitude + " meters, Speed: " + speed + " m/s");
    // Simulate time passing in seconds
    Thread.sleep(500);  

    }


// This parachuteDeployment() method
    public void parachuteDeployment() {

    // Reduce speed to no more than 7 m/s
    speed = PARACHUTE_SPEED;
    System.out.println("Parachutes deployed! Speed reduced to " + speed + " meters per second.");  

    }

}
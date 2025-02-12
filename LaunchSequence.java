public class LaunchSequence {

// Constants
    // This is the gravity effect for increasing and decreasing speed by 9.81 m/s^2 each second.
    private static final double GRAVITY_EFFECT = Math.pow(9.81, 2);
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
    boolean parachuteIsDeployed = false;


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
    
    System.out.println("Launch countdown is initiating.");
    // This for loop prints the seconds of the countdown from 10 to 0.
    for (int i = 10; i >= 0; i--) {
    System.out.print(i + "... \r");
    // This Thread.sleep() method pauses 1 second between countdown steps.
    Thread.sleep(1000);  
    }
    // This prints the string, "LAUNCH COMPLETE - - > ," after the countdown is finished.
    System.out.println("LAUNCH COMPLETE - - >");

    }


// This beginFlight() method proceeds with the flight of the spaceship into the atmosphere.
    public void beginFlight() throws InterruptedException {

    // While altitude is less than 70,000 meters, the processFlight() method will go into
    while (altitude < SUCCESSFUL_LAUNCH_ALTITUDE) {
    processFlight();
    }
    // After 70,000 meters is reached, the spacewalk for 30 seconds will commence.
    beginSpacewalk();
    // After the spacewalk, the journey back to Earth will commence.
    returnToEarth();

    }


// This processFlight method goes through the flight process.
    public void processFlight() throws InterruptedException {

    // This is the fuel burned per pound.
    double fuelBurned = 3; 
    // This is the calculation for the speed increase.
    double speedIncrease = fuelBurned * 30;
    // This subtracts the fuel burned from the fuel in the spaceship.
    fuel -= fuelBurned;
    // This updates the speed based on the fuel burned.
    speed += speedIncrease;
    // This updates the altitude based on the speed.
    altitude += speed;  
    // This prints the altitude, speed, and fuel for the flight.
    System.out.printf("Altitude: %.2f meters | Speed: %.2f m/s | Fuel: %.2f lbs \r", altitude, speed, fuel);
    // This Thread.sleep() method pauses 0.5 seconds between countdown steps.
    Thread.sleep(500);
    // This initiates the spacewalk.
    if (altitude >= SUCCESSFUL_LAUNCH_ALTITUDE && !spacewalkStarted) {
    spacewalkStarted = true;
    }
    
    }


// This beginSpacewalk() method goes through the spacewalk process.
    public void beginSpacewalk() throws InterruptedException {

    // This prints, "Astronauts have landed. Now astronauts will proceed with a spacewalk."
    if (spacewalkStarted) {
    System.out.println("Astronauts have landed. Now astronauts will proceed with a spacewalk."); 
    }
    // This for loop prints the seconds of the countdown from 30 to 0.
    for (int i = 30; i >= 0; i--) {
    System.out.print(i + "... \r");
    // This Thread.sleep() method pauses 1 second between countdown steps.
    Thread.sleep(1000);  
    }
    // This prints the string, "SPACEWALK COMPLETE - - > ," after the countdown is finished.
    System.out.println("SPACEWALK COMPLETE - - > ");

    }


// This returnToEarth() method goes through the process of returning to Earth.
    public void returnToEarth() throws InterruptedException {
    
    // This initiates the ascending phase where gravity slows the rocket.
    while (altitude > 10000) {
    ascendingGravityEffect();
    }
    // This initiates the descending phase where gravity accelerates the rocket.
    while (altitude > 0 && altitude < 10000) {
    // This then deploys the parachutes below 10,000 meters and prints the deployment statement once.
    if (altitude <= PARACHUTE_ALTITUDE && parachuteIsDeployed == false) {
    parachuteIsDeployed = true;
    parachuteDeployment();
    }    
    // If speed exceeds 3,000 m/s below 70,000 meters, the ship burns up and this message is displayed: "(!) The ship has burned up due to high speed during re-entry! (!)"
    if (altitude < SUCCESSFUL_LAUNCH_ALTITUDE && speed > RETURN_MAXIMUM_SPEED) {
    System.out.println("(!) The ship has burned up due to high speed during re-entry! (!)");
    return;
    }
    // If speed does not exceed 3,000 m/s, the spaceship goes to descending.
    else if (altitude < SUCCESSFUL_LAUNCH_ALTITUDE && speed < RETURN_MAXIMUM_SPEED) {
    descendingGravityEffect();    
    }
    
    }
    // After the launch sequence is successful and complete, this message prints: "The ship has landed safely. Astronauts may exit."
    System.out.println("\nThe ship has landed safely. Astronauts may exit.");
    
    }


// This ascendingGravityEffect() method goes through the process of ascending back to Earth.
    public void ascendingGravityEffect() throws InterruptedException {

    // This decreases the gravity effect from speed, because ascending slows down the speed.
    speed -= GRAVITY_EFFECT;
    // This prevents negative speed, but it also means that if speed reaches zero, the spaceship is stuck in space.
    if (speed < 0) {
    speed = 0;  
    }
    // This subtracts the speed from the altitude to show how the spaceship is returning to Earth's surface.
    altitude -= speed;
    // This prints the altitude, speed, and fuel for the ascending phase.
    System.out.printf("Ascending - - > Altitude: %.2f meters | Speed: %.2f m/s \r", altitude, speed);
    // This Thread.sleep() method pauses 0.5 seconds between countdown steps.
    Thread.sleep(500);  
    
    }

// This descendingGravityEffect() method goes through the process of descending back to Earth.
    public void descendingGravityEffect() throws InterruptedException {

    // Before parachute is deployed, the speed is affected by the gravity effect.
    if (parachuteIsDeployed == false) {
    // This increases the gravity effect from speed, because descending speeds up the speed.
    speed += GRAVITY_EFFECT;
    }
    // 
    if (parachuteIsDeployed == true && speed > PARACHUTE_SPEED) {
    speed -= 25;
    //
    if (speed < PARACHUTE_SPEED) {
    speed = PARACHUTE_SPEED;
    }
    }
    
    //
    altitude -= speed;
    //
    System.out.printf("Descending - - > Altitude: %.2f meters | Speed: %.2f m/s \r", altitude, speed);
    // Simulate time passing in seconds
    Thread.sleep(500);  

    }


// This parachuteDeployment() method goes through the process of displaying the parachute deployment message.
    public void parachuteDeployment() {
        
    // Reduce speed to no more than 7 m/s
    double parachuteSpeed = PARACHUTE_SPEED;
    System.out.println("Parachutes are deployed! Speed is being reduced to " + parachuteSpeed + " meters per second.");

    }

}
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class LaunchSequence {

    public static void main(String[] args) {

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
        altitude = Math.pow(speed, 2);
        System.out.println(altitude);

        // 10 Second Countdown
        kbd.nextLine();
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
    
    }


import java.util.Timer;
import java.util.TimerTask;

public class LaunchSequence {

    public static void Countdown() {

    int time = 10;
    double fuelBurned;
    double altitude;
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
    
    }

}
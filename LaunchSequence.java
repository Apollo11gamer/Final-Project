import java.util.Timer;
import java.util.TimerTask;

public class LaunchSequence {

    public static void main(String[] args) {


    // 10 Second Countdown
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


    // Flight and fuel
    double altitude;
    double fuelBurned;

    
    }


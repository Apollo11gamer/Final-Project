import java.util.Timer;
import java.util.TimerTask;

public class LaunchSequence {

    public static void main(String[] args) {


    // Countdown from 10
    int time = 10;
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
    int countDown = time;

    public void run() {
    System.out.println(countDown);

    if (countDown <= 0) {
    System.out.println("Timer is done");
    timer.cancel();
    }
    countDown--;
    }
    }, 0, 1000);


    // 30 Second timer
    int spacewalkTime = 30;
    Timer spacewalkTimer = new Timer();
    spacewalkTimer.scheduleAtFixedRate(new TimerTask() {
    int spacewalkCountdown = spacewalkTime;

    public void run() {
    System.out.println(spacewalkCountdown);

    if (spacewalkCountdown <= 0) {
    System.out.println("Spacewalk is done.");
    spacewalkTimer.cancel();
    }
    spacewalkCountdown--;
    }
    }, 0, 1000);
    

}
}
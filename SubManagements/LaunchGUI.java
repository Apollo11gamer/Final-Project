package SubManagements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchGUI {
    private JButton launchButton;
    private JLabel countdownLabel, statusLabel, fuelLabel, speedLabel, altitudeLabel;

    private double currentFuel = 1000.0;
    private double currentSpeed = 0.0;
    private double currentAltitude = 0.0;

    private static final double THRUST = 3000.0;
    private static final double ROCKET_MASS = 500.0;
    private static final double GRAVITY = 9.81;
    private static final double FUEL_BURN_RATE = 5.0;
    private static final double SPACEWALK_ALTITUDE = 100_000;

    public LaunchGUI(JButton launchButton, JLabel countdownLabel, JLabel statusLabel, JLabel fuelLabel, JLabel speedLabel, JLabel altitudeLabel) {
        this.launchButton = launchButton;
        this.countdownLabel = countdownLabel;
        this.statusLabel = statusLabel;
        this.fuelLabel = fuelLabel;
        this.speedLabel = speedLabel;
        this.altitudeLabel = altitudeLabel;

        this.launchButton.addActionListener(e -> initiateLaunch());
    }

    public void initiateLaunch() {
        launchButton.setEnabled(false);
        countdown(10);
    }

    private void countdown(int seconds) {
        Timer timer = new Timer(1000, new ActionListener() {
            int count = seconds;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= 0) {
                    countdownLabel.setText("T-" + count + " seconds...");
                    count--;
                } else {
                    ((Timer) e.getSource()).stop();
                    beginLaunch();
                }
            }
        });
        timer.start();
    }

    private void beginLaunch() {
        countdownLabel.setText("Launch!");
        statusLabel.setText("Rocket is ascending...");
        playSound("SubManagements/Accend.mp3");

        Timer ascentTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFuel > 0 && currentAltitude < SPACEWALK_ALTITUDE) {
                    double acceleration = (THRUST / ROCKET_MASS) - GRAVITY;
                    currentSpeed += acceleration;
                    currentAltitude += currentSpeed;
                    currentFuel -= FUEL_BURN_RATE;

                    fuelLabel.setText("Fuel: " + String.format("%.2f", currentFuel) + " lbs");
                    speedLabel.setText("Speed: " + String.format("%.2f", currentSpeed) + " m/s");
                    altitudeLabel.setText("Altitude: " + String.format("%.2f", currentAltitude) + " m");
                } else {
                    ((Timer) e.getSource()).stop();
                    stopSound();
                    startSpacewalk();
                }
            }
        });
        ascentTimer.start();
    }

    private void startSpacewalk() {
        statusLabel.setText("Spacewalk started! 30 sec remaining...");
        playSound("SubManagements/InSpace.mp3");

        Timer spacewalkTimer = new Timer(1000, new ActionListener() {
            int count = 30;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    countdownLabel.setText("Spacewalk: " + count + " sec left");
                    count--;
                } else {
                    ((Timer) e.getSource()).stop();
                    stopSound();
                    statusLabel.setText("Spacewalk complete. Beginning descent.");
                    descend();
                }
            }
        });
        spacewalkTimer.start();
    }

    private void descend() {
        statusLabel.setText("Descent initiated...");
        // Implement descent logic here
    }

    private void playSound(String filePath) {
        // Implement sound playback logic
    }

    private void stopSound() {
        // Implement sound stopping logic
    }
}

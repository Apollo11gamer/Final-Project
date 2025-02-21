package SubManagements;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import javax.swing.*;

public class LaunchGUI {
    private JFrame frame;
    private JLabel countdownLabel, fuelLabel, speedLabel, altitudeLabel, statusLabel;
    private JButton launchButton;
    
    private static final double FUEL_BURN_RATE = 1;
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
    
    private Clip clip;  // For audio playback

    public LaunchGUI() {
        frame = new JFrame("Rocket Launch Simulator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        countdownLabel = new JLabel("Press 'Launch' to start countdown", SwingConstants.CENTER);
        fuelLabel = new JLabel("Fuel: " + currentFuel + " lbs", SwingConstants.CENTER);
        speedLabel = new JLabel("Speed: " + currentSpeed + " m/s", SwingConstants.CENTER);
        altitudeLabel = new JLabel("Altitude: " + currentAltitude + " m", SwingConstants.CENTER);
        statusLabel = new JLabel("Status: Awaiting launch", SwingConstants.CENTER);

        launchButton = new JButton("Launch");
        launchButton.addActionListener(e -> new Thread(this::initiateLaunch).start());

        frame.add(countdownLabel);
        frame.add(fuelLabel);
        frame.add(speedLabel);
        frame.add(altitudeLabel);
        frame.add(statusLabel);
        frame.add(launchButton);

        frame.setVisible(true);
    }

    public void initiateLaunch() {
        launchButton.setEnabled(false);
        
        for (int i = 10; i >= 0; i--) {
            countdownLabel.setText("T-" + i + " seconds...");
            sleep(1000);
        }

        countdownLabel.setText("Launch!");
        statusLabel.setText("Rocket is ascending...");

        playSound("Music/Accend.mp3"); // Start playing ascent sound

        long spacewalkStartTime = 0;

        while (currentFuel > 0 && currentAltitude < SPACEWALK_ALTITUDE) {
            double acceleration = (THRUST / ROCKET_MASS) - GRAVITY;
            currentSpeed += acceleration;
            currentAltitude += currentSpeed;
            currentFuel -= FUEL_BURN_RATE;

            fuelLabel.setText("Fuel: " + String.format("%.2f", currentFuel) + " lbs");
            speedLabel.setText("Speed: " + String.format("%.2f", currentSpeed) + " m/s");
            altitudeLabel.setText("Altitude: " + String.format("%.2f", currentAltitude) + " m");

            sleep(500);
        }

        stopSound(); // Stop playing sound once the ascent ends

        statusLabel.setText("Spacewalk started! 30 sec remaining...");
        spacewalkStartTime = System.nanoTime();

        while ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000 < 30) {
            long timeRemaining = 30 - ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000);
            countdownLabel.setText("Spacewalk: " + timeRemaining + " sec left");
            sleep(1000);
        }

        statusLabel.setText("Spacewalk complete. Beginning descent.");
        descend();
    }

    private void descend() {
        statusLabel.setText("Rocket descending...");

        while (currentAltitude > 0) {
            if (!parachuteDeployed && currentAltitude <= PARACHUTE_DEPLOY_ALTITUDE) {
                parachuteDeployed = true;
                statusLabel.setText("Parachute deployed! Slowing descent...");
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

            speedLabel.setText("Speed: " + String.format("%.2f", currentSpeed) + " m/s");
            altitudeLabel.setText("Altitude: " + String.format("%.2f", currentAltitude) + " m");

            sleep(500);
        }

        currentSpeed = 0;
        currentAltitude = 0;
        statusLabel.setText("Rocket has landed safely.");
    }

    private void playSound(String filePath) {
        InputStream audioSrc = getClass().getResourceAsStream("/SubManagements/Accend.wav");
        stopSound(); // Ensure any previous clip is stopped before playing new sound

        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }

            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile)) {
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Play sound continuously
                clip.start();
                // Close the AudioInputStream to release resources
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopSound() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void launch() {
        SwingUtilities.invokeLater(LaunchGUI::new);
    }
}

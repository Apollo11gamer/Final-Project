package LaunchControl;

import java.awt.*;
import javax.swing.*;

public class LaunchGUI {
    private static final double SPACEWALK_ALTITUDE = 70000;
    private static final double THRUST = 5386220;
    private static final double ROCKET_MASS = 3;
    private static final double GRAVITY = 9.81;
    private static final double FUEL_BURN_RATE = 3000;
    private static final double TIME_STEP = 1; // Simulation time step in seconds

    private double currentFuel = 500000;
    private double currentSpeed = 0;
    private double currentAltitude = 0;
    private boolean parachutesDeployed = false;

    private final JFrame frame;
    private final JButton launchButton;
    private final JLabel countdownLabel;
    private final JLabel statusLabel;
    private final JLabel fuelLabel;
    private final JLabel speedLabel;
    private final JLabel altitudeLabel;

    public LaunchGUI() {
        frame = new JFrame("BAISD Astronaut Launch Panel");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));
        frame.getContentPane().setBackground(Color.BLACK);

        countdownLabel = new JLabel("Pre-launch Checks in Progress...");
        statusLabel = new JLabel("Awaiting launch...");
        fuelLabel = new JLabel("Fuel: " + currentFuel + " kg");
        speedLabel = new JLabel("Speed: " + currentSpeed + " m/s");
        altitudeLabel = new JLabel("Altitude: " + currentAltitude + " m");

        launchButton = new JButton("Launch");
        launchButton.addActionListener(_ -> initiateLaunch());

        Color textColor = Color.GREEN;
        for (JLabel label : new JLabel[]{countdownLabel, statusLabel, fuelLabel, speedLabel, altitudeLabel}) {
            label.setForeground(textColor);
            label.setOpaque(true);
            label.setBackground(Color.BLACK);
        }

        launchButton.setForeground(textColor);
        launchButton.setBackground(Color.DARK_GRAY);

        frame.add(countdownLabel);
        frame.add(statusLabel);
        frame.add(fuelLabel);
        frame.add(speedLabel);
        frame.add(altitudeLabel);
        frame.add(launchButton);

        frame.setVisible(true);
    }

    public void initiateLaunch() {
        launchButton.setEnabled(false);
        SwingWorker<Void, String[]> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                preLaunchSequence();
                publish(new String[]{"Liftoff! Rocket is ascending...", "", "", ""});

                // Play launch sound in a separate thread
                AudioPlayer launchSound = new AudioPlayer();
                new Thread(launchSound::sound).start();

                while (currentFuel > 0 || currentSpeed > 0) { 
                    double totalMass = ROCKET_MASS + currentFuel;
                    double weight = totalMass * GRAVITY;
                    double netForce = THRUST - weight;  
                    double acceleration = netForce / totalMass; 
                
                    if (acceleration <= 0 && currentSpeed <= 0) {
                        publish(new String[]{"Thrust Insufficient! Aborting Launch...", "", "", ""});
                        return null;
                    }
                
                    currentSpeed = Math.max(0, currentSpeed + acceleration * TIME_STEP);
                    currentAltitude = Math.max(0, currentAltitude + currentSpeed * TIME_STEP);
                    currentFuel = Math.max(0, currentFuel - FUEL_BURN_RATE * TIME_STEP);
                
                    if (currentAltitude >= SPACEWALK_ALTITUDE) {
                        publish(new String[]{"Altitude reached " + SPACEWALK_ALTITUDE + " m. Preparing for spacewalk...", "", "", ""});
                        
                        launchSound.stop(); // 🛑 Stop the launch sound immediately
                
                        performSpacewalk();
                        break;
                    }
                
                    publish(new String[]{
                        "Liftoff! Rocket is ascending...",
                        "Fuel: " + String.format("%.2f", currentFuel) + " kg",
                        "Speed: " + String.format("%.2f", currentSpeed) + " m/s",
                        "Altitude: " + String.format("%.2f", currentAltitude) + " m"
                    });
                
                    Thread.sleep((long) (TIME_STEP * 1000));
                }

                // Start descent
                descend();
                return null;
            }
            @Override
            protected void process(java.util.List<String[]> chunks) {
                for (String[] data : chunks) {
                    SwingUtilities.invokeLater(() -> {
                        if (data[0] != null && !data[0].isEmpty()) countdownLabel.setText(data[0]);
                        if (data[1] != null && !data[1].isEmpty()) fuelLabel.setText(data[1]);
                        if (data[2] != null && !data[2].isEmpty()) speedLabel.setText(data[2]);
                        if (data[3] != null && !data[3].isEmpty()) altitudeLabel.setText(data[3]);
                    });
                }
            }
        };
        worker.execute();
    }

    private void performSpacewalk() throws InterruptedException {
        publish(new String[]{"Spacewalk in Progress...", "Time Remaining: 30s", "", ""});
        
        // Play spacewalk music in a separate thread
        MusicPlayer spacewalk = new MusicPlayer();
        spacewalk.sound("Music copy/Spaceflight Simulator - Cosmic Ocean (Official Soundtrack).wav");

        for (int i = 30; i > 0; i--) {
            publish(new String[]{"Spacewalk in Progress...", "Time Remaining: " + i + "s", "", ""});
            Thread.sleep(1000);
        }

        spacewalk.stop();
        publish(new String[]{"Re-entry Initiating...", "", "", ""});
        
        // Ensure UI updates before descent starts
        Thread.sleep(2000);
    }

    private void publish(String[] data) {
        SwingUtilities.invokeLater(() -> {
            if (data[0] != null && !data[0].isEmpty()) countdownLabel.setText(data[0]);
            if (data[1] != null && !data[1].isEmpty()) fuelLabel.setText(data[1]);
            if (data[2] != null && !data[2].isEmpty()) speedLabel.setText(data[2]);
            if (data[3] != null && !data[3].isEmpty()) altitudeLabel.setText(data[3]);
        });
    }

    private void preLaunchSequence() throws InterruptedException {
        String[] countdownSteps = {
            "T-30: Engine Chilldown", "T-15: Final System Check", "T-10: Ignition Sequence Start"
        };
        for (String step : countdownSteps) {
            SwingUtilities.invokeLater(() -> countdownLabel.setText(step));
            Thread.sleep(3000);
        }
        for (int i = 9; i >= 0; i--) {
            int countdownValue = i;
            SwingUtilities.invokeLater(() -> countdownLabel.setText("T-" + countdownValue + " seconds..."));
            Thread.sleep(1000);
        }
    }

    private void descend() {
        System.out.println("Descending initiated..."); // Debugging
    
        SwingUtilities.invokeLater(() -> statusLabel.setText("Rocket is descending..."));
        Rentry reentrySound = new Rentry();
        reentrySound.sound("Music/Spaceflight Simulator - Tiny Planet (Official Soundtrack).wav");
    
        while (currentAltitude > 0) {
            currentSpeed += 9.81 * TIME_STEP; // Gravity pulls downward
            currentAltitude -= currentSpeed * TIME_STEP; // Update altitude
    
            // Deploy parachutes if below 5000m
            if (!parachutesDeployed && currentAltitude <= 10000) {
                parachutesDeployed = true;
                System.out.println("Parachutes deployed!"); // Debugging
            }
    
            // If parachutes are deployed, progressively slow down the descent
            if (parachutesDeployed) {
                currentSpeed *= 0.95; // Gradual deceleration
                if (currentSpeed < 5) {
                    currentSpeed = 5; // Set a low terminal velocity
                }
            }
    
            // Ensure that when altitude reaches zero, speed is also zero
            if (currentAltitude <= 10) { 
                currentSpeed *= 0.9; // Further slow down as it nears the ground
                if (currentAltitude <= 1) { // Final touch-down phase
                    currentSpeed = 0;
                    currentAltitude = 0;
                }
            }
    
            SwingUtilities.invokeLater(() -> {
                fuelLabel.setText("Fuel: " + String.format("%.2f", currentFuel) + " kg");
                speedLabel.setText("Speed: " + String.format("%.2f", currentSpeed) + " m/s");
                altitudeLabel.setText("Altitude: " + String.format("%.2f", currentAltitude) + " m");
            });
    
            sleep(500);
        }
    
        reentrySound.stop();
        SwingUtilities.invokeLater(() -> statusLabel.setText("Touchdown! Mission Success."));
    }
    
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LaunchGUI::new);
    }
}

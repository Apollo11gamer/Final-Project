import java.awt.*;
import javax.swing.*;


public class LaunchGUI {
    private static final double SPACEWALK_ALTITUDE = 400000; // Approximate ISS orbit altitude in meters
    private static final double THRUST = 7600000; // Example: Falcon 9 total thrust in Newtons
    private static final double ROCKET_MASS = 549054; // Rocket mass in kg
    private static final double GRAVITY = 9.81; // Gravity constant in m/s^2
    private static final double FUEL_BURN_RATE = 3000; // Fuel burn rate in kg/s

    private double currentFuel = 500000; // Initial fuel in kg
    private double currentSpeed = 0; // Initial speed in m/s
    private double currentAltitude = 0; // Initial altitude in meters
    private boolean stageOneActive = true; // Indicates if the first stage is active

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
        frame.getContentPane().setBackground(Color.BLACK); // Set background color
    
        countdownLabel = new JLabel("Pre-launch Checks in Progress...");
        statusLabel = new JLabel("Awaiting launch...");
        fuelLabel = new JLabel("Fuel: " + currentFuel + " kg");
        speedLabel = new JLabel("Speed: " + currentSpeed + " m/s");
        altitudeLabel = new JLabel("Altitude: " + currentAltitude + " m");
    
        launchButton = new JButton("Launch");
        launchButton.addActionListener(_ -> initiateLaunch());
    
        // Set text color and background
        Color textColor = Color.GREEN;
        countdownLabel.setForeground(textColor);
        statusLabel.setForeground(textColor);
        fuelLabel.setForeground(textColor);
        speedLabel.setForeground(textColor);
        altitudeLabel.setForeground(textColor);
        launchButton.setForeground(textColor);
        launchButton.setBackground(Color.DARK_GRAY); // Button background
    
        // Make labels background match the frame
        countdownLabel.setOpaque(true);
        countdownLabel.setBackground(Color.BLACK);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.BLACK);
        fuelLabel.setOpaque(true);
        fuelLabel.setBackground(Color.BLACK);
        speedLabel.setOpaque(true);
        speedLabel.setBackground(Color.BLACK);
        altitudeLabel.setOpaque(true);
        altitudeLabel.setBackground(Color.BLACK);
    
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
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Pre-Launch Sequence
                publish("T-30: Engine Chilldown");
                Thread.sleep(3000);
                publish("T-15: Final System Check");
                Thread.sleep(2000);
                publish("T-10: Ignition Sequence Start");
                Thread.sleep(1000);

                for (int i = 9; i >= 0; i--) {
                    publish("T-" + i + " seconds...");
                    Thread.sleep(1000);
                }

                publish("Liftoff! Rocket is ascending...");
                AudioPlayer test = new AudioPlayer();
                test.sound();

                // Ascent Phase
                while (currentFuel > 0 && currentAltitude < SPACEWALK_ALTITUDE) {
                    double acceleration = (THRUST / ROCKET_MASS) - GRAVITY;
                    currentSpeed += acceleration;
                    currentAltitude += currentSpeed; // Integrate speed for altitude
                    currentFuel -= FUEL_BURN_RATE;

                    if (stageOneActive && currentAltitude > 70000) {
                        stageOneActive = false;
                        publish("Stage Separation. Second Stage Ignition.");
                        Thread.sleep(2000);
                    }

                    publish(
                        "Fuel: " + String.format("%.2f", currentFuel) + " kg",
                        "Speed: " + String.format("%.2f", currentSpeed) + " m/s",
                        "Altitude: " + String.format("%.2f", currentAltitude) + " m"
                    );

                    Thread.sleep(500);
                }

                stopSound();
                publish("Orbit Achieved. Spacewalk in Progress...");
                MusicPlayer Inspace = new MusicPlayer();
        Inspace.InSpace();

                Thread.sleep(30000); // Simulate time spent in orbit

                publish("Re-entry Initiating...");
                descend();
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                if (!chunks.isEmpty()) {
                    countdownLabel.setText(chunks.get(0));
                }
                if (chunks.size() > 1) {
                    fuelLabel.setText(chunks.get(1));
                    speedLabel.setText(chunks.get(2));
                    altitudeLabel.setText(chunks.get(3));
                }
            }
        };
        worker.execute();
    }

    private void descend() {
        statusLabel.setText("Rocket is descending...");
        playSound("SubManagements/ReEntry.mp3");
        while (currentAltitude > 0) {
            currentSpeed -= 9.8; // Simulate atmospheric drag
            currentAltitude -= currentSpeed;
            if (currentAltitude <= 5000) {
                playSound("SubManagements/Parachutes.mp3");
            }
            fuelLabel.setText("Fuel: " + String.format("%.2f", currentFuel) + " kg");
            speedLabel.setText("Speed: " + String.format("%.2f", currentSpeed) + " m/s");
            altitudeLabel.setText("Altitude: " + String.format("%.2f", currentAltitude) + " m");
            sleep(500);
        }
        stopSound();
        System.out.println("Touchdown! Mission Success.");
    }

    private void playSound(String filePath) {
        System.out.println("Playing sound: " + filePath);
        // You can add actual sound playing logic here
    }

    private void stopSound() {
        System.out.println("Stopping sound...");
        // You can add actual sound stopping logic here
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static void launch() {
        SwingUtilities.invokeLater(LaunchGUI::new);
        
    }

}

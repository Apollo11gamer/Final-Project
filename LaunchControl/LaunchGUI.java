package LaunchControl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LaunchGUI {
    private static final double SPACEWALK_ALTITUDE = 70000;
    private static final double THRUST = 5386220;
    private static final double ROCKET_MASS = 3;
    private static final double GRAVITY = 9.81;
    private static final double FUEL_BURN_RATE = 3000;
    private static final double TIME_STEP = 1;
    private boolean exploded = false;  // Track if the rocket has exploded


    private double currentFuel = 500000;
    private double currentSpeed = 0;
    private double currentAltitude = 0;
    private boolean parachutesDeployed = false;

    private final JFrame frame;
    private final JButton launchButton;
    private final JLabel countdownLabel, statusLabel, fuelLabel, speedLabel, altitudeLabel;
    private final RocketPanel rocketPanel;
    private final JButton explodeButton;

    public LaunchGUI() {
        frame = new JFrame("BAISD Astronaut Launch Panel");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        JPanel controlsPanel = new JPanel(new GridLayout(6, 1));
        controlsPanel.setBackground(Color.BLACK);

        countdownLabel = createLabel("Pre-launch Checks in Progress...");
        statusLabel = createLabel("Awaiting launch...");
        fuelLabel = createLabel("Fuel: " + currentFuel + " kg");
        speedLabel = createLabel("Speed: " + currentSpeed + " m/s");
        altitudeLabel = createLabel("Altitude: " + currentAltitude + " m");

        launchButton = new JButton("Launch");
        launchButton.setPreferredSize(new Dimension(0, 50));
        launchButton.setForeground(Color.GREEN);
        launchButton.setBackground(Color.DARK_GRAY);
        launchButton.addActionListener(_ -> initiateLaunch());
        
        explodeButton = new JButton("Explode Rocket");
        explodeButton.setPreferredSize(new Dimension(0, 50));
        explodeButton.setForeground(Color.RED);
        explodeButton.setBackground(Color.DARK_GRAY);
        explodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                triggerExplosion();
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(launchButton, BorderLayout.CENTER);
        controlsPanel.add(buttonPanel);
        controlsPanel.add(explodeButton);

        controlsPanel.add(countdownLabel);
        controlsPanel.add(statusLabel);
        controlsPanel.add(fuelLabel);
        controlsPanel.add(speedLabel);
        controlsPanel.add(altitudeLabel);

        rocketPanel = new RocketPanel();
        frame.add(controlsPanel, BorderLayout.WEST);
        frame.add(rocketPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    

    private void triggerExplosion() {
        // Simulate explosion by updating the rocket state
        updateStatus("Rocket Exploded!");
        ToBeContinued TBC = new ToBeContinued();
        TBC.sound("LaunchControl/Music copy/To Be Continued - Sound Effect [Perfect Cut].wav");
        exploded = true;
        launchButton.setEnabled(false);
        explodeButton.setEnabled(false);
        rocketPanel.explodeRocket();  // Call the method to simulate explosion
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.GREEN);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        return label;
    }

    public void initiateLaunch() {
        launchButton.setEnabled(false);
        AudioPlayer Launch = new AudioPlayer();
        Launch.sound();
        ToBeContinued TBC = new ToBeContinued();
        BigBoom boom = new BigBoom();
        
        new Thread(() -> {
            try {
                // Random chance for launch failure (30% chance)
                if (Math.random() < 0.9) {
                    updateStatus("Launch aborted due to technical failure!");
                    boom.sound("LaunchControl/Music copy/Fart with reverb sound effect.wav");
                    Launch.stop();
                    TBC.sound("LaunchControl/Music copy/To Be Continued - Sound Effect [Perfect Cut].wav");
                    return;
                }
        
                preLaunchSequence();
                updateStatus("Liftoff! Rocket is ascending...");
                while (currentFuel > 0 || currentSpeed > 0) {
                    if (exploded) {
                        // Stop the launch if explosion has been triggered
                        updateStatus("Launch stopped due to explosion!");
                        Launch.stop();
                        return;  // Exit the launch thread
                    }
        
                    double totalMass = ROCKET_MASS + currentFuel;
                    double weight = totalMass * GRAVITY;
                    double netForce = THRUST - weight;
                    double acceleration = netForce / totalMass;
        
                    if (acceleration <= 0 && currentSpeed <= 0) {
                        updateStatus("Thrust Insufficient! Aborting Launch...");
                        return;
                    }
        
                    currentSpeed += acceleration * TIME_STEP;
                    currentAltitude += currentSpeed * TIME_STEP;
                    currentFuel = Math.max(0, currentFuel - FUEL_BURN_RATE * TIME_STEP);
        
                    updateLabels();
                    rocketPanel.setRocketAltitude(currentAltitude);
                    Thread.sleep((long) (TIME_STEP * 1000));
        
                    if (currentAltitude >= SPACEWALK_ALTITUDE) {
                        updateStatus("Altitude reached " + SPACEWALK_ALTITUDE + " m. Spacewalk in progress...");
                        Launch.stop();
                        performSpacewalk();
                        break;
                    }
                }
                descend();
            } catch (InterruptedException ignored) {}
        }).start();
    }
    
    

    private void performSpacewalk() throws InterruptedException {
        MusicPlayer space = new MusicPlayer();
        space.sound("Musc copy/Spaceflight Simulator - Cosmic Ocean (Official Soundtrack).wav");
        for (int i = 30; i > 0; i--) {
            updateStatus("Spacewalk in Progress... Time Remaining: " + i + "s");
            Thread.sleep(1000);
        }
        
        updateStatus("Re-entry Initiating...");
        space.stop();
        Thread.sleep(2000);
    }

    private void descend() {
        Rentry decend = new Rentry();
        decend.sound("Music copy/Spaceflight Simulator - Tiny Planet (Official Soundtrack).wav");
        updateStatus("Rocket is descending...");
        while (currentAltitude > 0) {
            currentSpeed = Math.max(0, currentSpeed - GRAVITY * TIME_STEP);
            currentAltitude = Math.max(0, currentAltitude - currentSpeed * TIME_STEP);

            if (currentAltitude <= 10000 && !parachutesDeployed) {
                parachutesDeployed = true;
                updateStatus("Parachutes Deployed!");
            }
            if (parachutesDeployed) {
                currentSpeed *= 0.87;
            }
            updateLabels();
            rocketPanel.setRocketAltitude(currentAltitude);
            sleep(500);
        }
        updateStatus("Touchdown! Mission Success.");
        decend.stop();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    private void preLaunchSequence() throws InterruptedException {
        String[] countdownSteps = {
            "T-30: Engine Chilldown", "T-15 Final System Check", "Launch sequence initated", "Launching in..."
        };
        for (String step : countdownSteps) {
            updateStatus(step);
            Thread.sleep(1000);
        }
        for (int i = 10; i >= 0; i--) {
            updateStatus("T-" + i + " seconds...");
            Thread.sleep(1000);
        }
    }

    private void updateLabels() {
        SwingUtilities.invokeLater(() -> {
            fuelLabel.setText("Fuel: " + String.format("%.2f", currentFuel) + " kg");
            speedLabel.setText("Speed: " + String.format("%.2f", currentSpeed) + " m/s");
            altitudeLabel.setText("Altitude: " + String.format("%.2f", currentAltitude) + " m");
        });
    }

    private void updateStatus(String text) {
        SwingUtilities.invokeLater(() -> statusLabel.setText(text));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LaunchGUI::new);
    }
}


    // Inner class for Rocket Animation with Realistic Look
    // Inner class for Rocket Animation with Realistic Look
class RocketPanel extends JPanel {
    private static final int PLATFORM_Y = 400;  // Platform altitude (Y position) on the screen
    private double astronautX = 180;  // Starting X position of the astronaut
    private double astronautY = 200;  // Starting Y position of the astronaut
    private double astronautXSpeed = 1.0;  // Horizontal movement speed
    private double astronautYSpeed = -0.5;  // Vertical movement speed

    private int rocketY = PLATFORM_Y;  // Rocket starts at the platform level (before launch)
    private double altitude = 0;  // Current altitude
    private double SPACEWALK_ALTITUDE = 70000;
    private boolean isRocketLaunched = false;  // Track if the rocket has launched
    private boolean isRocketExploded = false;

    public RocketPanel() {
        setPreferredSize(new Dimension(400, 500));
        setBackground(Color.BLACK);
    }

    public void setRocketAltitude(double altitude) {
        if (altitude < 0) {
            this.rocketY = PLATFORM_Y;  // Rocket stays at the platform level before launch
        } else {
            this.rocketY = 300 - (int) (altitude / 250);  // Update rocket's altitude after launch
        }
        this.altitude = altitude;

        // Once the rocket reaches the launch point, mark it as launched
        if (!isRocketLaunched && altitude > 0) {
            isRocketLaunched = true;  // The rocket has been launched
        }
        repaint();
    }

    public void explodeRocket() {
        isRocketExploded = true;
        repaint();  // Trigger an immediate repaint to show the explosion
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw dynamic background based on altitude
        drawBackground(g2d);

        // Draw platform only before launch (if the rocket hasn't launched)
        if (!isRocketLaunched) {
            drawPlatform(g2d);  // Platform is drawn before launch
        }

        // Rocket Body (Gradient for Metallic Look)
        GradientPaint rocketBody = new GradientPaint(150, rocketY, Color.DARK_GRAY, 170, rocketY + 80, Color.LIGHT_GRAY);
        g2d.setPaint(rocketBody);
        g2d.fillRoundRect(150, rocketY, 40, 100, 20, 20);

        // Window
        g2d.setColor(Color.CYAN);  // Window color
        g2d.fillOval(165, rocketY + 20, 15, 15);  // Adding a window to the rocket

        // Fins (move the fins down to be under the rocket body)
        g2d.setColor(Color.GRAY);
        g2d.fillPolygon(new int[]{150, 140, 160}, new int[]{rocketY + 90, rocketY + 120, rocketY + 120}, 3);
        g2d.fillPolygon(new int[]{190, 200, 180}, new int[]{rocketY + 90, rocketY + 120, rocketY + 120}, 3);

        // Nose Cone
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillPolygon(new int[]{150, 170, 190}, new int[]{rocketY, rocketY - 30, rocketY}, 3);

        // Flames (Always visible)
        drawFlames(g2d);

        // If spacewalk is happening, draw and animate the astronaut
        if (altitude >= SPACEWALK_ALTITUDE) {
            // Update astronaut position to simulate movement
            moveAstronaut();

            // Draw astronaut next to the rocket
            drawAstronaut(g2d, (int) astronautY);
        }
        if (!isRocketExploded) {
            // Normal rocket animation
            if (!isRocketLaunched) {
                drawPlatform(g2d);
            }

            // Draw Rocket Body
            g2d.setPaint(rocketBody);
            g2d.fillRoundRect(150, rocketY, 40, 100, 20, 20);

            // Rocket Fins
            g2d.setColor(Color.GRAY);
            g2d.fillPolygon(new int[]{150, 140, 160}, new int[]{rocketY + 90, rocketY + 120, rocketY + 120}, 3);
            g2d.fillPolygon(new int[]{190, 200, 180}, new int[]{rocketY + 90, rocketY + 120, rocketY + 120}, 3);

            // Rocket Nose Cone
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillPolygon(new int[]{150, 170, 190}, new int[]{rocketY, rocketY - 30, rocketY}, 3);

            // Rocket Flames
            drawFlames(g2d);
        } else {
            // Explosion animation when rocket explodes
            drawExplosion(g2d);
        }

        // Repaint continuously to animate the astronaut
        repaint();  // This ensures that the astronaut keeps moving by continuously repainting the panel
    }

    private void drawExplosion(Graphics2D g2d) {
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(150, rocketY, 100, 100); // Explosion effect

        g2d.setColor(Color.RED);
        g2d.fillOval(170, rocketY + 10, 60, 60); // Inner explosion

        g2d.setColor(Color.YELLOW);
        g2d.fillOval(190, rocketY + 20, 40, 40); // Core explosion

        // Optionally add fireballs or other effects
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(150, rocketY - 10, 50, 50);
    }

    private void moveAstronaut() {
        // Move the astronaut horizontally and vertically
        astronautX += astronautXSpeed;
        astronautY += astronautYSpeed;

        // Add some random variation to simulate floating
        astronautXSpeed += (Math.random() - 0.5) * 0.1;  // Small random change in horizontal speed
        astronautYSpeed += (Math.random() - 0.5) * 0.05;  // Small random change in vertical speed

        // Prevent the astronaut from moving out of bounds horizontally
        if (astronautX < 50) {
            astronautX = 50;
            astronautXSpeed = Math.abs(astronautXSpeed);  // Reverse direction if out of bounds
        } else if (astronautX > getWidth() - 50) {
            astronautX = getWidth() - 50;
            astronautXSpeed = -Math.abs(astronautXSpeed);  // Reverse direction if out of bounds
        }

        // Prevent the astronaut from moving out of bounds vertically
        if (astronautY < 50) {
            astronautY = 50;
            astronautYSpeed = Math.abs(astronautYSpeed);  // Reverse direction if out of bounds
        } else if (astronautY > getHeight() - 50) {
            astronautY = getHeight() - 50;
            astronautYSpeed = -Math.abs(astronautYSpeed);  // Reverse direction if out of bounds
        }
    }

    private void drawAstronaut(Graphics2D g2d, int startY) {
        // Astronaut's head (circle)
        g2d.setColor(Color.WHITE);  // Head color
        g2d.fillOval((int) astronautX - 10, startY, 20, 20);  // Draw a small circle for the head

        // Astronaut's body (rectangle)
        g2d.setColor(Color.WHITE);  // Body color
        g2d.fillRect((int) astronautX - 13, startY + 20, 26, 40);  // Rectangle for the body

        // Arms (lines)
        g2d.setColor(Color.WHITE);  // Arm color
        g2d.drawLine((int) astronautX - 13, startY + 30, (int) astronautX - 30, startY + 50);  // Left arm
        g2d.drawLine((int) astronautX + 13, startY + 30, (int) astronautX + 30, startY + 50);  // Right arm

        // Legs (lines)
        g2d.setColor(Color.WHITE);  // Leg color
        g2d.drawLine((int) astronautX - 5, startY + 60, (int) astronautX - 10, startY + 85);  // Left leg
        g2d.drawLine((int) astronautX + 5, startY + 60, (int) astronautX + 10, startY + 85);  // Right leg
    }

    private void drawPlatform(Graphics2D g2d) {
        // Draw the platform (this will be drawn only before the rocket is launched)
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, PLATFORM_Y, getWidth(), 20);  // Draw a platform at the bottom of the screen
    }

    private void drawBackground(Graphics2D g2d) {
        if (altitude < 5000) {
            // Earth Sky: Blue gradient with clouds
            g2d.setPaint(new GradientPaint(0, 0, Color.CYAN, 0, getHeight(), Color.BLUE));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            drawClouds(g2d);
        } else if (altitude < 30000) {
            // Transition: Darker blue sky
            g2d.setPaint(new GradientPaint(0, 0, Color.BLUE, 0, getHeight(), Color.DARK_GRAY));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else {
            // Space: Black with stars
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            drawStars(g2d);
        }
    }

    private void drawClouds(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(50, 100, 60, 30);
        g2d.fillOval(100, 120, 80, 40);
        g2d.fillOval(200, 80, 70, 35);
    }

    private void drawStars(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 50; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillOval(x, y, 2, 2);
        }
    }

    private void drawFlames(Graphics2D g2d) {
        int flameBaseX = 165, flameBaseY = rocketY + 100;

        Color[] flameColors = {Color.YELLOW, Color.ORANGE, Color.RED};
        int[][] flameSizes = {{15, 30}, {20, 35}, {10, 25}};

        for (int i = 0; i < flameColors.length; i++) {
            g2d.setColor(flameColors[i]);
            g2d.fillOval(flameBaseX - flameSizes[i][0] / 2, flameBaseY, flameSizes[i][0], flameSizes[i][1]);
        }
    }

}

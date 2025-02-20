import SubManagements.AstronautManagementGUI;
import SubManagements.DOBDetectorGUI;
import SubManagements.LaunchGUI;
import SubManagements.SpaceManageGUI;
import java.awt.*;
import javax.swing.*;
import password.PasswordGUI;

public class EntryPointGUI {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("BAISD Astronaut Control Panel");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the components (buttons)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1)); // Changed to 6 rows since there's no title inside it

        // Add a title label
        JLabel titleLabel = new JLabel("🚀 Welcome to BAISD Astronaut Control Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Add a panel for the title label
        frame.add(titleLabel, BorderLayout.NORTH);

        // Add buttons for each operation
        JButton passwordButton = new JButton("Verify Password");
        passwordButton.addActionListener(e -> PasswordGUI.pass());

        JButton dobButton = new JButton("Verify DOB");
        dobButton.addActionListener(e -> DOBDetectorGUI.DOB());

        JButton astronautButton = new JButton("Manage Astronaut");
        astronautButton.addActionListener(e -> AstronautManagementGUI.Space());

        JButton spaceshipButton = new JButton("Manage Spaceship");
        spaceshipButton.addActionListener(e -> SpaceManageGUI.ship());

        JButton launchButton = new JButton("Launch Mission");
        launchButton.addActionListener(e -> LaunchGUI.launch());

        JButton exitButton = new JButton("Exit Program");
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        panel.add(passwordButton);
        panel.add(dobButton);
        panel.add(astronautButton);
        panel.add(spaceshipButton);
        panel.add(launchButton);
        panel.add(exitButton);

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}

package SubManagements;

import javax.swing.; import java.awt.; import java.awt.event.ActionEvent; import java.awt.event.ActionListener; import java.util.Random;

public class SpaceManageGUI { private JFrame frame; private JTextField astronautCountField; private JButton nextButton;

public SpaceManageGUI() {
    frame = new JFrame("Astronaut Management");
    frame.setSize(300, 150);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridLayout(3, 1));
    
    JPanel inputPanel = new JPanel();
    inputPanel.add(new JLabel("Number of Astronauts:"));
    astronautCountField = new JTextField(5);
    inputPanel.add(astronautCountField);
    frame.add(inputPanel);
    
    nextButton = new JButton("Next");
    nextButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int count = Integer.parseInt(astronautCountField.getText());
                if (count > 0) {
                    new AstronautDetailsGUI(count);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    frame.add(nextButton);
    
    frame.setVisible(true);
}

public static void ship() {
    SwingUtilities.invokeLater(SpaceManageGUI::new);
}

}

class AstronautDetailsGUI { private JFrame frame; private JTextField[] kinFields, weightFields, payRateFields, statusFields; private int[] serialNumbers; private int astronautCount; private Random random;

public AstronautDetailsGUI(int count) {
    this.astronautCount = count;
    this.random = new Random();
    serialNumbers = new int[count];
    kinFields = new JTextField[count];
    weightFields = new JTextField[count];
    payRateFields = new JTextField[count];
    statusFields = new JTextField[count];

    frame = new JFrame("Astronaut Details");
    frame.setSize(400, (count * 50) + 100);
    frame.setLayout(new GridLayout(count + 1, 5));

    frame.add(new JLabel("Serial"));
    frame.add(new JLabel("Next of Kin"));
    frame.add(new JLabel("Weight (kg)"));
    frame.add(new JLabel("Pay Rate ($/hr)"));
    frame.add(new JLabel("Status"));

    for (int i = 0; i < count; i++) {
        serialNumbers[i] = random.nextInt(100000);
        frame.add(new JLabel(String.valueOf(serialNumbers[i])));

        kinFields[i] = new JTextField(10);
        frame.add(kinFields[i]);

        weightFields[i] = new JTextField(5);
        frame.add(weightFields[i]);

        payRateFields[i] = new JTextField(5);
        frame.add(payRateFields[i]);

        statusFields[i] = new JTextField(10);
        frame.add(statusFields[i]);
    }

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder astronautData = new StringBuilder("Astronaut Information:\n");
            for (int i = 0; i < astronautCount; i++) {
                astronautData.append("Serial: ").append(serialNumbers[i]).append(", ")
                        .append("Next of Kin: ").append(kinFields[i].getText()).append(", ")
                        .append("Weight: ").append(weightFields[i].getText()).append(" kg, ")
                        .append("Pay Rate: $").append(payRateFields[i].getText()).append("/hr, ")
                        .append("Status: ").append(statusFields[i].getText()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, astronautData.toString(), "Astronaut Details", JOptionPane.INFORMATION_MESSAGE);
        }
    });
    frame.add(submitButton);

    frame.setVisible(true);
}

}


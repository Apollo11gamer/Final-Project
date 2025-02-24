package SubManagements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AstronautDetailsGUI {

    private JFrame frame;
    private JTextField[] kinFields, weightFields, payRateFields, statusFields;
    private int[] serialNumbers;
    private int astronautCount;
    private Random random;

    public AstronautDetailsGUI(int count) {
        this.astronautCount = count;
        this.random = new Random();
        serialNumbers = new int[count];
        kinFields = new JTextField[count];
        weightFields = new JTextField[count];
        payRateFields = new JTextField[count];
        statusFields = new JTextField[count];

        frame = new JFrame("Astronaut Details");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel for Input Fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(count + 1, 5, 5, 5));

        panel.add(new JLabel("Serial"));
        panel.add(new JLabel("Next of Kin"));
        panel.add(new JLabel("Weight (kg)"));
        panel.add(new JLabel("Pay Rate ($/hr)"));
        panel.add(new JLabel("Status"));

        for (int i = 0; i < count; i++) {
            serialNumbers[i] = random.nextInt(100000);
            panel.add(new JLabel(String.valueOf(serialNumbers[i])));

            kinFields[i] = new JTextField(10);
            panel.add(kinFields[i]);

            weightFields[i] = new JTextField(5);
            panel.add(weightFields[i]);

            payRateFields[i] = new JTextField(5);
            panel.add(payRateFields[i]);

            statusFields[i] = new JTextField(10);
            panel.add(statusFields[i]);
        }

        // Button to Submit
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

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(submitButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

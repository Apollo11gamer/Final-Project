package SubManagements;

import LaunchControl.LaunchGUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class AstronautDetailsGUI {

    private JFrame frame;
    private JTextField[] nameFields, kinFields, weightFields, payRateFields;
    private int[] serialNumbers;
    private int astronautCount;
    private Random random;
    private JButton nextButton;  // Declare the Next button here

    public AstronautDetailsGUI(int count) {
        this.astronautCount = count;
        this.random = new Random();
        serialNumbers = new int[count];
        nameFields = new JTextField[count];
        kinFields = new JTextField[count];
        weightFields = new JTextField[count];
        payRateFields = new JTextField[count];

        frame = new JFrame("Astronaut Details");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel for Input Fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(count + 1, 5, 5, 5));

        panel.add(new JLabel("Serial"));
        panel.add(new JLabel("Name of Astronaut"));
        panel.add(new JLabel("Next of Kin"));
        panel.add(new JLabel("Weight (kg)"));
        panel.add(new JLabel("Pay Rate ($/hr)"));

        for (int i = 0; i < count; i++) {
            serialNumbers[i] = random.nextInt(100000);
            panel.add(new JLabel(String.valueOf(serialNumbers[i])));

            nameFields[i] = new JTextField(10);
            panel.add(nameFields[i]);

            kinFields[i] = new JTextField(10);
            panel.add(kinFields[i]);

            weightFields[i] = new JTextField(5);
            panel.add(weightFields[i]);

            payRateFields[i] = new JTextField(5);
            panel.add(payRateFields[i]);
        }

        // Button to Submit
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder astronautData = new StringBuilder("Astronaut Information:\n");
                for (int i = 0; i < astronautCount; i++) {
                    astronautData.append("Serial: ").append(serialNumbers[i]).append(", ")
                            .append("Name: ").append(nameFields[i].getText()).append(", ")
                            .append("Next of Kin: ").append(kinFields[i].getText()).append(", ")
                            .append("Weight: ").append(weightFields[i].getText()).append(" kg, ")
                            .append("Pay Rate: $").append(payRateFields[i].getText()).append("/hr\n");
                }
                JOptionPane.showMessageDialog(frame, astronautData.toString(), "Astronaut Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Button to Proceed (initially hidden)
        nextButton = new JButton("Launch!!!");
        nextButton.setEnabled(false);  // Disable initially
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedToNextFile();
            }
        });

        // Add DocumentListener to each field to track user input
        for (int i = 0; i < astronautCount; i++) {
            nameFields[i].getDocument().addDocumentListener(new InputChangeListener());
            kinFields[i].getDocument().addDocumentListener(new InputChangeListener());
            weightFields[i].getDocument().addDocumentListener(new InputChangeListener());
            payRateFields[i].getDocument().addDocumentListener(new InputChangeListener());
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(submitButton, BorderLayout.SOUTH);
        frame.add(nextButton, BorderLayout.NORTH); // ✅ Correctly added

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void proceedToNextFile() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to proceed to the launch panel?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose(); // Close current window
            SwingUtilities.invokeLater(() -> new LaunchGUI()); // ✅ Open the LaunchGUI
        }
    }

    // This listener will enable/disable the Next button based on input validation
    private class InputChangeListener implements javax.swing.event.DocumentListener {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            validateInput();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            validateInput();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            validateInput();
        }

        private void validateInput() {
            boolean allFieldsValid = true;

            // Check all astronaut input fields
            for (int i = 0; i < astronautCount; i++) {
                if (nameFields[i].getText().isEmpty() || kinFields[i].getText().isEmpty() ||
                    weightFields[i].getText().isEmpty() || payRateFields[i].getText().isEmpty()) {
                    allFieldsValid = false;
                    break;
                }
            }

            // Enable the "Next" button if all fields are filled
            nextButton.setEnabled(allFieldsValid);
        }
    }

    public static void main(String[] args) {
    }
}

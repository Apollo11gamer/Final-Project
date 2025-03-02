package SubManagements;

import LaunchControl.LaunchGUI;
import java.awt.*;
import java.io.*;
import java.util.Random;
import javax.swing.*;

public class AstronautDetailsGUI {
    private JFrame frame;
    private JTextField[] nameFields, kinFields, weightFields, payRateFields;
    private int[] serialNumbers;
    private int astronautCount;
    private JButton submitButton, nextButton;  // Declare both buttons here
    private Random random;

    public AstronautDetailsGUI(int count) {
        this.astronautCount = count;
        this.random = new Random();

        serialNumbers = new int[count];
        nameFields = new JTextField[count];
        kinFields = new JTextField[count];
        weightFields = new JTextField[count];
        payRateFields = new JTextField[count];

        frame = new JFrame("🚀 Astronaut Details");
        frame.setSize(700, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(count + 1, 5, 5, 5));
        panel.setBackground(Color.WHITE);

        String[] headers = {"Serial", "Astronaut Name", "Next of Kin", "Weight (lbs)", "Pay Rate ($/hr)"};
        for (String header : headers) {
            JLabel label = new JLabel(header, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.BLACK);
            panel.add(label);
        }

        for (int i = 0; i < count; i++) {
            serialNumbers[i] = random.nextInt(1000) + 1;
            JLabel serialLabel = new JLabel(String.valueOf(serialNumbers[i]), SwingConstants.CENTER);
            serialLabel.setOpaque(true);
            serialLabel.setBackground(i % 2 == 0 ? new Color(44, 62, 80) : new Color(52, 73, 94));
            serialLabel.setForeground(Color.WHITE);
            serialLabel.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(serialLabel);

            nameFields[i] = new JTextField(10);
            kinFields[i] = new JTextField(10);
            weightFields[i] = new JTextField(5);
            payRateFields[i] = new JTextField(5);

            JTextField[] fields = {nameFields[i], kinFields[i], weightFields[i], payRateFields[i]};
            for (JTextField field : fields) {
                field.setBackground(i % 2 == 0 ? new Color(236, 240, 241) : new Color(189, 195, 199));
                panel.add(field);
                field.getDocument().addDocumentListener(new InputChangeListener());
            }
        }

        // Set default button styles
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 18)); // Larger font size
        UIManager.put("Button.foreground", Color.GREEN);  // White text color
        UIManager.put("Button.background", new Color(46, 204, 113)); // Green background for Submit button
        UIManager.put("Button.focus", Color.GREEN); // Focus color when the button is clicked

        submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(150, 50));  // Increased button size for better clickability
        submitButton.addActionListener(e -> {
            saveToFile();
            JOptionPane.showMessageDialog(frame, "Astronaut Details Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        nextButton = new JButton("🚀 Launch!!!");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18)); // Larger font size
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(231, 76, 60)); // Red for urgency
        nextButton.setEnabled(false);
        nextButton.setPreferredSize(new Dimension(150, 50));  // Increased button size
        nextButton.addActionListener(e -> proceedToNextFile());

        // Hover effects for Launch button
        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(192, 57, 43));  // Darker red on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(231, 76, 60));  // Original color on exit
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("astronauts.txt", true))) {
            for (int i = 0; i < astronautCount; i++) {
                writer.write("Serial Number: " + serialNumbers[i] + ", ");
                writer.write("Name: " + nameFields[i].getText() + ", ");
                writer.write("Next of Kin: " + kinFields[i].getText() + ", ");
                writer.write("Weight: " + weightFields[i].getText() + " lbs, ");
                writer.write("Pay Rate: " + payRateFields[i].getText() + " $/hr");
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving astronaut details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void proceedToNextFile() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Proceed to Launch Panel?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose();
            SwingUtilities.invokeLater(LaunchGUI::new);
        }
    }

    private class InputChangeListener implements javax.swing.event.DocumentListener {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { validateInput(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { validateInput(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { validateInput(); }

        private void validateInput() {
            for (JTextField field : nameFields) {
                if (field.getText().trim().isEmpty()) {
                    nextButton.setEnabled(false);
                    return;
                }
            }
            nextButton.setEnabled(true);
        }
    }
}

package SubManagements;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import javax.swing.*;

public class DOBDetectorGUI {
    
    public static void DOB() {
        SwingUtilities.invokeLater(DOBDetectorGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("DOB Detector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(4, 1));
        
        JLabel promptLabel = new JLabel("Enter your Date of Birth:");
        JTextField dobField = new JTextField();
        JButton submitButton = new JButton("Submit");
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputDOB = dobField.getText().trim();
                LocalDate birthDate = parseDate(inputDOB);
                if (birthDate != null) {
                    int age = calculateAge(birthDate);
                    resultLabel.setText("Valid DOB: " + birthDate + ", Age: " + age);
                    JOptionPane.showMessageDialog(frame, "Proceeding to Phone Number Verifier...");
                    PhoneNumberVerifierGUI.phone();
                } else {
                    resultLabel.setText("Invalid Date of Birth. Try again.");
                }
            }
        });
        
        frame.add(promptLabel);
        frame.add(dobField);
        frame.add(submitButton);
        frame.add(resultLabel);
        
        frame.setVisible(true);
    }

    private static LocalDate parseDate(String dob) {
        dob = dob.replaceAll("[./]", "-");
        if (!Pattern.matches("\\d{1,4}[-]\\d{1,2}[-]\\d{1,4}", dob)) {
            return null;
        }
        String[] dateFormats = {"yyyy-MM-dd", "MM-dd-yyyy", "dd-MM-yyyy", "MM/dd/yyyy", "dd/MM/yyyy", "yyyy/MM/dd"};
        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(dob, formatter);
            } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    private static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}

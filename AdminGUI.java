import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class AdminGUI {
    private static final String ADMIN_USERNAME = "Admin";
    private static final String ADMIN_PASSWORD = "Kruskie25!";
    
    private JFrame frame;
    private JTextArea displayArea;
    private JTextField nameField, serialField, statusField;
    private Map<String, Astronaut> astronautDatabase = new HashMap<>();

    public AdminGUI() {
        authenticateAdmin();
    }

    private void authenticateAdmin() {
        String username = JOptionPane.showInputDialog("Enter Admin Username:");
        String password = JOptionPane.showInputDialog("Enter Admin Password:");

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            createGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Access Denied!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void createGUI() {
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 2));

        nameField = new JTextField();
        serialField = new JTextField();
        statusField = new JTextField();

        JButton addButton = new JButton("Add Astronaut");
        JButton removeButton = new JButton("Remove Astronaut");
        JButton editButton = new JButton("Edit Astronaut");

        addButton.addActionListener(e -> addAstronaut());
        removeButton.addActionListener(e -> removeAstronaut());
        editButton.addActionListener(e -> editAstronaut());

        controlPanel.add(new JLabel("Name:"));
        controlPanel.add(nameField);
        controlPanel.add(new JLabel("Serial No:"));
        controlPanel.add(serialField);
        controlPanel.add(new JLabel("Status:"));
        controlPanel.add(statusField);
        controlPanel.add(addButton);
        controlPanel.add(removeButton);
        controlPanel.add(editButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addAstronaut() {
        String name = nameField.getText();
        String serial = serialField.getText();
        String status = statusField.getText();

        if (name.isEmpty() || serial.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        astronautDatabase.put(serial, new Astronaut(name, serial, status));
        displayData();
        clearFields();
    }

    private void removeAstronaut() {
        String serial = serialField.getText();
        if (!astronautDatabase.containsKey(serial)) {
            JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String confirmation = JOptionPane.showInputDialog("Type DELETE to confirm removal:");
        if ("DELETE".equals(confirmation)) {
            astronautDatabase.remove(serial);
            displayData();
        } else {
            JOptionPane.showMessageDialog(frame, "Deletion cancelled.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editAstronaut() {
        String serial = serialField.getText();
        if (!astronautDatabase.containsKey(serial)) {
            JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String password = JOptionPane.showInputDialog("Enter Admin Password to Edit:");
        if (!ADMIN_PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(frame, "Incorrect Password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText();
        String status = statusField.getText();

        if (name.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Astronaut astronaut = astronautDatabase.get(serial);
        astronaut.setName(name);
        astronaut.setStatus(status);
        displayData();
        clearFields();
    }

    private void displayData() {
        displayArea.setText("");
        for (Astronaut astronaut : astronautDatabase.values()) {
            displayArea.append(astronaut + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        serialField.setText("");
        statusField.setText("");
    }

    public static void Admin() {
        SwingUtilities.invokeLater(AdminGUI::new);
    }
}

class Astronaut {
    private String name;
    private String serialNumber;
    private String status;

    public Astronaut(String name, String serialNumber, String status) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Serial No: " + serialNumber + ", Status: " + status;
    }
}
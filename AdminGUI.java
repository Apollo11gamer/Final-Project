import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class AdminGUI {
    private static final String ADMIN_USERNAME = "Admin";
    private static String ADMIN_PASSWORD = "Kruskie25!";
    
    private JFrame frame;
    private JTextArea displayArea;
    private JTextField nameField;
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
        controlPanel.setLayout(new GridLayout(4, 2));

        nameField = new JTextField();

        JButton addButton = new JButton("Add Astronaut");
        JButton removeButton = new JButton("Remove Astronaut");
        JButton editButton = new JButton("Edit Astronaut");
        JButton changePasswordButton = new JButton("Change Password");

        addButton.addActionListener(e -> addAstronaut());
        removeButton.addActionListener(e -> removeAstronaut());
        editButton.addActionListener(e -> editAstronaut());
        changePasswordButton.addActionListener(e -> changePassword());

        controlPanel.add(new JLabel("Name:"));
        controlPanel.add(nameField);
        controlPanel.add(addButton);
        controlPanel.add(removeButton);
        controlPanel.add(editButton);
        controlPanel.add(changePasswordButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Show existing users when the GUI starts
        displayData();

        frame.setVisible(true);
    }

    private void addAstronaut() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name field is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        astronautDatabase.put(name, new Astronaut(name));
        displayData();
        clearFields();
    }

    private void removeAstronaut() {
        String name = nameField.getText();
        if (!astronautDatabase.containsKey(name)) {
            JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String confirmation = JOptionPane.showInputDialog("Type DELETE to confirm removal:");
        if ("DELETE".equals(confirmation)) {
            astronautDatabase.remove(name);
            displayData();
        } else {
            JOptionPane.showMessageDialog(frame, "Deletion cancelled.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editAstronaut() {
        String name = nameField.getText();
        if (!astronautDatabase.containsKey(name)) {
            JOptionPane.showMessageDialog(frame, "Astronaut not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String password = JOptionPane.showInputDialog("Enter Admin Password to Edit:");
        if (!ADMIN_PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(frame, "Incorrect Password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newName = JOptionPane.showInputDialog("Enter new name:");
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Astronaut astronaut = astronautDatabase.get(name);
        astronaut.setName(newName);
        displayData();
        clearFields();
    }

    private void changePassword() {
        String currentPassword = JOptionPane.showInputDialog("Enter Current Password:");
        if (!ADMIN_PASSWORD.equals(currentPassword)) {
            JOptionPane.showMessageDialog(frame, "Incorrect Current Password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newPassword = JOptionPane.showInputDialog("Enter New Password:");
        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "New password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ADMIN_PASSWORD = newPassword; // Update the password
        JOptionPane.showMessageDialog(frame, "Password changed successfully!");
    }

    private void displayData() {
        displayArea.setText("");
        for (Astronaut astronaut : astronautDatabase.values()) {
            displayArea.append(astronaut + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
    }

    public static void Admin() {
        SwingUtilities.invokeLater(AdminGUI::new);
    }
}

class Astronaut {
    private String name;

    public Astronaut(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }
}

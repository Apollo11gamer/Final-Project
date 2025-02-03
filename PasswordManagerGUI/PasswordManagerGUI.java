import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PasswordManagerGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI Elements
        Label siteLabel = new Label("Website:");
        TextField siteField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button saveButton = new Button("Save Password");
        Button retrieveButton = new Button("Retrieve Password");

        Label resultLabel = new Label();

        // Save Password Action
        saveButton.setOnAction(e -> {
            String site = siteField.getText();
            String password = passwordField.getText();
            try {
                PasswordStorage.savePassword(site, password);
                resultLabel.setText("✅ Password saved!");
            } catch (Exception ex) {
                resultLabel.setText("❌ Error saving password.");
            }
        });

        // Retrieve Password Action
        retrieveButton.setOnAction(e -> {
            String site = siteField.getText();
            try {
                String password = PasswordStorage.getPassword(site);
                resultLabel.setText("🔑 Password: " + password);
            } catch (Exception ex) {
                resultLabel.setText("❌ Error retrieving password.");
            }
        });

        // Layout
        VBox layout = new VBox(10, siteLabel, siteField, passwordLabel, passwordField, saveButton, retrieveButton, resultLabel);
        layout.setPadding(new Insets(20));

        // Scene and Stage
        Scene scene = new Scene(layout, 350, 250);
        primaryStage.setTitle("Password Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

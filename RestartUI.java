import javax.swing.*;

public class RestartUI {
    public static void restartApplication() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(null);
            if (frame != null) {
                frame.dispose(); // Close current window
            }
            EntryPointGUI.main(null); // Restart the main application
        });
    }
}

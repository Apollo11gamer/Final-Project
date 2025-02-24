import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioPlayer {
    private Clip clip; // For .wav files
    private Thread mp3Thread;

    // Play an audio file (auto-detect format)
    public void play(String filePath) {
        if (filePath.endsWith(".wav")) {
            playWav(filePath);
        } else {
            System.out.println("Unsupported file format: " + filePath);
        }
    }

    // Stop the currently playing sound
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Play a .wav file
    private void playWav(String filePath) {
        try {
            File soundFile = new File(filePath = "Accend.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        player.play("Accend.wav"); // Change to a valid file path
    }
}
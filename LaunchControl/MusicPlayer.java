package LaunchControl;


import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer {
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
    public static void Music() {
        MusicPlayer player = new MusicPlayer();
        player.play("Music/Spaceflight Simulator - Deep Space (Official Soundtrack).wav"); // Change to a valid file path
    }

    public void InSpace() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("Music/Spaceflight Simulator - Deep Space (Official Soundtrack).wav")
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            Thread.sleep(clip.getMicrosecondLength() / 1000); // Keep thread alive
            clip.start();
            // If you want the sound to loop infinitely, then put: clip.loop(Clip.LOOP_CONTINUOUSLY); 
            // If you want to stop the sound, then use clip.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
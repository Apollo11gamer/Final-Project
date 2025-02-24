import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {

    public static void main(String[] args) {
        playMusic("resources/Spaceflight Simulator - Cosmic Ocean (Official Soundtrack).mp3"); // Replace with your music file path
    }

    public static void playMusic(String filePath) {
        try {
            // Create an AudioInputStream from the sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new java.io.File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start(); // Start playing the music
            
            // Keep the program running until the music is done playing
            System.out.println("Playing music...");
            while (clip.isRunning()) {
                Thread.sleep(1000);
            }
            clip.close();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Audio file format is unsupported: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

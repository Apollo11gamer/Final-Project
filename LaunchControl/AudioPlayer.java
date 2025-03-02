package LaunchControl;

import java.io.IOException;
import javax.sound.sampled.*;

public class AudioPlayer {
    private Clip clip;
    private FloatControl volumeControl;

    public void sound() {
        try {
            // Load your audio file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                getClass().getResource("Music copy/Spaceflight Simulator - Deep Space (Official Soundtrack).wav")
            );
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            
            // Get volume control
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Set initial volume to a low but audible level
            float minVolume = volumeControl.getMinimum();  // Absolute minimum volume
            float maxVolume = volumeControl.getMaximum();  // Maximum volume
            float initialVolume = minVolume + (maxVolume - minVolume) * 0.1f; // 10% of the full range

            volumeControl.setValue(initialVolume);

            // Start playing the sound asynchronously
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            // Gradually increase volume for fade-in effect
            new Thread(() -> fadeIn(initialVolume, maxVolume)).start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void fadeIn(float startVolume, float maxVolume) {
        try {
            float step = (maxVolume - startVolume) / 50; // Adjust steps for smoother fade-in

            for (float volume = startVolume; volume <= maxVolume; volume += step) {
                volumeControl.setValue(volume);
                Thread.sleep(500); // Adjust duration of fade-in
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}

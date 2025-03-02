package LaunchControl;

import java.io.IOException;
import javax.sound.sampled.*;

public class BigBoom {
    private Clip clip;

    public void sound(String Music) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("Music copy/Fart with reverb sound effect.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
        }
    }
    

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}

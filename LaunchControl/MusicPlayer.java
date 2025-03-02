package LaunchControl;

import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer {
    private Clip clip;

    public void sound(String musicSpaceflight_Simulator__Tiny_Planet_O) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("Music copy/Spaceflight Simulator - Cosmic Ocean (Official Soundtrack).wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
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

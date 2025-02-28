package LaunchControl;

import javax.sound.sampled.*;

public class Rentry {
    private Clip clip;

    public void sound(String launchControlMusic_copySpaceflight_Simula) {
        try {
            // Load your audio file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("Music copy/Spaceflight Simulator - Tiny Planet (Official Soundtrack).wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); //Loop audio
            clip.start(); // Start playing the sound asynchronously
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}

package LaunchControl;

import javax.sound.sampled.*;

public class MusicPlayer {
    private Clip clip;

    public void sound(String musicSpaceflight_Simulator__Tiny_Planet_O) {
        try {
            System.out.println("Attempting to play music...");
            if (clip != null && clip.isRunning()) {
                System.out.println("Stopping existing clip...");
                clip.stop();
                clip.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("Music copy/Spaceflight Simulator - Cosmic Ocean (Official Soundtrack).wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            System.out.println("Music should be playing now...");
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

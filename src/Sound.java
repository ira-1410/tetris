package tetris;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;
    private FloatControl gainControl;

    public Sound(String fileName) {
        try {
            File file = new File("tetris/sounds/" + fileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        if (clip == null) return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    public void setVolume(float decibels) {
        if (gainControl != null) {
            // Clamp between min and max supported by the system
            decibels = Math.max(gainControl.getMinimum(), Math.min(decibels, gainControl.getMaximum()));
            gainControl.setValue(decibels);
        }
    }
}

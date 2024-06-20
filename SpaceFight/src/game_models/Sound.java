package game_models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;

    public static final Sound Explosion = new Sound("");
    public static final Sound Soundgame = new Sound("");
    public static final Sound SoundShoot = new Sound("");

    private Sound(String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            new Thread(() -> {
                clip.setFramePosition(0);
                clip.start();
            }).start();
        }
    }

    public void loop() {
        if (clip != null) {
            new Thread(() -> {
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }).start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}

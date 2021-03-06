package ac.bootcamp.row1game.menu.sound;

import java.io.IOException;
import java.util.HashMap;



public class SoundManager {

    private static SoundManager instance;

    private HashMap<GameSound, Sound> sounds = new HashMap<>();

    public static void playSound(GameSound gameSound) {

        instance.reproduceSound(gameSound);
    }

    public SoundManager init() {

        instance = this;
        for(GameSound sound : GameSound.values()) {

            try {

                sounds.put(sound, new Sound(sound.getPath()));
            }
            catch(IOException e) {

                sounds.put(sound, null);
                System.out.println("Sound not found " + sound.getPath());
            }
        }

        return this;
    }

    public void dispose() {

        for(GameSound sound : sounds.keySet()) {

            if(sounds.get(sound) == null) {

                continue;
            }

            sounds.get(sound).close();
        }
    }

    private void reproduceSound(GameSound gameSound) {

        Sound s = sounds.get(gameSound);

        if(s == null) {

            System.out.println("Sound not found " + gameSound);
            return;
        }

        s.play(true);
    }
}
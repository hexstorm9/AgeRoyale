package persistence;

import business.entities.Sounds;
import javafx.scene.media.Media;

import java.util.HashMap;

public class SoundDAO {

    private static final String SOUNDS_PATH = "./resources/sounds/";

    public HashMap<Sounds, Media> read() {
        HashMap<Sounds, Media> soundsLoaded = new HashMap<Sounds, Media>();//aqui el hashmap
        for(Sounds sounds: Sounds.values()){// aqui el enum i el contructor
            Media songMedia = new Media(SOUNDS_PATH + sounds.getFileName());
            soundsLoaded.put(sounds, songMedia);//aqui els song i media
        }
        return soundsLoaded;
    }
}

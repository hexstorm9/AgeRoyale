package persistence;

import business.entities.Sounds;
import java.io.File;
import java.util.HashMap;


public class SoundDAO {

    private static final String SOUNDS_PATH = "./resources/sounds/";

    public HashMap<Sounds, File> read() {
        HashMap<Sounds, File> soundsLoaded = new HashMap<>();
        for(Sounds s: Sounds.values()){
            soundsLoaded.put(s, new File(SOUNDS_PATH + s.getFileName()));
        }
        return soundsLoaded;
    }
}

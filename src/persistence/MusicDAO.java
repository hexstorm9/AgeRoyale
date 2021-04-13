package persistence;

import business.entities.Songs;
import javafx.scene.media.*;
import java.util.HashMap;


public class MusicDAO {
    private static final String MUSIC_PATH = "./resources/music/";

    public HashMap<Songs, Media> read() {
        HashMap<Songs, Media> songsLoaded = new HashMap<Songs, Media>();//aqui el hashmap
        for(Songs s: Songs.values()){// aqui el enum i el contructor
            Media songMedia = new Media(MUSIC_PATH + s.getFileName());
            songsLoaded.put(s, songMedia);//aqui els song i media
        }
        return songsLoaded;
    }

}




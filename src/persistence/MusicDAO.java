package persistence;

import business.entities.Songs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class MusicDAO {

    private static final String MUSIC_PATH = "./resources/music/";

    public HashMap<Songs, File> read() throws IOException {
        HashMap<Songs, File> songsLoaded = new HashMap<>();
        for(Songs s: Songs.values()){
            songsLoaded.put(s, new File(MUSIC_PATH + s.getFileName()));
        }
        return songsLoaded;
    }

}




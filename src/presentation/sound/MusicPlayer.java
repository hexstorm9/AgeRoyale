package presentation.sound;
import business.entities.Songs;
import business.entities.Sounds;
import javazoom.jlgui.basicplayer.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * SoundPlayer will be a {@code singleton} class providing methods to add music to the application.
 *
 * <p>The songs described in the {@link Songs} enum will have a path ({@link Songs#getFileName()}),
 * and it will be used to load them all on the RAM whenever the method {@link MusicPlayer#load()} is called.
 *
 * <p>Internally, this class uses the BasicPlayer library.
 *
 * @see Sounds
 * @see BasicPlayer
 * @version 1.0
 */
public class MusicPlayer implements BasicPlayerListener {

    private static MusicPlayer singletonInstance;
    private static final String MUSIC_PATH = "./resources/music/";

    /**
     * Songs will be loaded into a HashMap, where each song ({@link Songs}) maps to a File.
     * This File will be played by the BasicPlayer.
     */
    private HashMap<Songs, File> songsLoaded;

    private double volume = 0; //From 0 to 100

    /**
     * Only one song can be played at a time, so we'll only have one BasicPlayer
     */
    private BasicPlayer basicMusicPlayer;
    private Songs currentSongPlaying;


    private MusicPlayer(){
        //Disable BasicPlayer Logging
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog"); //Disable Commons-Logging (the BasicPlayer library uses it)
    }

    /**
     * Returns the singleton instance of the class.
     * @return MusicPlayer Singleton Instance
     */
    public static MusicPlayer getInstance() {
        if(singletonInstance == null) singletonInstance = new MusicPlayer();
        return singletonInstance;
    }


    /**
     * Loads all the song files into RAM and gets the singleton instance ready to start playing.
     * @throws IOException Whenever a Song provided in the {@link Songs} enum is not found.
     */
    public void load() throws IOException{
        try{
            if(basicMusicPlayer != null){
                basicMusicPlayer.stop();
                basicMusicPlayer.removeBasicPlayerListener(this);
                currentSongPlaying = null;
            }
        }catch(BasicPlayerException e){ e.printStackTrace(); }

        songsLoaded = singletonInstance.read();
        basicMusicPlayer = new BasicPlayer();
        basicMusicPlayer.addBasicPlayerListener(singletonInstance);
    }


    public void setVolume(int newVolume){
        volume = newVolume;
        if(basicMusicPlayer != null){
            try{
                basicMusicPlayer.setGain(volume/100);
            }catch(BasicPlayerException e){
            }
        }

    }

    public int getVolume(){
        return (int)volume;
    }

    /**
     * Starts playing the {@link Songs} provided in an infinite loop.
     * <p>If {@link MusicPlayer#load()} has not yet been called, the music won't start.
     *
     * @param song The song that needs to be played
     */
    public void playInLoop(Songs song){
        if(song == null || basicMusicPlayer == null || currentSongPlaying == song) return;

        try{
            if(basicMusicPlayer.getStatus() == BasicPlayer.PLAYING) basicMusicPlayer.stop();
            basicMusicPlayer.open(songsLoaded.get(song));
            basicMusicPlayer.play();
            currentSongPlaying = song;
        }catch(BasicPlayerException e){}
    }



    @Override
    public void opened(Object o, Map map) {}

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {}

    @Override
    public void stateUpdated(BasicPlayerEvent basicPlayerEvent) {
        //If the BasicMusicPlayer reaches the end, start again playing (infinite music loop)
        if(basicPlayerEvent.getCode() == BasicPlayerEvent.EOM){
            try{
                basicMusicPlayer.stop();
                currentSongPlaying = null;
                basicMusicPlayer.play();
            }catch(BasicPlayerException e){}
        }
    }

    @Override
    public void setController(BasicController basicController) {}


    /**
     * Reads all the music files into the {@link Songs} enum and loads them into a HashMap that will be returned.
     * @return A HashMap loaded with every Song and its respective File
     * @throws IOException Whenever a Song provided in the {@link Songs} enum is not found.
     *
     * @see Sounds
     * @see File
     */
    private HashMap<Songs, File> read() throws IOException {
        HashMap<Songs, File> songsLoaded = new HashMap<>();
        for(Songs s: Songs.values()){
            File file = new File(MUSIC_PATH + s.getFileName());
            if(!file.exists()) throw new IOException(s.getFileName() + " not found");
            songsLoaded.put(s, file);
        }
        return songsLoaded;
    }
}
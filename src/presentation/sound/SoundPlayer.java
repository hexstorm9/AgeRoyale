package presentation.sound;

import business.entities.Sounds;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * SoundPlayer will be a {@code singleton} class providing methods to add sound to the application.
 *
 * <p>The sounds described in the {@link Sounds} enum will have a path ({@link Sounds#getFileName()}),
 * and it will be used to load them all on the RAM whenever the method {@link SoundPlayer#load()} is called.
 *
 * <p>Internally, this class uses the BasicPlayer library.
 *
 * @see Sounds
 * @see BasicPlayer
 * @version 1.0
 */
public class SoundPlayer{

    private static SoundPlayer singletonInstance;
    private static final String SOUNDS_PATH = "./resources/sounds/";

    private double volume = 100; //From 0 to 100
    private HashMap<Sounds, File> soundsLoaded;

    /**
     * As multiple sounds can be played at a time, we will have a SoundPlayer Pool
     * with size SOUND_PLAYER_POOL_SIZE.
     *
     * <p>That means that we'll have a lot of BasicPlayer objects (a Pool) and whenever we want to play
     * a sound, we'll use the first BasicPlayer of the pool so as to play it. And as we're using it, we'll
     * send it to the last position of the pool.
     */
    private ArrayList<BasicPlayer> soundPlayerPool;
    private static final int SOUND_PLAYER_POOL_SIZE = 10;

    private BasicPlayer buttonSound; //Has to be already loaded and ready to play as the sound when clicking a button has to be instantaneously


    private SoundPlayer() {
        //Disable BasicPlayer Logging
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog"); //Disable Commons-Logging (the BasicPlayer library uses it)
    }

    /**
     * Returns the singleton instance of the class.
     * @return SoundPlayer Singleton Instance
     */
    public static SoundPlayer getInstance(){
        if(singletonInstance == null) singletonInstance = new SoundPlayer();
        return singletonInstance;
    }


    /**
     * Loads all the sound files into RAM and gets the singleton instance ready to start playing.
     * @throws IOException Whenever a Sound provided in the {@link Sounds} enum is not found.
     */
    public void load() throws IOException{
        soundsLoaded = singletonInstance.read();
        soundPlayerPool = new ArrayList<>(SOUND_PLAYER_POOL_SIZE);
        for(int i = 0; i < SOUND_PLAYER_POOL_SIZE; i++){
            soundPlayerPool.add(new BasicPlayer());
        }

        buttonSound = new BasicPlayer(); //Load the buttonSound and have it prepared to be played
        try{
            buttonSound.open(soundsLoaded.get(Sounds.BUTTON));
        }catch(BasicPlayerException e){ e.printStackTrace();}
    }


    public void setVolume(int newVolume){
        volume = newVolume;
    }

    public int getVolume(){
        return (int)volume;
    }


    /**
     * Starts playing the {@link Sounds} provided.
     * <p>If {@link SoundPlayer#load()} has not yet been called, the sound won't be played.
     *
     * @param sound The sound that needs to be played
     */
    public void play(Sounds sound){
        if(sound == null || soundPlayerPool == null) return;

        if(sound == Sounds.BUTTON){
            try{
                buttonSound.play();
            }catch(BasicPlayerException e) {
                e.printStackTrace();
            }
            return; //Always exit the method
        }

        BasicPlayer soundPlayerToUse = soundPlayerPool.get(0); //Get the first BasicPlayer of the pool
        soundPlayerPool.remove(soundPlayerToUse); //Remove it of the pool
        soundPlayerPool.add(soundPlayerPool.size(), soundPlayerToUse); //Add it again to the pool but as the last one
        try{
            soundPlayerToUse.open(soundsLoaded.get(sound));
            soundPlayerToUse.play();
        }catch (BasicPlayerException e){}
    }


    /**
     * Reads all the sound files into the {@link Sounds} enum and loads them into a HashMap that will be returned.
     * @return A HashMap loaded with every Sound and its respective File
     * @throws IOException Whenever a Sound provided in the {@link Sounds} enum is not found.
     *
     * @see Sounds
     * @see File
     */
    private HashMap<Sounds, File> read() throws IOException {
        HashMap<Sounds, File> soundsLoaded = new HashMap<>();
        for(Sounds s: Sounds.values()){
            File file = new File(SOUNDS_PATH + s.getFileName());
            if(!file.exists()) throw new IOException(s.getFileName() + " not found");
            soundsLoaded.put(s, file);
        }
        return soundsLoaded;
    }


}

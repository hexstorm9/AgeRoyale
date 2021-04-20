package presentation.sound;

import business.entities.Sounds;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SoundPlayer{

    private static SoundPlayer singletonInstance;
    private static final String SOUNDS_PATH = "./resources/sounds/";

    private HashMap<Sounds, File> soundsLoaded;

    private ArrayList<BasicPlayer> soundPlayerPool;
    private static final int SOUND_PLAYER_POOL_SIZE = 10;


    private SoundPlayer() {}

    public static SoundPlayer getInstance(){
        if(singletonInstance == null) singletonInstance = new SoundPlayer();
        return singletonInstance;
    }


    public void load() throws IOException{
        singletonInstance.soundsLoaded = singletonInstance.read();
        singletonInstance.soundPlayerPool = new ArrayList<>(SOUND_PLAYER_POOL_SIZE);
        for(int i = 0; i < SOUND_PLAYER_POOL_SIZE; i++) singletonInstance.soundPlayerPool.add(new BasicPlayer());
    }


    public void play(Sounds sound){
        if(sound == null || soundPlayerPool == null) return;

        BasicPlayer soundPlayerToUse = soundPlayerPool.get(0); //Get the first BasicPlayer of the pool
        soundPlayerPool.remove(soundPlayerToUse); //Remove it of the pool
        soundPlayerPool.add(soundPlayerPool.size(), soundPlayerToUse); //Add it again to the pool but as the last one
        try{
            soundPlayerToUse.open(soundsLoaded.get(sound));
            soundPlayerToUse.play();
        }catch (BasicPlayerException e){}
    }


    public HashMap<Sounds, File> read() throws IOException {
        HashMap<Sounds, File> soundsLoaded = new HashMap<>();
        for(Sounds s: Sounds.values()){
            File file = new File(SOUNDS_PATH + s.getFileName());
            if(!file.exists()) throw new IOException(s.getFileName() + " not found");
            soundsLoaded.put(s, file);
        }
        return soundsLoaded;
    }
}

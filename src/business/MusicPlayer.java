package business;
import business.entities.Songs;
import javazoom.jlgui.basicplayer.*;
import persistence.MusicDAO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MusicPlayer implements BasicPlayerListener {

    private static MusicPlayer singletonInstance;
    private MusicDAO musicDAO;
    private HashMap<Songs, File> songsLoaded;

    private BasicPlayer basicMusicPlayer;

    private MusicPlayer(){}

    public static MusicPlayer getInstance() throws IOException{
        if(singletonInstance == null){
            singletonInstance = new MusicPlayer();
            singletonInstance.musicDAO = new MusicDAO();
            singletonInstance.songsLoaded = singletonInstance.musicDAO.read();
            singletonInstance.basicMusicPlayer = new BasicPlayer();
            singletonInstance.basicMusicPlayer.addBasicPlayerListener(singletonInstance);
        }
        return singletonInstance;
    }


    public void playInLoop(Songs song){
        if(song == null || basicMusicPlayer == null) return;

        try{
            if(basicMusicPlayer.getStatus() == BasicPlayer.PLAYING) basicMusicPlayer.stop();
            basicMusicPlayer.open(songsLoaded.get(song));
            basicMusicPlayer.play();
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
                basicMusicPlayer.play();
            }catch(BasicPlayerException e){}
        }
    }

    @Override
    public void setController(BasicController basicController) {}
}
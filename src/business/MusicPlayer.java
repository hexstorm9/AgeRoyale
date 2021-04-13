package business;
import business.entities.Songs;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import persistence.MusicDAO;

import java.util.HashMap;


public class MusicPlayer {

    private MusicDAO musicDAO;
    private HashMap<Songs, Media> songsLoaded;
    private MediaPlayer currentSongPlayer;



    public void load() {

        if (musicDAO == null) {
            musicDAO = new MusicDAO();
        }
        songsLoaded = musicDAO.read();
    }

    public void playInLoop(Songs song){
        Media mediaToPlay = songsLoaded.get(song);
        if(currentSongPlayer != null) currentSongPlayer.stop();

        currentSongPlayer = new MediaPlayer(mediaToPlay);
        currentSongPlayer.play();
    }



}
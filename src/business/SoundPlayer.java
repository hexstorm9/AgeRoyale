package business;

import business.entities.Sounds;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import persistence.SoundDAO;

import java.util.HashMap;

public class SoundPlayer {

    private SoundDAO soundDAO;
    private HashMap<Sounds, Media> soundsLoaded;
    //private MediaPlayer currentSoundPlayer[] = new MediaPlayer[10];
    private MediaPlayer currentSongPlayerButton;
    private MediaPlayer currentSongPlayerSplashscreen;

    public SoundPlayer() {
    }

    public void load() {

        if (soundDAO == null) {
            soundDAO = new SoundDAO();
        }
        soundsLoaded = soundDAO.read();
    }

    public void play(Sounds sound){
        Media mediaToPlay = soundsLoaded.get(sound);

        if(currentSongPlayerButton == null && currentSongPlayerSplashscreen == null){

            currentSongPlayerButton = new MediaPlayer(mediaToPlay);
            currentSongPlayerButton.play();

        }else if(currentSongPlayerButton != null && currentSongPlayerSplashscreen == null){

            currentSongPlayerSplashscreen = new MediaPlayer(mediaToPlay);
            currentSongPlayerSplashscreen.play();

        }else if(currentSongPlayerButton == null && currentSongPlayerSplashscreen != null){

            currentSongPlayerButton = new MediaPlayer(mediaToPlay);
            currentSongPlayerButton.play();
        }

    }
}

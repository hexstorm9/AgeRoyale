package main;


import persistence.LanguageDAO;

import business.MusicPlayer;
import business.SoundPlayer;
import business.entities.Songs;
import business.entities.Sounds;


import java.io.IOException;


public class Main {



    public static void main(String[] args) {
        //Do not remove -----------------------------
        //Uncomment to see BasicPlayer logs ---------
        //System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog"); //Disable Commons-Logging (the BasicPlayer library uses it)
        // ------------------------------------------
        LanguageDAO lan = new LanguageDAO();
        try{
            SoundPlayer.getInstance().play(Sounds.BUTTON);
            Thread.sleep(1000);
            SoundPlayer.getInstance().play(Sounds.SPLASH_SCREEN);
            Thread.sleep(4000);
            MusicPlayer.getInstance().playInLoop(Songs.MENU);
            lan.readSentences();
        }catch(IOException | InterruptedException e){
            System.out.println("Couldn't find songs. Check whether the paths are correct.");

        }


    }

}

package presentation.controller;

import business.GameModel;
import business.entities.LanguageManager;
import com.google.gson.JsonSyntaxException;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import business.entities.Sounds;
import presentation.view.RoyaleFrame;
import presentation.view.SplashScreen;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


/**
 * {@code SplashScreenController} is a {@link ScreenController} that will manage an instance of a
 * {@link SplashScreen} and put it onto the {@link RoyaleFrame} of the game.
 *
 * <p>It will load the whole game in background (sounds, strings of the current language, check connection with
 * the database) and update the progress to the {@link SplashScreen} it manages. Once ended, it will create and
 * start a {@link LoginScreenController}
 *
 * @see SplashScreen
 * @see ScreenController
 * @version 1.0
 */
public class SplashScreenController extends ScreenController{

    private SplashScreen splashScreen;


    /**
     * Default SplashScreenController constructor.
     * @param royaleFrame The royaleFrame of the game
     * @param gameModel The gameModel of the game
     */
    public SplashScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel, null);
    }


    /**
     * {@inheritDoc}
     */
    public void start(boolean showSettingsPanelOnStart){
        splashScreen = new SplashScreen(royaleFrame.getHeight());
        setPanelToListenForESCKey(splashScreen);

        royaleFrame.changeScreen(splashScreen);
        new LoadGameInBackground().execute(); //Start Loading the Game in a Background thread
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void buildSettingsPanel(){
        settingsPanel.addLanguagesButton();
        settingsPanel.addCreditsButton();
    }


    /**
     * Updates the progressBar of the {@link SplashScreen} it controls.
     * @param progress New progress of the progressBar
     */
    private void updateProgressBar(int progress){
        splashScreen.setProgressBarValue(progress);
    }




    private class LoadGameInBackground extends SwingWorker<String, Integer>{
        @Override
        protected String doInBackground(){
            try{
                try {
                    SoundPlayer.getInstance().load(); //Load Sounds
                    SoundPlayer.getInstance().play(Sounds.SPLASH_SCREEN); // Play the SplashScreen sound when sounds have been loaded (ASAP)
                    moveProgress(0, 10, 600);
                }catch(IOException e){
                    e.printStackTrace();
                    return "Error Loading Sounds";
                }
                try {
                    MusicPlayer.getInstance().load(); //Load Songs
                    moveProgress(11, 40, 800);
                }catch(IOException e){
                    e.printStackTrace();
                    return "Error Loading Songs";
                }
                try {
                    gameModel.loadDatabaseAndTestConnection(); //Load Database and Test the connection
                    moveProgress(41, 70, 300);
                }catch(IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    return "Error Loading the Database";
                }
                try {
                    LanguageManager.load(); //Load current language sentences
                    moveProgress(71, 100, 600);
                }catch(JsonSyntaxException | IOException e){
                    e.printStackTrace();
                    return "Error Loading the Language Manager";
                }
            }catch(InterruptedException e){
                e.printStackTrace();
                return "Error. Interrupted Exception.";
            }

            return null; //Return success
        }


        private void moveProgress(int startingProgress, int desiredProgress, int finalDelay) throws InterruptedException{
            Random r = new Random();
            final int progressStep = r.nextInt(3) + 1;
            final int sleepStep = r.nextInt(40) + 1;
            int progress = startingProgress;

            while(progress < desiredProgress){
                if(progress > desiredProgress) progress = desiredProgress;
                publish(progress);
                Thread.sleep(sleepStep);
                progress += progressStep;
            }

            if(finalDelay < 1000) Thread.sleep(finalDelay);
            else Thread.sleep(1000);
        }


        @Override
        protected void process(List<Integer> chunks) {
            int currentProgress = chunks.get(chunks.size() - 1);
            updateProgressBar(currentProgress);
        }

        @Override
        protected void done(){
            String errorMessage = null;
            try{
                errorMessage = get();
            }catch(Exception e){
                errorMessage = "Error in the Worker Thread";
                e.printStackTrace();
            }

            if (errorMessage == null) goToScreen(Screens.LOGIN_SCREEN);
            else royaleFrame.showCriticalErrorAndExitApplication(errorMessage);
        }
    }

}

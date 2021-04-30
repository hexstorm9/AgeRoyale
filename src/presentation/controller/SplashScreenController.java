package presentation.controller;

import business.GameModel;
import business.entities.LanguageManager;
import com.google.gson.JsonSyntaxException;
import presentation.graphics.MenuGraphics;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import business.entities.Sounds;
import presentation.view.RoyaleFrame;
import presentation.view.SplashScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class SplashScreenController extends ScreenController{

    private SplashScreen splashScreen;
    private LoadGameInBackground loadGameInBackground;


    public SplashScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }


    public void start(boolean showSettingsPanelOnStart){
        splashScreen = new SplashScreen(royaleFrame.getHeight());
        setPanelToListenForESCKey(splashScreen);

        royaleFrame.changeScreen(splashScreen, RoyaleFrame.BackgroundStyle.MENU);

        loadGameInBackground = new LoadGameInBackground();
        loadGameInBackground.execute();
    }


    @Override
    public void buildSettingsPanel(){
        settingsPanel.addLanguagesButton();
        settingsPanel.addCreditsButton();
    }


    public void updateProgressBar(int progress){
        splashScreen.setProgressBarValue(progress);
    }


    @Override
    public void actionPerformed(ActionEvent e) {}


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

            if (errorMessage == null) goToScreen(Screen.LOGIN_SCREEN);
            else royaleFrame.showCriticalErrorAndExitApplication(errorMessage);
        }
    }

}

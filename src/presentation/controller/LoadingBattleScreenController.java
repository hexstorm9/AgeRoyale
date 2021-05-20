package presentation.controller;

import business.GameModel;
import presentation.graphics.BattleGraphics;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import presentation.view.LoadingBattleScreen;
import presentation.view.RoyaleFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class LoadingBattleScreenController extends ScreenController{

    private LoadingBattleScreen loadingBattleScreen;

    public LoadingBattleScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    @Override
    public void start(boolean showSettingsPanelOnStart) {
        loadingBattleScreen = new LoadingBattleScreen(royaleFrame.getHeight());
        setPanelToListenForESCKey(loadingBattleScreen);

        royaleFrame.changeScreen(loadingBattleScreen, RoyaleFrame.BackgroundStyle.MENU);

        MusicPlayer.getInstance().stop();
        new LoadBattleResourcesInBackground().execute(); //Start the LoadBattleResourcesInBackground thread
    }

    @Override
    public void buildSettingsPanel() {
        settingsPanel.addLanguagesButton();
        settingsPanel.addCreditsButton();
    }


    public void updateProgressBar(int progress){
        loadingBattleScreen.setProgressBarValue(progress);
    }





    private class LoadBattleResourcesInBackground extends SwingWorker<String, Integer> {
        @Override
        protected String doInBackground(){
            try{
                try {
                    //TODO: Load Battle Sounds correctly (implement them to the SoundPlayer)
                    SoundPlayer.getInstance().load(); //Load Battle Sounds
                    moveProgress(0, 40, 600);
                }catch(IOException e){
                    e.printStackTrace();
                    return "Error Loading Battle Sounds";
                }
                try {
                    BattleGraphics.load();
                    moveProgress(41, 100, 600);
                }catch(IOException e){
                    e.printStackTrace();
                    return "Error Loading Battle Graphics";
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
            String errorMessage;
            try{
                errorMessage = get();
            }catch(Exception e){
                errorMessage = "Error in the Worker Thread";
                e.printStackTrace();
            }

            if (errorMessage == null) goToScreen(Screens.BATTLE); //If everything has been loaded correctly, let's go to the BATTLE Screen
            else royaleFrame.showCriticalErrorAndExitApplication(errorMessage);
        }
    }

}

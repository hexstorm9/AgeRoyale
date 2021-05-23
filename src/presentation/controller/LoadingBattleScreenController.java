package presentation.controller;

import business.GameModel;
import business.Movement;
import business.BattleSprites;
import presentation.sound.MusicPlayer;
import presentation.view.LoadingBattleScreen;
import presentation.view.RoyaleFrame;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


/**
 * {@code LoadingBattleScreenController} is a {@link ScreenController} that will manage and listen to an
 * instance of a {@link LoadingBattleScreen} and put it onto the {@link RoyaleFrame} of the game.
 *
 * <p>It will load whatever is necessary to start the Battle, and when it finishes to do so it will create
 * and start a new {@link BattleController}
 *
 * <p>Depending on what it is provided to the constructor, it will load an existing old battle or load
 * a new battle to play.
 *
 * @see LoadingBattleScreen
 * @version 1.0
 */
public class LoadingBattleScreenController extends ScreenController{


    private LoadingBattleScreen loadingBattleScreen;

    /**
     * Id of the battle to reproduce. {@code null} if there is no battle to reproduce and we want to
     * load a normal battle instead.
     */
    private Object battleToReproduceId;
    private Movement[] movementsOfTheBattleToReproduce;


    /**
     * Default LoginBattleScreenController constructor.
     *
     * @param royaleFrame The RoyaleFrame this controller will control
     * @param gameModel The GameModel this controller will control
     * @param battleToReproduceId The ID of the battle that needs to be loaded and reproduced. If {@code null},
     * it means that no battle needs to be reproduced, and we're only loading a normal battle
     */
    public LoadingBattleScreenController(RoyaleFrame royaleFrame, GameModel gameModel, Object battleToReproduceId){
        super(royaleFrame, gameModel, battleToReproduceId);
        this.battleToReproduceId = battleToReproduceId;
    }


    /**
     * Called when the LoadingBattleScreenController is created.
     * <p>Creates the view for this LoadingBattleScreenController ({@link LoadingBattleScreen}) and puts it onto the {@link RoyaleFrame}
     * this controller controls.
     *
     * @param showSettingsPanelOnStart Whether you want to show the settings panel on start or not
     */
    @Override
    public void start(boolean showSettingsPanelOnStart) {
        loadingBattleScreen = new LoadingBattleScreen(royaleFrame.getHeight());
        setPanelToListenForESCKey(loadingBattleScreen);

        royaleFrame.changeScreen(loadingBattleScreen);

        MusicPlayer.getInstance().stop();
        new LoadBattleResourcesInBackground().execute(); //Start the LoadBattleResourcesInBackground thread
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void buildSettingsPanel() {
        settingsPanel.addLanguagesButton();
        settingsPanel.addCreditsButton();
    }


    /**
     * Method provided to update the progress bar on the view this controller controls.
     * @param progress The new progress of the progressbar
     */
    public void updateProgressBar(int progress){
        loadingBattleScreen.setProgressBarValue(progress);
    }





    private class LoadBattleResourcesInBackground extends SwingWorker<String, Integer> {
        @Override
        protected String doInBackground(){
            try{
                try {
                    if(battleToReproduceId != null)
                        movementsOfTheBattleToReproduce = gameModel.getCompleteBattleById((int)battleToReproduceId).getMovements();
                    moveProgress(0, 40, 600);
                }catch(SQLException e){
                    e.printStackTrace();
                    return "Error loading the Battle to Reproduce";
                }
                try {
                    BattleSprites.load();
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


            //If everything has been loaded correctly, let's go to the BATTLE Screen
            if(errorMessage == null){
                if(battleToReproduceId == null) goToScreen(Screens.BATTLE);
                else goToScreen(Screens.BATTLE, movementsOfTheBattleToReproduce);
            }
            else royaleFrame.showCriticalErrorAndExitApplication(errorMessage);
        }
    }

}

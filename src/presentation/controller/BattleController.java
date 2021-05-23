package presentation.controller;

import business.*;
import business.entities.Cards;
import business.entities.Songs;
import business.entities.Sounds;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import presentation.view.BattleEndedFrontPanel;
import presentation.view.BattleScreen;
import presentation.view.RoyaleFrame;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * BattleController is the Controller class for the {@link BattleScreen}.
 * <p>It will be linking both the {@link BattleScreen} and {@link BattleModel}.
 *
 * <p>Depending on what's passed in the Constructor, it will create
 * a normal Battle or reproduce an existing old battle.
 *
 * @see BattleScreen
 * @see BattleModel
 * @version 1.0
 */
public class BattleController extends ScreenController implements Runnable{

    private BattleScreen battleScreen;
    private BattleModel battleModel;

    private boolean gameRunning = true;

    private boolean isReproducingOldBattle; //False if the constructor param movementsArray is null
    private Movement[] oldBattleMovements;

    private BattleBot battleBot;


    private final int MAX_FRAMES_PER_SECOND = 60;
    private final int INITIAL_UPDATES_PER_SECOND = 60;
    private int CURRENT_UPDATES_PER_SECOND = INITIAL_UPDATES_PER_SECOND;

    private final double fOPTIMAL_TIME = 1000000000 / MAX_FRAMES_PER_SECOND;
    private double uOPTIMAL_TIME = 1000000000 / CURRENT_UPDATES_PER_SECOND;


    /**
     * Default BattleController Constructor.
     *
     * @param royaleFrame The RoyaleFrame this controller will control
     * @param gameModel The GameModel this controller will control
     * @param movementsArray Array of {@link business.Movement} that will include all the movements that need to
     * occur during the Battle. If {@code null}, it means it's a normal battle (not a reproduced one)
     */
    public BattleController(RoyaleFrame royaleFrame, GameModel gameModel, Object movementsArray){
        super(royaleFrame, gameModel, movementsArray);

        //If the movementsArray is not null, it means we have to reproduce an old battle with the movements provided
        if(movementsArray != null){
            isReproducingOldBattle = true;
            oldBattleMovements = Arrays.copyOf((Object[])movementsArray, ((Object[])movementsArray).length, Movement[].class);
            settingsPanel.removeConfirmationBeforeExiting(); //We don't want confirmation before exiting if we're not playing
        }
        else{
            isReproducingOldBattle = false;
        }
    }


    /**
     * Called when the BattleController is created.
     * <p>Creates the view for this BattleController ({@link BattleScreen}) and puts it onto the {@link RoyaleFrame}
     * this controller controls.
     * <p>Also, starts the {@link BattleBot} Thread and the {@code Game Loop} Thread (this)
     * @param showSettingsPanelOnStart Whether you want to show the settings panel on start or not
     */
    public void start(boolean showSettingsPanelOnStart){
        battleScreen = new BattleScreen(this, royaleFrame.getHeight(), isReproducingOldBattle);
        setPanelToListenForESCKey(battleScreen);

        royaleFrame.changeScreen(battleScreen);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.BATTLE);


        //If it's not reproducing an old battle, create a normal BattleModel and start the Bot
        if(!isReproducingOldBattle){
            battleModel = new BattleModel(this, gameModel.getPlayer(), battleScreen.getBattlePanelSize());
            battleScreen.updateCardsToShow();
            battleBot = new BattleBot(battleModel, battleScreen.getBattlePanelSize());
            battleBot.startBot();
        }
        else{ //If it's reproducing an old battle, cast the array of movements and pass it when creating the BattleModel
            battleModel = new BattleModel(oldBattleMovements, this, gameModel.getPlayer(), battleScreen.getBattlePanelSize());
        }


        //Start the gameLoop
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void buildSettingsPanel(){
        settingsPanel.addTroopsStats();
        settingsPanel.addLogOutButton();
        settingsPanel.addCreditsButton();
        settingsPanel.addConfirmationBeforeExiting();
    }


    /**
     * Automatically called when the new thread of this class starts.
     * <p>It starts the GameLoop of the game, running both Updates() and Paints() at a controlled interval of time.
     */
    @Override
    public void run(){
        double uDeltaTime = 0, fDeltaTime = 0;
        int frames = 0, updates = 0;
        long startTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while(gameRunning){
            long currentTime = System.nanoTime();
            if(uOPTIMAL_TIME != Double.MAX_VALUE) uDeltaTime += (currentTime - startTime);
            fDeltaTime += (currentTime - startTime);
            startTime = currentTime;

            if(uDeltaTime >= uOPTIMAL_TIME){
                battleModel.update(); //Update MAX_UPDATES_PER_SECOND times per second the physics of the game
                updates++;
                uDeltaTime -= uOPTIMAL_TIME;
            }

            if(fDeltaTime >= fOPTIMAL_TIME){
                battleScreen.draw(); //Draw MAX_FRAMES_PER_SECOND times per second the BattleScreen
                frames++;
                fDeltaTime -= fOPTIMAL_TIME;
            }


            if(System.currentTimeMillis() - timer >= 1000){
                //System.out.println("UPS --> " + updates + ".  FPS --> " + frames);
                updates = 0;
                frames = 0;
                timer += 1000;
            }
        }
   }



   public void paintBattle(Graphics g){
        battleModel.draw(g);
   }


    /**
    * Returns an {@code Double} from 0 to 100 indicating the current Gold that the Player Has
    * @return {@code Double} from 0 to 100 indicating the current Gold that the Player Has
    */
    public double getPlayerCurrentGold(){
       return battleModel.getPlayerCurrentGold();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(e.getActionCommand() == BattleScreen.EXIT_BUTTON_ACTION_COMMAND && isReproducingOldBattle)
            goToScreen(Screens.MAIN_MENU);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if(e.getSource() instanceof BattleScreen.SouthPanel.CardPanel && !isReproducingOldBattle){
            BattleScreen.SouthPanel.CardPanel cardClicked = (BattleScreen.SouthPanel.CardPanel) e.getSource();
            battleScreen.setCardSelected(cardClicked.getCardHolding());
            SoundPlayer.getInstance().play(Sounds.BUTTON);
        }
        else if(e.getSource() instanceof BattleScreen.BattlePanel && !isReproducingOldBattle){
            Cards cardToThrow = battleScreen.getCardSelected();
            if(cardToThrow == null){ //If no card has been selected
                SoundPlayer.getInstance().play(Sounds.ERROR);
                return;
            }

            boolean cardThrown = battleModel.throwCard(cardToThrow, Card.Status.PLAYER, e.getX(), e.getY());
            if(!cardThrown) SoundPlayer.getInstance().play(Sounds.ERROR);
            else battleScreen.updateCardsToShow(); //Once a card is thrown, let's update cards
        }
        else if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(BattleScreen.PLAY_BUTTON_ACTION_COMMAND)){
                modifyUpdatesPerSecond(-1);
                battleScreen.setPauseButtonVisible();
            }
            else if(labelClicked.getActionCommand().equals(BattleScreen.PAUSE_BUTTON_ACTION_COMMAND)) {
                modifyUpdatesPerSecond(0);
                battleScreen.setPlayButtonVisible();
            }
            else if(labelClicked.getActionCommand().equals(BattleScreen.FORWARD_BUTTON_ACTION_COMMAND)) {
                updatesPerSecondForward();
            }
            else if(labelClicked.getActionCommand().equals(BattleScreen.BACKWARDS_BUTTON_ACTION_COMMAND)) {
                updatesPerSecondBackwards();
            }
        }
    }



    /**
     * To be called from the BattleModel whenever the gameStats have changed
     * @param playerTroops Current player troops on the map
     * @param enemyTroops Current enemy troops on the map
     */
    public void gameStatsChanged(int playerTroops, int enemyTroops) {
        //The method can sometimes be called from other threads, so we'll make sure to pass the duty to the EDT
        SwingUtilities.invokeLater(
                () -> settingsPanel.changeTroopsStats(playerTroops, enemyTroops)
        );
    }


    /**
     * Returns an ArrayList with the current cards that the player can throw (cards to show)
     * @return ArrayList of Cards to show
     */
    public ArrayList<Cards> getCardsToShow(){
        return battleModel.getPlayerCurrentCardsToThrow();
    }


    /**
     * Ends the current Game and Exits the application
     */
    public void endGameAndExit() {
        System.exit(0);
    }

    /**
     * Ends the current Game and Logs Out the current user.
     */
    public void endGameAndLogOut(){
        gameModel.forgetPlayer();
        goToScreen(Screens.LOGIN_SCREEN, false);
    }

    /**
     * To be called from the Model whenever the battle has ended.
     * <p>It stops the GameLoop and everything related to the battle. Also, starts displaying an instance of
     * {@link BattleEndedFrontPanel} with the information of the battle.
     *
     * @param playerCrowns The crowns that the user has won/lost
     */
    public void endGame(int playerCrowns){
        //This method will be called from the gameLoop Thread, and we want to stop that thread.
        //That's why we'll do it from the EDT
        gameRunning = false;

        MusicPlayer.getInstance().stop();

        //If we're reproducing an old battle, do not continue
        if(isReproducingOldBattle) return;


        SoundPlayer.getInstance().play(playerCrowns > 0? Sounds.WON: Sounds.LOST);
        battleBot.stopBot();

        //Let's save a reference to the currentPlayerCrowns and winRate before the model updates it
        final int currentPlayerCrowns = gameModel.getPlayer().getCrowns();
        final int currentWinRate = (int)(((gameModel.getPlayer().getBattleWins() + (playerCrowns > 0? 1: 0))
                /((double)(gameModel.getPlayer().getBattlePlays() + 1))) * 100);

        try{
            //As this method will be called from the gameLoopThread, let's use the EDT to modify the View
            SwingUtilities.invokeLater(() -> {
                BattleEndedFrontPanel frontPanel = new BattleEndedFrontPanel(royaleFrame.getWidth(), royaleFrame.getHeight(),
                        currentPlayerCrowns, playerCrowns, currentWinRate);

                BattleEndedFrontPanelController battleEndedFrontPanelController = new BattleEndedFrontPanelController(this, frontPanel, royaleFrame, playerCrowns > 0);
                battleEndedFrontPanelController.setIfCanBeHidden(false); //We don't want to allow the user to be able to hide this panel

                showFrontPanel(frontPanel, battleEndedFrontPanelController);
            });
        }catch(Exception e){ e.printStackTrace();}
    }


    /**
     * To be called whenever the Battle has finished, from the {@link BattleEndedFrontPanelController}
     *
     * <p>Given a Battle Name, returns to the MainMenu (the Battle has finished and the Player information has been updated)
     * and asks the model to save the current Battle to the Database.
     * <p>The battleName can be {@code null} (no name)
     *
     * @param battleName The name of the Battle that just ended
     * @param won Whether the game was won or not
     */
    public void returnToMainMenu(String battleName, boolean won) {
        Thread saveBattleInBackground = new Thread(() -> {
            gameModel.saveBattle(battleModel.getBattleMovements(), battleName, won);
        });
        saveBattleInBackground.start();

        goToScreen(Screens.MAIN_MENU);
    }


    /**
     * Modifies the UPS to make the game run faster/slower
     * @param multiplicity Can be 0.25, 0.5, 0, 2 or 4
     */
    private void modifyUpdatesPerSecond(double multiplicity){
        if(multiplicity == -1) CURRENT_UPDATES_PER_SECOND = INITIAL_UPDATES_PER_SECOND;

        else CURRENT_UPDATES_PER_SECOND *= multiplicity;
        uOPTIMAL_TIME = multiplicity == 0? Double.MAX_VALUE: 1000000000 / CURRENT_UPDATES_PER_SECOND;
    }

    private void updatesPerSecondForward(){
        double currentMultiplicity = CURRENT_UPDATES_PER_SECOND/(double)INITIAL_UPDATES_PER_SECOND;

        if(currentMultiplicity == 4) return;
        modifyUpdatesPerSecond(2); //Multiply the current UPS by 2
    }

    private void updatesPerSecondBackwards(){
        double currentMultiplicity = CURRENT_UPDATES_PER_SECOND/(double)INITIAL_UPDATES_PER_SECOND;

        if(currentMultiplicity == 0.25) return;
        modifyUpdatesPerSecond(0.5); //Multiply the current UPS by 0.5
    }
}

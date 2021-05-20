package presentation.controller;

import business.BattleBot;
import business.BattleModel;
import business.Card;
import business.GameModel;
import business.entities.Cards;
import business.entities.Songs;
import business.entities.Sounds;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import presentation.view.BattleEndedFrontPanel;
import presentation.view.BattleScreen;
import presentation.view.RoyaleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * BattleController is the Controller class for the {@link BattleScreen}.
 * <p>It will be linking both the {@link BattleScreen} and {@link BattleModel}.
 *
 * @see BattleScreen
 * @see BattleModel
 * @version 1.0
 */
public class BattleController extends ScreenController implements Runnable{

    private BattleScreen battleScreen;
    private BattleModel battleModel;

    private boolean gameRunning = true;

    private BattleBot battleBot;


    /**
     * Default BattleController Constructor.
     * @param royaleFrame The RoyaleFrame this controller will control
     * @param gameModel The GameModel this controller will control
     */
    public BattleController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }


    /**
     * Called when the BattleController is created.
     * <p>Creates the view for this BattleController ({@link BattleScreen}) and puts it onto the {@link RoyaleFrame}
     * this controller controls.
     * <p>Also, starts the {@link BattleBot} Thread and the {@code Game Loop} Thread (this)
     * @param showSettingsPanelOnStart Whether you want to show the settings panel on start or not
     */
    public void start(boolean showSettingsPanelOnStart){
        battleScreen = new BattleScreen(this, royaleFrame.getHeight());
        setPanelToListenForESCKey(battleScreen);

        royaleFrame.changeScreen(battleScreen, RoyaleFrame.BackgroundStyle.MENU);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.BATTLE);

        battleModel = new BattleModel(this, gameModel.getPlayer(), battleScreen.getBattlePanelSize());
        battleScreen.updateCardsToShow();

        battleBot = new BattleBot(battleModel, battleScreen.getBattlePanelSize());
        battleBot.startBot();

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
        final int MAX_FRAMES_PER_SECOND = 60;
        final int MAX_UPDATES_PER_SECOND = 60;

        final double uOPTIMAL_TIME = 1000000000 / MAX_UPDATES_PER_SECOND;
        final double fOPTIMAL_TIME = 1000000000 / MAX_FRAMES_PER_SECOND;

        double uDeltaTime = 0, fDeltaTime = 0;
        int frames = 0, updates = 0;
        long startTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while(gameRunning){
            long currentTime = System.nanoTime();
            uDeltaTime += (currentTime - startTime);
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
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if(e.getSource() instanceof BattleScreen.SouthPanel.CardPanel){
            BattleScreen.SouthPanel.CardPanel cardClicked = (BattleScreen.SouthPanel.CardPanel) e.getSource();
            battleScreen.setCardSelected(cardClicked.getCardHolding());
            SoundPlayer.getInstance().play(Sounds.BUTTON);
        }
        else if(e.getSource() instanceof BattleScreen.BattlePanel){
            Cards cardToThrow = battleScreen.getCardSelected();
            if(cardToThrow == null){ //If no card has been selected
                SoundPlayer.getInstance().play(Sounds.BUTTON);
                return;
            }

            boolean cardThrown = battleModel.throwCard(cardToThrow, Card.Status.PLAYER, e.getX(), e.getY());
            if(!cardThrown) SoundPlayer.getInstance().play(Sounds.BUTTON);
            else battleScreen.updateCardsToShow(); //Once a card is thrown, let's update cards
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
     * <p>It stops the GameLoop and everything related to the battle.
     *
     * @param playerCrowns The crowns that the user has won/lost
     */
    public void endGame(int playerCrowns){
        //This method will be called from the gameLoop Thread, and we want to stop that thread.
        //That's why we'll do it from the EDT
        gameRunning = false;
        battleBot.stopBot();

        //As this method will be called from the gameLoopThread, let's use the EDT to modify the View
        SwingUtilities.invokeLater(() -> {
            BattleEndedFrontPanel frontPanel = new BattleEndedFrontPanel(royaleFrame.getWidth(), royaleFrame.getHeight(), playerCrowns);

            BattleEndedFrontPanelController battleEndedFrontPanelController = new BattleEndedFrontPanelController(frontPanel, royaleFrame);
            battleEndedFrontPanelController.setFrontPanelVisibility(true);
            battleEndedFrontPanelController.canBeHidden(false);
        });
    }

}

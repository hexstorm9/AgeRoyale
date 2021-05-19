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
import presentation.view.BattleScreen;
import presentation.view.RoyaleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


/**
 * BattleController is the Controller class for the {@link BattleScreen}.
 * <p>It will be linking both the {@link BattleScreen} and {@link BattleModel}.
 *
 * @see BattleScreen
 * @see BattleModel
 * @version 1.0
 */
public class BattleController extends ScreenController implements Runnable, MouseListener{

    private BattleScreen battleScreen;
    private BattleModel battleModel;

    private boolean gameRunning = true;


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

        BattleBot battleBot = new BattleBot(battleModel, battleScreen.getBattlePanelSize());
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
                battleModel.update();
                updates++;
                uDeltaTime -= uOPTIMAL_TIME;
            }

            if(fDeltaTime >= fOPTIMAL_TIME){
                battleScreen.getBattlePanel().repaint();
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


    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof BattleScreen.SouthPanel.CardPanel){
            BattleScreen.SouthPanel.CardPanel cardClicked = (BattleScreen.SouthPanel.CardPanel) e.getSource();
            battleScreen.setCardSelected(cardClicked.getCardHolding());
            SoundPlayer.getInstance().play(Sounds.BUTTON);
        }
        else if(e.getSource() instanceof BattleScreen.BattlePanel){
            Cards cardToThrow = battleScreen.getCardSelected();
            if(cardToThrow == null){
                SoundPlayer.getInstance().play(Sounds.BUTTON);
                return;
            }

            boolean cardThrown = battleModel.throwCard(cardToThrow, Card.Status.PLAYER, e.getX(), e.getY());
            if(!cardThrown) SoundPlayer.getInstance().play(Sounds.BUTTON);
            else battleScreen.updateCardsToShow(); //Once a card is thrown, let's update cards
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e){}


    /**
     * To be called from the BattleModel whenever the gameStats have changed
     * @param playerTroops Current player troops on the map
     * @param enemyTroops Current enemy troops on the map
     */
    public void gameStatsChanged(int playerTroops, int enemyTroops) {
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
     * Ends the Game and Exits the application
     */
    public void endGameAndExit() {
        System.exit(0);
    }

    /**
     * Ends the Game and Logs Out
     */
    public void endGameAndLogOut(){
        gameModel.forgetPlayer();
        goToScreen(Screen.LOGIN_SCREEN, false);
    }


    private class LoadGoldOnBackground extends SwingWorker<Void, Void>{

        @Override
        protected Void doInBackground() throws Exception {
            return null;
        }
    }


}

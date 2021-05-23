package presentation.controller;

import business.GameModel;
import business.entities.BattleInfo;
import business.entities.Player;
import business.entities.Songs;
import presentation.sound.MusicPlayer;
import presentation.view.MainMenuScreen;
import presentation.view.RoyaleFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * {@code MainMenuController} is a {@link ScreenController} that will manage and listen to an instance of a
 * {@link MainMenuScreen} and put it onto the {@link RoyaleFrame} of the game.
 *
 * @see MainMenuScreen
 * @see ScreenController
 * @version 1.0
 */
public class MainMenuController extends ScreenController{

    private MainMenuScreen mainMenuScreen;
    private Thread updateRankingsThread;

    private CustomMouseAdapter mouseAdapter;


    /**
     * Default MainMenuController constructor.
     * @param royaleFrame The royaleFrame of the game
     * @param gameModel The gameModel of the game
     */
    public MainMenuController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel, null);
    }


    /**
     * {@inheritDoc}
     */
    public void start(boolean showSettingsPanelOnStart){
        mouseAdapter = new CustomMouseAdapter();

        mainMenuScreen = new MainMenuScreen(gameModel.getPlayer(), mouseAdapter, royaleFrame.getWidth(), royaleFrame.getHeight());
        mainMenuScreen.addButtonsListener(this);
        mainMenuScreen.addPanelsListener(this);
        mainMenuScreen.addLabelsListener(this);
        updateRankings();
        setPanelToListenForESCKey(mainMenuScreen);

        royaleFrame.changeScreen(mainMenuScreen);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.MAIN_MENU);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void buildSettingsPanel(){
        Player currentPlayer = gameModel.getPlayer();
        settingsPanel.addPlayerInformation(currentPlayer.getName(), currentPlayer.getArena(), currentPlayer.getCrowns());
        settingsPanel.addLogOutButton();
        settingsPanel.addDeleteAccountButton();
        settingsPanel.addLanguagesButton();
        settingsPanel.addCreditsButton();
    }


    /**
     * To be called when the log out button is pressed.
     * Tells the model to forget the current player, and goes to the LogIn screen
     */
    public void logOut(){
        gameModel.forgetPlayer();
        goToScreen(Screens.LOGIN_SCREEN);
    }


    /**
     * Updates the Rankings of the MainMenuScreen in a new separated Thread.
     */
    private void updateRankings(){
        //If we're already updating, return
        if(updateRankingsThread != null && updateRankingsThread.isAlive()) return;

        updateRankingsThread = new Thread(() -> {
            Player[] playersByCrowns = gameModel.getPlayersByCrowns(15);
            Player[] playersByWinRate = gameModel.getPlayersByWinRate(15);
            BattleInfo[] latestBattles = gameModel.getLatestBattles(15);

            //Once all information has been retrieved from the database, let's tell the EDT to
            //update the view
            SwingUtilities.invokeLater(() -> {
                mainMenuScreen.updateRankingsMenuInformation(playersByCrowns, playersByWinRate, latestBattles);
            });

        });

        updateRankingsThread.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case MainMenuScreen.BATTLE_MENU_BUTTON_ACTION_COMMAND:
                mainMenuScreen.putBattleMenuPanelToCenter();
                break;
            case MainMenuScreen.CARDS_MENU_BUTTON_ACTION_COMMAND:
                mainMenuScreen.putCardsMenuPanelToCenter();
                break;
            case MainMenuScreen.RANKINGS_MENU_BUTTON_ACTION_COMMAND:
                updateRankings(); //Starts updating the rankings
                mainMenuScreen.putRankingsMenuPanelToCenter();
                break;
            case MainMenuScreen.PLAY_BUTTON_COMMAND:
                mainMenuScreen.pauseAllComponents();
                goToScreen(Screens.LOADING_BATTLE_SCREEN);
                break;
            case MainMenuScreen.RANKING_BY_CROWNS_BUTTON_ACTION_COMMAND:
                mainMenuScreen.showRankingPlayersByCrowns();
                break;
            case MainMenuScreen.RANKING_BY_WINRATE_BUTTON_ACTION_COMMAND:
                mainMenuScreen.showRankingPlayersByWinRate();
                break;
            case MainMenuScreen.LATEST_BATTLES_BUTTON_ACTION_COMMAND:
                mainMenuScreen.showLatestBattles();
                break;
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof MainMenuScreen.CardsMenuPanel.CardPanel){
            MainMenuScreen.CardsMenuPanel.CardPanel cardPanelClicked = (MainMenuScreen.CardsMenuPanel.CardPanel) e.getSource();

            if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_1_ACTION_COMMAND)){
                System.out.println("Card one clicked");
            }
            else if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_2_ACTION_COMMAND)){
                System.out.println("Card two clicked");
            }
            else if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_3_ACTION_COMMAND)){
                System.out.println("Card three clicked");
            }
            else if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_4_ACTION_COMMAND)){
                System.out.println("Card four clicked");
            }
            else if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_5_ACTION_COMMAND)){
                System.out.println("Card five clicked");
            }
            else if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_6_ACTION_COMMAND)){
                System.out.println("Card six clicked");
            }
            else if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_7_ACTION_COMMAND)){
                System.out.println("Card seven clicked");
            }
            else if(cardPanelClicked.getActionCommand().equals(MainMenuScreen.CARD_PANEL_8_ACTION_COMMAND)){
                System.out.println("Card eight clicked");
            }
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


    class CustomMouseAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            if (e.getSource() instanceof JTable) {
                JTable currentTable = mainMenuScreen.getCurrentTable();
                if(currentTable == null) return;

                int row = currentTable.rowAtPoint(e.getPoint());

                String secondColumn = (String) currentTable.getValueAt(row, 1);
                try {
                    //If the second column is an integer, a player has been clicked
                    Integer.parseInt(secondColumn);

                    //A player has been clicked, so let's show its battles ->
                    //TODO: Show player battles

                } catch (NumberFormatException ex) {
                    //If it is not an integer, a battle has been clicked
                    final int battleId = Integer.parseInt((String)currentTable.getValueAt(row, 4));
                    goToScreen(Screens.LOADING_BATTLE_SCREEN, battleId);
                }

            }
        }
    }

}

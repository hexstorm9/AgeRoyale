package presentation.controller;

import business.GameModel;
import business.entities.Player;
import business.entities.Songs;
import presentation.sound.MusicPlayer;
import presentation.view.MainMenuScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


/**
 *
 */
public class MainMenuController extends ScreenController implements ActionListener, MouseListener{

    private MainMenuScreen mainMenuScreen;


    public MainMenuController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel, null);
    }


    public void start(boolean showSettingsPanelOnStart){
        mainMenuScreen = new MainMenuScreen(gameModel.getPlayer(), royaleFrame.getWidth(), royaleFrame.getHeight());
        mainMenuScreen.addButtonsListener(this);
        mainMenuScreen.addPanelsListener(this);
        mainMenuScreen.addLabelsListener(this);
        setPanelToListenForESCKey(mainMenuScreen);

        royaleFrame.changeScreen(mainMenuScreen);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.MAIN_MENU);
    }


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
                mainMenuScreen.putRankingsMenuPanelToCenter();
                break;
            case MainMenuScreen.PLAY_BUTTON_COMMAND:
                mainMenuScreen.pauseAllComponents();
                goToScreen(Screens.LOADING_BATTLE_SCREEN, 5);
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
}

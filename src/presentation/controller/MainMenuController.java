package presentation.controller;

import business.GameModel;
import business.entities.Player;
import business.entities.Songs;
import presentation.sound.MusicPlayer;
import presentation.view.MainMenuScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenuController extends ScreenController implements ActionListener, MouseListener{

    private MainMenuScreen mainMenuScreen;

    public MainMenuController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(boolean showSettingsPanelOnStart){
        mainMenuScreen = new MainMenuScreen(gameModel.getPlayer().getName(), gameModel.getPlayer().getCrowns(),
                gameModel.getPlayer().getBattleWins(), gameModel.getPlayer().getBattlePlays(),
                gameModel.getPlayer().getArena(), royaleFrame.getHeight());
        mainMenuScreen.addButtonsListener(this);
        mainMenuScreen.addLabelsListener(this);
        setPanelToListenForESCKey(mainMenuScreen);

        royaleFrame.changeScreen(mainMenuScreen, RoyaleFrame.BackgroundStyle.MENU);

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
        goToScreen(Screen.LOGIN_SCREEN, false);
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
                goToScreen(Screen.LOADING_BATTLE_SCREEN);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

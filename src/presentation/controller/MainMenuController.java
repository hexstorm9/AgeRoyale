package presentation.controller;

import business.GameModel;
import business.entities.Player;
import business.entities.Songs;
import presentation.sound.MusicPlayer;
import presentation.view.MainMenuScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;

public class MainMenuController extends ScreenController{

    private MainMenuScreen mainMenuScreen;

    public MainMenuController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(boolean showSettingsPanelOnStart){
        mainMenuScreen = new MainMenuScreen();
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
    }
}

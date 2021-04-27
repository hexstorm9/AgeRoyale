package presentation.controller;

import business.GameModel;
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


        if(showSettingsPanelOnStart){
            royaleFrame.setPanelOnTop(settingsPanel);
            royaleFrame.setPanelOnTopVisible(true);
            settingsPanelIsBeingShown = true;
        }
        MusicPlayer.getInstance().playInLoop(Songs.MAIN_MENU);
    }


    @Override
    public void buildSettingsPanel(){

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

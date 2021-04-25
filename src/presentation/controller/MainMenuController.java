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

    public void start(){
        mainMenuScreen = new MainMenuScreen();
        royaleFrame.changeScreen(mainMenuScreen, RoyaleFrame.BackgroundStyle.MENU);
        MusicPlayer.getInstance().playInLoop(Songs.MAIN_MENU);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

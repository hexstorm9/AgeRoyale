package presentation.controller;

import business.GameModel;
import presentation.sound.MusicPlayer;
import business.entities.Songs;
import presentation.view.LoginScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginScreenController implements ActionListener {

    private static LoginScreenController singletonInstance;
    private LoginScreen loginScreen;
    private RoyaleFrame royaleFrame;
    private GameModel gameModel;


    private LoginScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        this.royaleFrame = royaleFrame;
        this.gameModel = gameModel;
    }

    public static LoginScreenController getInstance(RoyaleFrame rf, GameModel gm){
        if(singletonInstance == null) singletonInstance = new LoginScreenController(rf, gm);
        return singletonInstance;
    }


    public void start(){
        loginScreen = new LoginScreen();
        royaleFrame.changeMainPane(loginScreen);
        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

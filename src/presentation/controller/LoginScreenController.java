package presentation.controller;

import business.GameModel;
import presentation.sound.MusicPlayer;
import business.entities.Songs;
import presentation.view.LoginScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginScreenController extends ScreenController implements ActionListener {

    private LoginScreen loginScreen;

    public LoginScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(){
        loginScreen = new LoginScreen();
        loginScreen.addButtonsListener(this);
        royaleFrame.changeScreen(loginScreen, RoyaleFrame.BackgroundStyle.MENU);
        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(LoginScreen.LOGIN_BUTTON_ACTION_COMMAND)){
            gameModel.checkLogin(loginScreen.getTextUsernameTextField(), loginScreen.getTextPasswordTextField());
        }
    }
}

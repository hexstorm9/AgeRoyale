package presentation.controller;

import business.GameModel;
import business.entities.Sounds;
import presentation.sound.MusicPlayer;
import business.entities.Songs;
import presentation.sound.SoundPlayer;
import presentation.view.LoginScreen;
import presentation.view.RoyaleFrame;
import presentation.view.customcomponents.RoyaleLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class LoginScreenController extends ScreenController implements ActionListener, MouseListener {

    private LoginScreen loginScreen;

    public LoginScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(){
        loginScreen = new LoginScreen(royaleFrame.getHeight());
        loginScreen.addButtonListener(this);
        loginScreen.addLabelsListener(this);
        royaleFrame.changeScreen(loginScreen, RoyaleFrame.BackgroundStyle.MENU);
        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(LoginScreen.LOGIN_BUTTON_ACTION_COMMAND)){
            SoundPlayer.getInstance().play(Sounds.BUTTON);
            loginScreen.pauseAllComponents();
            gameModel.checkLogin(loginScreen.getTextUsernameTextField(), loginScreen.getTextPasswordTextField());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(LoginScreen.FORGOT_PASSWORD_LABEL_ACTION_COMMAND)){
                loginScreen.pauseAllComponents();
                SoundPlayer.getInstance().play(Sounds.BUTTON);
                goToScreen(Screen.PASSWORD_FORGOTTEN_SCREEN);
            }
            else if(labelClicked.getActionCommand().equals(LoginScreen.REGISTER_LABEL_ACTION_COMMAND)){
                loginScreen.pauseAllComponents();
                SoundPlayer.getInstance().play(Sounds.BUTTON);
                goToScreen(Screen.REGISTER_SCREEN);
            }

        }
    }


    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

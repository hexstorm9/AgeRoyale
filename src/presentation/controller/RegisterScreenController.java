package presentation.controller;

import business.GameModel;
import business.entities.Songs;
import business.entities.Sounds;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import presentation.view.LoginScreen;
import presentation.view.RegisterScreen;
import presentation.view.RoyaleFrame;
import presentation.view.customcomponents.RoyaleLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class RegisterScreenController extends ScreenController implements ActionListener, MouseListener {

    private RegisterScreen registerScreen;

    public RegisterScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(){
        registerScreen = new RegisterScreen(royaleFrame.getHeight());
        registerScreen.addButtonListener(this);
        registerScreen.addLabelsListener(this);
        royaleFrame.changeScreen(registerScreen, RoyaleFrame.BackgroundStyle.MENU);
        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(RegisterScreen.REGISTER_BUTTON_ACTION_COMMAND)){
            registerScreen.pauseAllComponents();
            SoundPlayer.getInstance().play(Sounds.BUTTON);
            // gameModel.checkLogin(registerScreen.getTextUsernameTextField(), registerScreen.getTextPasswordTextField());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(RegisterScreen.LOGIN_LABEL_ACTION_COMMAND)){
                registerScreen.pauseAllComponents();
                SoundPlayer.getInstance().play(Sounds.BUTTON);
                goToScreen(Screen.LOGIN_SCREEN);
            }
        }
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

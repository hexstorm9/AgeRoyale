package presentation.controller;

import business.GameModel;
import presentation.view.PasswordForgottenScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;


public class PasswordForgottenScreenController extends ScreenController{

    private PasswordForgottenScreen passwordForgottenScreen;

    public PasswordForgottenScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(){
        passwordForgottenScreen = new PasswordForgottenScreen();
        royaleFrame.changeScreen(passwordForgottenScreen, RoyaleFrame.BackgroundStyle.MENU);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

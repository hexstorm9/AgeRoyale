package presentation.controller;

import business.GameModel;
import presentation.view.RegisterScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;


public class RegisterScreenController extends ScreenController{

    private RegisterScreen registerScreen;

    public RegisterScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }


    public void start(){
        registerScreen = new RegisterScreen();
        royaleFrame.changeScreen(registerScreen, RoyaleFrame.BackgroundStyle.MENU);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

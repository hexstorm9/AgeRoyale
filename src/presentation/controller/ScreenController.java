package presentation.controller;

import business.GameModel;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionListener;

public abstract class ScreenController implements ActionListener {

    protected RoyaleFrame royaleFrame;
    protected GameModel gameModel;

    public enum Screen{
        SPLASH_SCREEN,
        LOGIN_SCREEN,
        REGISTER_SCREEN,
        PASSWORD_FORGOTTEN_SCREEN,
        MAIN_MENU,
        BATTLE
    }


    public ScreenController(RoyaleFrame rf, GameModel gm){
        royaleFrame = rf;
        gameModel = gm;
    }


    public abstract void start();

    public void goToScreen(Screen screen){
        switch(screen){
            case LOGIN_SCREEN -> new LoginScreenController(royaleFrame, gameModel).start();
            case REGISTER_SCREEN -> new RegisterScreenController(royaleFrame, gameModel).start();
            case PASSWORD_FORGOTTEN_SCREEN -> new PasswordForgottenScreenController(royaleFrame, gameModel).start();
            case MAIN_MENU -> new MainMenuController(royaleFrame, gameModel).start();
            case BATTLE -> new BattleController(royaleFrame, gameModel).start();
            default -> new SplashScreenController(royaleFrame, gameModel).start();
        }
    }

    public void showCriticalErrorAndExit(String errorMessage){
        royaleFrame.showCriticalErrorAndExitApplication(errorMessage);

    }


}

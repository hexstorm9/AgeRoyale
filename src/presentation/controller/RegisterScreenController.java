package presentation.controller;

import business.GameModel;
import presentation.view.RegisterScreen;
import presentation.view.RoyaleFrame;


public class RegisterScreenController {

    private static RegisterScreenController singletonInstance;
    private RegisterScreen registerScreen;
    private RoyaleFrame royaleFrame;
    private GameModel gameModel;


    private RegisterScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        this.royaleFrame = royaleFrame;
        this.gameModel = gameModel;
    }

    public static RegisterScreenController getInstance(RoyaleFrame rf, GameModel gm){
        if(singletonInstance == null) singletonInstance = new RegisterScreenController(rf, gm);
        return singletonInstance;
    }


    public void start(){

    }

}

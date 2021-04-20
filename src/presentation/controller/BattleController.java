package presentation.controller;

import business.GameModel;
import presentation.view.BattleScreen;
import presentation.view.RoyaleFrame;

import java.awt.*;

public class BattleController {

    private static BattleController singletonInstance;
    private BattleScreen battleScreen;
    private RoyaleFrame royaleFrame;
    private GameModel gameModel;


    private BattleController(RoyaleFrame royaleFrame, GameModel gameModel){
        this.royaleFrame = royaleFrame;
        this.gameModel = gameModel;
    }

    public static BattleController getInstance(RoyaleFrame rf, GameModel gm){
        if(singletonInstance == null) singletonInstance = new BattleController(rf, gm);
        return singletonInstance;
    }

}

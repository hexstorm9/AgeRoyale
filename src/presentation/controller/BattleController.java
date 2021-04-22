package presentation.controller;

import business.GameModel;
import presentation.view.BattleScreen;
import presentation.view.RoyaleFrame;

import java.awt.event.ActionEvent;


public class BattleController extends ScreenController{

    private BattleScreen battleScreen;

    public BattleController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }


    public void start(){
        battleScreen = new BattleScreen();
        royaleFrame.changeScreen(battleScreen, RoyaleFrame.BackgroundStyle.BATTLE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

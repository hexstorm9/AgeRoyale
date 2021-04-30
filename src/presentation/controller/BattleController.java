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


    public void start(boolean showSettingsPanelOnStart){
        battleScreen = new BattleScreen();
        setPanelToListenForESCKey(battleScreen);

        royaleFrame.changeScreen(battleScreen, RoyaleFrame.BackgroundStyle.BATTLE);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);
    }

    @Override
    public void buildSettingsPanel(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

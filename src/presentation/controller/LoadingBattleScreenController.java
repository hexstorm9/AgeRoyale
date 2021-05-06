package presentation.controller;

import business.GameModel;
import presentation.view.LoadingBattleScreen;
import presentation.view.RoyaleFrame;
import presentation.view.SplashScreen;

import java.awt.event.ActionEvent;

public class LoadingBattleScreenController extends ScreenController{
    private LoadingBattleScreen loadingBattleScreen;

    public LoadingBattleScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    @Override
    public void start(boolean showSettingsPanelOnStart) {
        loadingBattleScreen = new LoadingBattleScreen(royaleFrame.getHeight());
        setPanelToListenForESCKey(loadingBattleScreen);

        royaleFrame.changeScreen(loadingBattleScreen, RoyaleFrame.BackgroundStyle.MENU);

    }

    @Override
    public void buildSettingsPanel() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

package presentation.controller;

import business.BattleModel;
import business.GameModel;
import business.entities.Songs;
import presentation.sound.MusicPlayer;
import presentation.view.BattleScreen;
import presentation.view.RoyaleFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class BattleController extends ScreenController implements Runnable, MouseListener {

    private BattleScreen battleScreen;
    private BattleModel battleModel;

    private boolean gameRunning = true;


    public BattleController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }


    public void start(boolean showSettingsPanelOnStart){
        battleScreen = new BattleScreen(this);
        setPanelToListenForESCKey(battleScreen);

        royaleFrame.changeScreen(battleScreen, RoyaleFrame.BackgroundStyle.MENU);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.BATTLE);

        battleModel = new BattleModel(battleScreen.getBattlePanelSize());


        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }

    @Override
    public void buildSettingsPanel(){
        settingsPanel.addCreditsButton();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void run(){
        final int MAX_FRAMES_PER_SECOND = 60;
        final int MAX_UPDATES_PER_SECOND = 60;

        final double uOPTIMAL_TIME = 1000000000 / MAX_UPDATES_PER_SECOND;
        final double fOPTIMAL_TIME = 1000000000 / MAX_FRAMES_PER_SECOND;

        double uDeltaTime = 0, fDeltaTime = 0;
        int frames = 0, updates = 0;
        long startTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while(gameRunning){
            long currentTime = System.nanoTime();
            uDeltaTime += (currentTime - startTime);
            fDeltaTime += (currentTime - startTime);
            startTime = currentTime;

            if(uDeltaTime >= uOPTIMAL_TIME){
                battleModel.update();
                updates++;
                uDeltaTime -= uOPTIMAL_TIME;
            }

            if(fDeltaTime >= fOPTIMAL_TIME){
                battleScreen.getBattlePanel().repaint();
                frames++;
                fDeltaTime -= fOPTIMAL_TIME;
            }


            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("UPS --> " + updates + ".  FPS --> " + frames);
                updates = 0;
                frames = 0;
                timer += 1000;
            }

        }
   }



   public void paintBattle(Graphics g){
        battleModel.draw(g);
   }



    @Override
    public void mouseClicked(MouseEvent e) {

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

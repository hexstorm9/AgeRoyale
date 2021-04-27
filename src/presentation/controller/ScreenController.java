package presentation.controller;

import business.GameModel;
import business.entities.Sounds;
import presentation.sound.SoundPlayer;
import presentation.view.RoyaleFrame;
import presentation.view.SettingsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public abstract class ScreenController implements ActionListener{

    protected RoyaleFrame royaleFrame;
    protected GameModel gameModel;

    protected SettingsPanel settingsPanel;

    protected boolean settingsPanelIsBeingShown;


    public enum Screen{
        SPLASH_SCREEN,
        LOGIN_SCREEN,
        REGISTER_SCREEN,
        PASSWORD_FORGOTTEN_SCREEN,
        MAIN_MENU,
        BATTLE
    }


    /**
     * Default Constructor for the class.
     * <p><b>ALWAYS call it on subclasses' constructor</b></p>
     *
     * @param rf The RoyaleFrame that will control this controller
     * @param gm The GameModel that this class will interact with
     */
    public ScreenController(RoyaleFrame rf, GameModel gm){
        royaleFrame = rf;
        gameModel = gm;


        settingsPanel = new SettingsPanel(rf.getWidth(), rf.getHeight());
        SettingsPanelController settingsPanelController = new SettingsPanelController(settingsPanel);
        settingsPanelIsBeingShown = false;

        buildSettingsPanel();
        settingsPanel.addButtonsListener(settingsPanelController);
        settingsPanel.addMouseListener(settingsPanelController);
        settingsPanel.addLabelsListener(settingsPanelController);
    }


    /**
     * This method has to be called whenever a new {@link ScreenController} is created. It will create the view
     * for that controller and assign it to the {@link RoyaleFrame} by using {@link RoyaleFrame#changeScreen(JPanel, RoyaleFrame.BackgroundStyle)}
     *
     * <p>Furthermore, as all ScreenControllers control a Screen which has a SettingsPanel, you can either start displaying
     * the new Screen or display the new Screen + the SettingsPanel on top of that new Screen
     *
     * @param showSettingsPanelOnStart Whether you want to show the settings panel on start or not
     */
    public abstract void start(boolean showSettingsPanelOnStart);



    /**
     * Creates a new {@link ScreenController} depending on the Screen that we want to go to, and calls the method
     * {@link ScreenController#start(boolean)} on that controller (which creates the Screen and puts it into the frame)
     *
     * @param screen {@link Screen} which we want to go to
     */
    public void goToScreen(Screen screen){
        switch(screen){
            case LOGIN_SCREEN -> new LoginScreenController(royaleFrame, gameModel).start(false); //Never start the splashScreen with a settingsPanel being shown //Never start the splashScreen with a settingsPanel being shown //Never start the splashScreen with a settingsPanel being shown //Never start the splashScreen with a settingsPanel being shown
            case REGISTER_SCREEN -> new RegisterScreenController(royaleFrame, gameModel).start(settingsPanelIsBeingShown);
            case PASSWORD_FORGOTTEN_SCREEN -> new PasswordForgottenScreenController(royaleFrame, gameModel).start(settingsPanelIsBeingShown);
            case MAIN_MENU -> new MainMenuController(royaleFrame, gameModel).start(settingsPanelIsBeingShown);
            case BATTLE -> new BattleController(royaleFrame, gameModel).start(settingsPanelIsBeingShown);
            default -> new SplashScreenController(royaleFrame, gameModel).start(settingsPanelIsBeingShown);
        }
    }


    /**
     * The {@link SettingsPanel} class is a <b>customizable panel</b>. As each Screen needs a settings panel,
     * this method will allow the subclass to configure its own settings panel according to its preferences.
     * <p>To configure the settingsPanel for the current Screen, use the attribute of this class {@link ScreenController#settingsPanel}
     * The object is already created, so start customizing it directly.
     *
     * <p>There's no need to call this method, it should only be implemented</p>
     *
     * @see SettingsPanel
     */
    public abstract void buildSettingsPanel();


    /**
     * To be called whenever the {@link ScreenController#settingsPanel} needs to appear on top of everything else.
     */
    public void showSettingsPanel(){
        showPanelOnTopOfEverythingElse(settingsPanel);
    }


    /**
     * Puts a {@link JPanel} on top of everything else, and shows it.
     * <p>In order to hide it, call {@link ScreenController#hidePanelOnTopOfEverythingElse()}
     */
    public void showPanelOnTopOfEverythingElse(JPanel panelToPutOnTopOfEverythingElse){
        royaleFrame.setPanelOnTop(panelToPutOnTopOfEverythingElse);
        royaleFrame.setPanelOnTopVisible(true);
    }


    /**
     * Hides the {@link JPanel} that is on top of everything else.
     */
    public void hidePanelOnTopOfEverythingElse(){
        royaleFrame.setPanelOnTopVisible(false);
    }


    /**
     * Given a {@code JPanel}, this method will make it listen to the {@code ESC} key. When pressed, it will automatically call
     * {@link ScreenController#escKeyPressed()} always, even when the focus is not on that {@code JPanel}.
     *
     * <p>We can't do this using {@link KeyListener} as the focus will not always be on the JPanel. Instead, we'll use
     * <a href="https://stackoverflow.com/questions/22741215/how-to-use-key-bindings-instead-of-key-listeners">Key Bindings</a>
     *
     * @see <a href="https://stackoverflow.com/questions/22741215/how-to-use-key-bindings-instead-of-key-listeners">KeyBindings</a>
     * @param panel The {@code JPanel} that will always listen to the ESC key
     */
    public void setPanelToListenForESCKey(JPanel panel){
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "showSettings");
        panel.getActionMap().put("showSettings", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                escKeyPressed();
            }
        });

    }


    /**
     * To be called when the ESC key is being pressed.
     * <p>It will show the {@link ScreenController#settingsPanel} of this {@link ScreenController}
     */
    protected void escKeyPressed(){
            if(settingsPanelIsBeingShown) hidePanelOnTopOfEverythingElse();
            else showSettingsPanel();
            SoundPlayer.getInstance().play(Sounds.BUTTON);
            settingsPanelIsBeingShown = !settingsPanelIsBeingShown;
    }

}

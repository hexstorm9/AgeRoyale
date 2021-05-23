package presentation.controller;

import business.GameModel;
import presentation.view.FrontPanel;
import presentation.view.RoyaleFrame;
import presentation.view.SettingsPanel;

import javax.swing.*;
import java.awt.event.*;


/**
 * Parent class of all {@code ScreenControllers} of the Game.
 * <p>It controls a {@link presentation.view.Screen}, and puts it onto the {@link RoyaleFrame} of the Game.
 * <p>Defines and declares all basic functionality of a {@code ScreenController}, and forces sub-classes to implement some
 * methods that all {@code ScreenControllers} must have.
 *
 * @version 1.0
 * @see presentation.view.Screen
 */
public abstract class ScreenController implements ActionListener, MouseListener{

    protected RoyaleFrame royaleFrame;
    protected GameModel gameModel;

    protected SettingsPanel settingsPanel;
    protected SettingsPanelController settingsPanelController;


    private FrontPanelController currentFrontPanelController;


    /**
     * Defines all the Screens of the Game
     */
    public enum Screens{
        /**
         * Defines the SplashScreen
         */
        SPLASH_SCREEN,
        /**
         * Defines the LoginScreen
         */
        LOGIN_SCREEN,
        /**
         * Defines the RegisterScreen
         */
        REGISTER_SCREEN,
        /**
         * Defines the PasswordForgottenScreen
         */
        PASSWORD_FORGOTTEN_SCREEN,
        /**
         * Defines the MainMenuScreen
         */
        MAIN_MENU,
        /**
         * Defines the LoadingBattleScreen
         */
        LOADING_BATTLE_SCREEN,
        /**
         * Defines the BattleScreen
         */
        BATTLE
    }


    /**
     * Default Constructor for the class.
     * <p><b>ALWAYS call it on subclasses' constructor</b></p>
     *
     * @param rf The RoyaleFrame that will control this controller
     * @param gm The GameModel that this class will interact with
     * @param extraInfo Some screens may need extra information so as to be constructed. If not, it'll be {@code null}
     */
    public ScreenController(RoyaleFrame rf, GameModel gm, Object extraInfo){
        royaleFrame = rf;
        gameModel = gm;
        rf.setFrontPanelVisible(false); //Clear frontPanel from the royaleFrame when starting a new Screen

        settingsPanel = new SettingsPanel(rf.getWidth(), rf.getHeight());
        settingsPanelController = new SettingsPanelController(settingsPanel, this, rf);

        buildSettingsPanel();
        settingsPanel.addButtonsListener(settingsPanelController);
        settingsPanel.addMouseListener(settingsPanelController);
        settingsPanel.addLabelsListener(settingsPanelController);
        settingsPanel.addSlidersListener(settingsPanelController);
    }


    /**
     * This method has to be called whenever a new {@link ScreenController} is created. It will create the view
     * for that controller and assign it to the {@link RoyaleFrame} by using {@link RoyaleFrame#changeScreen(JPanel)}
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
     * @param screen {@link Screens} which we want to go to
     */
    public void goToScreen(Screens screen){
        goToScreen(screen, null);
    }


    /**
     * Creates a new {@link ScreenController} depending on the Screen that we want to go to, and calls the method
     * {@link ScreenController#start(boolean)} on that controller (which creates the Screen and puts it into the frame)
     *
     * @param screen {@link Screens} which we want to go to
     * @param extraInfo Some screens may need extra information so as to be constructed. If not, set to {@code null}
     */
    public void goToScreen(Screens screen, Object extraInfo){
        //Now that we know that we need to change screen, let's check whether that
        //new screen should start with the settingsPanel or not

        boolean settingsPanelIsBeingShown = false;
        //If the front panel is visible and the current FrontPanel is the SettingsPanel, let's set the flag to true
        if(currentFrontPanelController != null && currentFrontPanelController.isFrontPanelVisible()
                && currentFrontPanelController == settingsPanelController)
            settingsPanelIsBeingShown = true;

        goToScreen(screen, settingsPanelIsBeingShown, extraInfo);
    }


    /**
     * Creates a new {@link ScreenController} depending on the Screen that we want to go to, and calls the method
     * {@link ScreenController#start(boolean)} on that controller (which creates the Screen and puts it into the frame)
     *
     * @param screen {@link Screens} which we want to go to
     * @param startWithSettingsPanelEnabled Whether the next screen should start with the settingsPanel enabled or not
     * @param extraInfo Some screens may need extra information so as to be constructed. If not, set to {@code null}
     */
    public void goToScreen(Screens screen, boolean startWithSettingsPanelEnabled, Object extraInfo){
        switch(screen){
            case LOGIN_SCREEN -> new LoginScreenController(royaleFrame, gameModel).start(startWithSettingsPanelEnabled);
            case REGISTER_SCREEN -> new RegisterScreenController(royaleFrame, gameModel).start(startWithSettingsPanelEnabled);
            case PASSWORD_FORGOTTEN_SCREEN -> new PasswordForgottenScreenController(royaleFrame, gameModel).start(startWithSettingsPanelEnabled);
            case MAIN_MENU -> new MainMenuController(royaleFrame, gameModel).start(startWithSettingsPanelEnabled);
            case LOADING_BATTLE_SCREEN -> new LoadingBattleScreenController(royaleFrame, gameModel, extraInfo).start(startWithSettingsPanelEnabled);
            case BATTLE -> new BattleController(royaleFrame, gameModel, extraInfo).start(startWithSettingsPanelEnabled);
            default -> new SplashScreenController(royaleFrame, gameModel).start(false); //Never start the splashScreen with a settingsPanel being shown
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
     * Puts a {@link JPanel} on top of everything else, and shows it.
     * <p>In order to hide it, call {@link ScreenController#hideFrontPanel()}
     *
     * @param panelToPutOnTopOfEverythingElse The frontPanel to be shown
     * @param frontPanelController The frontPanelController of the frontPanel that wants to be shown
     */
    public void showFrontPanel(FrontPanel panelToPutOnTopOfEverythingElse, FrontPanelController frontPanelController){
        currentFrontPanelController = frontPanelController;
        currentFrontPanelController.setFrontPanelVisibility(true);
    }


    /**
     * Hides the {@link JPanel} that is on top of everything else.
     */
    public void hideFrontPanel(){
        if(currentFrontPanelController != null)
            currentFrontPanelController.setFrontPanelVisibility(false);
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
        if(currentFrontPanelController != null && currentFrontPanelController.isFrontPanelVisible()
                && currentFrontPanelController.canBeHidden()) currentFrontPanelController.setFrontPanelVisibility(false);
        else showFrontPanel(settingsPanel, settingsPanelController);
    }


    /**
     * Returns the GameModel from this Controller.
     * @return The GameModel that this controller has
     */
    public GameModel getGameModel(){
        return gameModel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {}


    /**
     * Invoked when a mouse has been pressed on a component that this controller is listening to.
     * <p>When overridden from a sub-class, <b>always call super.mousePressed(e)</b>
     * @param e The event that occurred
     */
    @Override
    public void mousePressed(MouseEvent e){

    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}

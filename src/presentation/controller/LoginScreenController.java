package presentation.controller;

import business.GameModel;
import business.LoginException;
import business.entities.LanguageManager;
import business.entities.Sentences;
import presentation.sound.MusicPlayer;
import business.entities.Songs;
import presentation.view.LoginScreen;
import presentation.view.RoyaleFrame;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


/**
 * {@code LoginScreenController} is a {@link ScreenController} that will manage and listen to an instance of a
 * {@link LoginScreen} and put it onto the {@link RoyaleFrame} of the game.
 *
 * <p>Whenever a new login attempt event is created, this class will start a new {@code thread} to check whether
 * that login is correct or not. If it is, it will create and start a new {@link MainMenuController}
 *
 * @see LoginScreen
 * @version 1.0
 */
public class LoginScreenController extends ScreenController {

    private LoginScreen loginScreen;


    /**
     * Default LoginScreenController constructor.
     * @param royaleFrame The royaleFrame of the game
     * @param gameModel The gameModel of the game
     */
    public LoginScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel, null);
    }


    /**
     * {@inheritDoc}
     */
    public void start(boolean settingsPanelIsBeingShown){
        loginScreen = new LoginScreen(royaleFrame.getHeight());
        loginScreen.addButtonListener(this);
        loginScreen.addLabelsListener(this);
        setPanelToListenForESCKey(loginScreen);

        royaleFrame.changeScreen(loginScreen);

        if(settingsPanelIsBeingShown)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void buildSettingsPanel(){
        settingsPanel.addLanguagesButton();
        settingsPanel.addCreditsButton();
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(LoginScreen.LOGIN_BUTTON_ACTION_COMMAND)){
            loginScreen.pauseAllComponents();
            loginScreen.emptyErrorMessage();
            new CheckLoginInBackground().execute();
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(LoginScreen.FORGOT_PASSWORD_LABEL_ACTION_COMMAND)){
                loginScreen.pauseAllComponents();
                goToScreen(Screens.PASSWORD_FORGOTTEN_SCREEN);
            }
            else if(labelClicked.getActionCommand().equals(LoginScreen.REGISTER_LABEL_ACTION_COMMAND)){
                loginScreen.pauseAllComponents();
                goToScreen(Screens.REGISTER_SCREEN);
            }

        }
    }



    private class CheckLoginInBackground extends SwingWorker<String, Void>{

        @Override
        protected String doInBackground(){

            try{
                gameModel.checkLoginAndLoadUser(loginScreen.getTextUsernameTextField(), loginScreen.getTextPasswordTextField());
            }catch(LoginException e){
                switch(e.getExceptionCause()){
                    case NAME_DOES_NOT_EXIST:
                        return LanguageManager.getSentence(Sentences.NAME_DOES_NOT_EXIST);
                    case EMAIL_DOES_NOT_EXIST:
                        return LanguageManager.getSentence(Sentences.EMAIL_DOES_NOT_EXIST);
                    case INCORRECT_PASSWORD:
                        return LanguageManager.getSentence(Sentences.INCORRECT_PASSWORD);
                }
            }catch(SQLException e){
                e.printStackTrace();
                return LanguageManager.getSentence(Sentences.DATABASE_ERROR);
            }

            return null;
        }

        @Override
        protected void done() {
            String errorMessage;
            try{
                errorMessage = get();
            }catch(Exception e){
                errorMessage = LanguageManager.getSentence(Sentences.UNKNOWN_ERROR);
                System.err.println("Error in the LogIn Worker Thread");
                e.printStackTrace();
            }

            if(errorMessage != null){ //If there is some error, let's show it and re-enable components
                loginScreen.setErrorMessage(errorMessage);
                loginScreen.enableAllComponents();
            }
            else goToScreen(Screens.MAIN_MENU); //If there is no error, let's go to the main menu
        }
    }


}

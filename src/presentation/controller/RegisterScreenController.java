package presentation.controller;

import business.GameModel;
import business.RegistrationException;
import business.entities.LanguageManager;
import business.entities.Sentences;
import business.entities.Songs;
import presentation.sound.MusicPlayer;
import presentation.view.RegisterScreen;
import presentation.view.RoyaleFrame;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;


/**
 * {@code RegisterScreenController} is a {@link ScreenController} that will manage and listen to an instance of
 * a {@link RegisterScreen} and put it onto the {@link RoyaleFrame} of the game.
 *
 * <p>When the user submits a new register event (with its information), this class will initiate a new
 * {@code thread} so as to check (using the {@link GameModel} whether the information is correct or not). If it
 * is, the registration will be successful and the new user will be redirected to the {@link presentation.view.MainMenuScreen}
 *
 * @see RegisterScreen
 * @version 1.0
 */
public class RegisterScreenController extends ScreenController {

    private RegisterScreen registerScreen;

    /**
     * Default RegisterScreenController constructor.
     * @param royaleFrame The royaleFrame of the game
     * @param gameModel The gameModel of the game
     */
    public RegisterScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel, null);
    }


    /**
     * {@inheritDoc}
     */
    public void start(boolean showSettingsPanelOnStart){
        registerScreen = new RegisterScreen(royaleFrame.getHeight());
        registerScreen.addLabelsListener(this);
        registerScreen.addButtonListener(this);
        setPanelToListenForESCKey(registerScreen);

        royaleFrame.changeScreen(registerScreen);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }


    /**
     * {@inheritDoc}
     */
    public void buildSettingsPanel(){
        settingsPanel.addLanguagesButton();
        settingsPanel.addCreditsButton();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(RegisterScreen.REGISTER_BUTTON_ACTION_COMMAND)){
            registerScreen.pauseAllComponents();
            registerScreen.emptyErrorMessage();
            new RegisterUserInBackground().execute();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(RegisterScreen.LOGIN_LABEL_ACTION_COMMAND)){
                registerScreen.pauseAllComponents();
                goToScreen(Screens.LOGIN_SCREEN);
            }
        }
    }



    private class RegisterUserInBackground extends SwingWorker<String, Void> {

        @Override
        protected String doInBackground(){
            //If both passwords are not equal, do not continue
            if(Arrays.equals(registerScreen.getTextPasswordTextField(), registerScreen.getTextConfirmPasswordTextField()) == false){
                return LanguageManager.getSentence(Sentences.PASSWORDS_DONT_MATCH);
            }

            try{
                //Let's try to create a new user and catch all possible errors
                gameModel.createNewUser(registerScreen.getTextUsernameTextField(), registerScreen.getTextMailTextField(), registerScreen.getTextPasswordTextField());
            }catch(RegistrationException e){
                switch(e.getExceptionCause()){
                    case PASSWORD_NOT_SECURE:
                        return LanguageManager.getSentence(Sentences.PASSWORD_NOT_SECURE);
                    case NAME_NOT_WELL_FORMATTED:
                        return LanguageManager.getSentence(Sentences.NAME_NOT_WELL_FORMATTED);
                    case EMAIL_NOT_VALID:
                        return LanguageManager.getSentence(Sentences.EMAIL_NOT_VALID);
                    case NAME_ALREADY_IN_USE:
                        return LanguageManager.getSentence(Sentences.NAME_ALREADY_IN_USE);
                    case EMAIL_ALREADY_IN_USE:
                        return LanguageManager.getSentence(Sentences.EMAIL_ALREADY_IN_USE);
                }
            }catch(SQLException e){
                e.printStackTrace();
                return LanguageManager.getSentence(Sentences.DATABASE_ERROR);
            }

            return null;
        }

        @Override
        protected void done() {
            String errorMessage = null;
            try{
                errorMessage = get();
            }catch(Exception e){
                errorMessage = LanguageManager.getSentence(Sentences.UNKNOWN_ERROR);
                System.err.println("Error in the RegisterUser Worker Thread");
                e.printStackTrace();
            }

            if(errorMessage != null){ //If there is some error, let's show it and re-enable components
                registerScreen.setErrorMessage(errorMessage);
                registerScreen.enableAllComponents();
            }
            else goToScreen(Screens.MAIN_MENU); //If there is no error, let's go to the main menu
        }
    }
}

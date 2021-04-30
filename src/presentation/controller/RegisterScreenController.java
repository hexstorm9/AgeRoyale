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
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Arrays;


public class RegisterScreenController extends ScreenController implements ActionListener, MouseListener {

    private RegisterScreen registerScreen;

    public RegisterScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(boolean showSettingsPanelOnStart){
        registerScreen = new RegisterScreen(royaleFrame.getHeight());
        registerScreen.addLabelsListener(this);
        registerScreen.addButtonListener(this);
        setPanelToListenForESCKey(registerScreen);

        royaleFrame.changeScreen(registerScreen, RoyaleFrame.BackgroundStyle.MENU);

        if(showSettingsPanelOnStart)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }


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
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(RegisterScreen.LOGIN_LABEL_ACTION_COMMAND)){
                registerScreen.pauseAllComponents();
                goToScreen(Screen.LOGIN_SCREEN);
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


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
            else goToScreen(Screen.MAIN_MENU); //If there is no error, let's go to the main menu
        }
    }

}

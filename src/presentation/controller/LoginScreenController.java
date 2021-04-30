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
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;



public class LoginScreenController extends ScreenController implements ActionListener, MouseListener {

    private LoginScreen loginScreen;


    public LoginScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel);
    }

    public void start(boolean settingsPanelIsBeingShown){
        loginScreen = new LoginScreen(royaleFrame.getHeight());
        loginScreen.addButtonListener(this);
        loginScreen.addLabelsListener(this);
        setPanelToListenForESCKey(loginScreen);

        royaleFrame.changeScreen(loginScreen, RoyaleFrame.BackgroundStyle.MENU);

        if(settingsPanelIsBeingShown)
            showFrontPanel(settingsPanel, settingsPanelController);

        MusicPlayer.getInstance().playInLoop(Songs.MENU);
    }


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
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(LoginScreen.FORGOT_PASSWORD_LABEL_ACTION_COMMAND)){
                loginScreen.pauseAllComponents();
                goToScreen(Screen.PASSWORD_FORGOTTEN_SCREEN);
            }
            else if(labelClicked.getActionCommand().equals(LoginScreen.REGISTER_LABEL_ACTION_COMMAND)){
                loginScreen.pauseAllComponents();
                goToScreen(Screen.REGISTER_SCREEN);
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
            String errorMessage = null;
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
            else goToScreen(Screen.MAIN_MENU); //If there is no error, let's go to the main menu
        }
    }


}

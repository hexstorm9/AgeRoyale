package presentation.controller;

import business.LoginException;
import business.RegistrationException;
import business.entities.Language;
import business.entities.LanguageManager;
import business.entities.Sentences;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import presentation.view.RoyaleFrame;
import presentation.view.SettingsPanel;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyaleSlider;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class SettingsPanelController extends FrontPanelController implements ChangeListener{

    //We need to have a reference to the ScreenController that created this SettingsPanelController
    //so as to send to it major events (that require changing the whole screen) such as changing the
    //language or logging out
    private ScreenController screenController;

    public SettingsPanelController(SettingsPanel settingsPanelToControl, ScreenController screenControllerToRedirectActions, RoyaleFrame royaleFrame){
        super(settingsPanelToControl, royaleFrame);
        this.screenController = screenControllerToRedirectActions;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case SettingsPanel.EXIT_BUTTON_ACTION_COMMAND:
                    System.exit(0);
                break;
            case SettingsPanel.CHANGE_LANGUAGE_BUTTON_ACTION_COMMAND:
                ((SettingsPanel)frontPanel).showLanguagesPanel();
                break;
            case SettingsPanel.CREDITS_BUTTON_ACTION_COMMAND:
                ((SettingsPanel)frontPanel).showCreditsPanel();
                break;
            case SettingsPanel.LOG_OUT_BUTTON_ACTION_COMMAND:
                //It can only be called from the MainMenuController.
                //Else, do nothing
                if(screenController instanceof MainMenuController)
                    ((MainMenuController)screenController).logOut();
                break;
            case SettingsPanel.DELETE_ACCOUNT_BUTTON_ACTION_COMMAND:
                ((SettingsPanel)frontPanel).showDeleteAccountPanel();
                break;
            case SettingsPanel.CONFIRM_DELETE_ACCOUNT_BUTTON_ACTION_COMMAND:
                ((SettingsPanel)frontPanel).clearDeleteAccountError();
                ((SettingsPanel)frontPanel).pauseAllDeleteAccountComponents();
                new DeleteAccountOnBackground().execute(); //Run this task in background
                break;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if(e.getSource() instanceof RoyaleLabel){
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();

            if(labelClicked.getActionCommand().equals(SettingsPanel.RETURN_TO_MAIN_MENU_ACTION_COMMAND)){
                ((SettingsPanel)frontPanel).showMainPanel();
            }
            else if(labelClicked.getActionCommand().equals(SettingsPanel.CHANGE_LANGUAGE_TO_ENGLISH_ACTION_COMMAND)){
                LanguageManager.changePreferredLanguage(Language.ENGLISH);
                screenController.goToScreen(ScreenController.Screen.SPLASH_SCREEN);
            }
            else if(labelClicked.getActionCommand().equals(SettingsPanel.CHANGE_LANGUAGE_TO_SPANISH_ACTION_COMMAND)){
                LanguageManager.changePreferredLanguage(Language.SPANISH);
                screenController.goToScreen(ScreenController.Screen.SPLASH_SCREEN);
            }
            else if(labelClicked.getActionCommand().equals(SettingsPanel.CHANGE_LANGUAGE_TO_CATALAN_ACTION_COMMAND)){
                LanguageManager.changePreferredLanguage(Language.CATALAN);
                screenController.goToScreen(ScreenController.Screen.SPLASH_SCREEN);
            }
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() instanceof RoyaleSlider){
            RoyaleSlider source = (RoyaleSlider) e.getSource();
            if(source.getValueIsAdjusting()) return; //If the value is still adjusting, do nothing
            int value = source.getValue();

            switch(source.getActionCommand()){
                case SettingsPanel.MUSIC_SLIDER_ACTION_COMMAND:
                    MusicPlayer.getInstance().setVolume(value);
                    break;
                case SettingsPanel.SOUND_SLIDER_ACTION_COMMAND:
                    SoundPlayer.getInstance().setVolume(value);
                    break;
            }

        }
    }



    private class DeleteAccountOnBackground extends SwingWorker<String, Void>{

        @Override
        protected String doInBackground(){
            SettingsPanel settingsPanel = (SettingsPanel)frontPanel;
            if(Arrays.equals(settingsPanel.getDeleteAccountPasswordFieldText(),
                    settingsPanel.getDeleteAccountConfirmPasswordFieldText()) == false)
                return LanguageManager.getSentence(Sentences.PASSWORDS_DONT_MATCH);


            try{
                boolean accountDeleted = screenController.getGameModel().deleteUser(settingsPanel.getDeleteAccountPasswordFieldText());
                if(accountDeleted == false) return LanguageManager.getSentence(Sentences.INCORRECT_PASSWORD);
            }catch(SQLException e){
                return LanguageManager.getSentence(Sentences.DATABASE_ERROR);
            }
            return null;
        }


        @Override
        protected void done() {
            String errorMessage;
            try{
                errorMessage = get();
            }catch(InterruptedException | ExecutionException e){
                errorMessage = LanguageManager.getSentence(Sentences.UNKNOWN_ERROR);
                e.printStackTrace();
            }

            if(errorMessage != null){ //If account hasn't been deleted, show the error
                ((SettingsPanel)frontPanel).setDeleteAccountError(errorMessage);
                ((SettingsPanel)frontPanel).enableAllDeleteAccountComponents();
            }
            else{ //If the account has been deleted successfully, log out
                screenController.goToScreen(ScreenController.Screen.SPLASH_SCREEN);
            }
        }
    }



}

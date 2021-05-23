package presentation.controller;

import business.GameModel;
import business.MailUtility;
import business.RegistrationException;
import business.SecurityUtility;
import business.entities.*;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import presentation.view.LoginScreen;
import presentation.view.PasswordForgottenScreen;
import presentation.view.RoyaleFrame;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyaleTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * {@code PasswordForgottenScreenController} is a {@link ScreenController} that will manage and listen to an
 * instance of a {@link PasswordForgottenScreen} and put it onto the {@link RoyaleFrame} of the game.
 *
 * <p>Whenever the email of the user that wants to recover the password, or when it already can change it and
 * submits the new one, this class will create new {@code threads} to carry out these tasks on background.
 *
 * @see PasswordForgottenScreen
 * @version 1.0
 */
public class PasswordForgottenScreenController extends ScreenController{

    private PasswordForgottenScreen passwordForgottenScreen;


    /**
     * Default PasswordForgottenScreenController constructor
     * @param royaleFrame The royaleFrame of the game
     * @param gameModel The gameModel of the game
     */
    public PasswordForgottenScreenController(RoyaleFrame royaleFrame, GameModel gameModel){
        super(royaleFrame, gameModel, null);
    }

    /**
     * {@inheritDoc}
     */
    public void start(boolean showSettingsPanelOnStart){
        passwordForgottenScreen = new PasswordForgottenScreen(royaleFrame.getHeight());
        passwordForgottenScreen.addButtonListener(this);
        passwordForgottenScreen.addLabelsListener(this);
        setPanelToListenForESCKey(passwordForgottenScreen);

        royaleFrame.changeScreen(passwordForgottenScreen);

        if(showSettingsPanelOnStart)
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
        if(e.getActionCommand().equals(PasswordForgottenScreen.SEND_EMAIL_ACTION_COMMAND)){
            passwordForgottenScreen.pauseAllComponents();
            passwordForgottenScreen.emptyEmailErrorMessage();
            new checkEmailValidAndSendEmailInBackground().execute();
        }
        else if(e.getActionCommand().equals(PasswordForgottenScreen.CHECK_CODE_ACTION_COMMAND)){
            passwordForgottenScreen.pauseAllComponents();
            passwordForgottenScreen.emptyVerificationCodeErrorMessage();
            String verificationCodeIntroduced = passwordForgottenScreen.getTextVerificationCodeTextField();

            if(verificationCodeIntroduced.equals(SecurityUtility.getLatestVerificationCodeGenerated())){
                passwordForgottenScreen.goToNewPasswordPanel();
            }
            else{
                passwordForgottenScreen.setVerificationCodeErrorMessage("Error. Incorrect verification code");
                //TODO: ADD this word to the language manager
            }

            passwordForgottenScreen.enableAllComponents(); //Re-enable all components when done
        }
        else if(e.getActionCommand().equals(PasswordForgottenScreen.CHANGE_PASSWORD_BUTTON_ACTION_COMMAND)){
            passwordForgottenScreen.pauseAllComponents();
            passwordForgottenScreen.emptyPasswordsErrorMessage();
            new ChangeUserPasswordInBackground().execute();
        }
    }



    @Override
    public void mousePressed(MouseEvent e){
        super.mousePressed(e);

        if(e.getSource() instanceof RoyaleLabel) {
            RoyaleLabel labelClicked = (RoyaleLabel) e.getSource();
            if(labelClicked.getActionCommand().equals(PasswordForgottenScreen.RETURN_LOGIN_LABEL_ACTION_COMMAND)){
                passwordForgottenScreen.pauseAllComponents();
                goToScreen(Screens.LOGIN_SCREEN);
            }
        }
    }




    private class checkEmailValidAndSendEmailInBackground extends SwingWorker<String, Void>{

        @Override
        protected String doInBackground(){
            try{
                if(gameModel.checkIfMailExists(passwordForgottenScreen.getTextEmailTextField()) == false)
                    return LanguageManager.getSentence(Sentences.EMAIL_DOES_NOT_EXIST);
            }catch(SQLException e){
                return LanguageManager.getSentence(Sentences.DATABASE_ERROR);
            }

            try{
                MailUtility.sendMail(passwordForgottenScreen.getTextEmailTextField(), "War Royale Verification Code",
                        SecurityUtility.getSixDigitRandomVerificationCode());
            }catch(Exception e){
                return "Can't send email";
            }

            return null; //If mail exists and no SQL exception has been thrown, return null
        }


        @Override
        protected void done() {
            String errorMessage = null;
            try{
                errorMessage = get();
            }catch(Exception e){
                errorMessage = LanguageManager.getSentence(Sentences.UNKNOWN_ERROR);
                System.err.println("Error in the checkEmail Worker Thread");
                e.printStackTrace();
            }


            if(errorMessage != null){ //If an error has occurred, let's print it
                passwordForgottenScreen.setEmailErrorMessage(errorMessage);
            }
            else{ //If there is no error message it means there is no error, so email exists in the database.
                passwordForgottenScreen.setCheckCodePanelVisible();
            }
            passwordForgottenScreen.enableAllComponents(); //Re-enable all components before finishing

        }
    }



    private class ChangeUserPasswordInBackground extends SwingWorker<String, Void> {

        @Override
        protected String doInBackground(){
            //If both passwords are not equal, do not continue
            if(!Arrays.equals(passwordForgottenScreen.getTextPasswordTextField(), passwordForgottenScreen.getTextConfirmPasswordTextField())){
                return LanguageManager.getSentence(Sentences.PASSWORDS_DONT_MATCH);
            }

            try{
                //Let's try to modify user password
                gameModel.changeUserPassword(passwordForgottenScreen.getTextEmailTextField(), passwordForgottenScreen.getTextPasswordTextField());
            }catch(RegistrationException e){
                switch(e.getExceptionCause()){
                    case PASSWORD_NOT_SECURE:
                        return LanguageManager.getSentence(Sentences.PASSWORD_NOT_SECURE);
                    default:
                        return LanguageManager.getSentence(Sentences.UNKNOWN_ERROR);
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
                passwordForgottenScreen.setPasswordsErrorMessage(errorMessage);
                passwordForgottenScreen.enableAllComponents();
            }
            else goToScreen(Screens.SPLASH_SCREEN); //If everything has gone OK, return to the splash screen
        }
    }


}



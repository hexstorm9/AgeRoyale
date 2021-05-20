package presentation.view;

import business.entities.LanguageManager;
import business.entities.Sentences;
import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyalePasswordField;
import presentation.view.customcomponents.RoyaleTextField;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


/**
 * {@code PasswordForgottenScreen} is a {@link Screen} providing a visual way to recover the password to a user that has
 * forgotten it.
 * <p>The user will introduce its email, then it will be asked to introduce the code sent to this email and finally
 * will be able to write a new password.
 *
 * <p>It has to be created and listened from a {@link presentation.controller.PasswordForgottenScreenController} object.
 *
 * @version 1.0
 * @see presentation.controller.PasswordForgottenScreenController
 */
public class PasswordForgottenScreen extends Screen {

    private JPanel sendEmailAndCheckCodePanel;
    private JPanel newPasswordsPanel;

    private JPanel checkCodePanel; //SubPanel inside sendEmailAndCheckCodePanel

    private RoyaleButton sendEmailButton, checkCodeButton, confirmNewPasswordButton;
    private RoyaleTextField emailTextField, verificationCodeTextField;
    private RoyalePasswordField newPasswordTextField, confirmNewPasswordTextField;
    private RoyaleLabel returnToLoginLabel, errorCodeLabel, errorMailLabel, errorPasswordsLabel;

    private JPanel centerPane;

    public static final String SEND_EMAIL_ACTION_COMMAND = "send_email_button";
    public static final String RETURN_LOGIN_LABEL_ACTION_COMMAND = "return_email_button";
    public static final String CHECK_CODE_ACTION_COMMAND = "check_code_button";
    public static final String CHANGE_PASSWORD_BUTTON_ACTION_COMMAND = "change_password_button";


    /**
     * Default PasswordForgottenScreen constructor.
     * @param screenHeight The Height of the Screen
     */
    public PasswordForgottenScreen(int screenHeight){
        super(screenHeight);

        setLayout(new GridBagLayout());
        centerPane = new JPanel();
        centerPane.setOpaque(false);
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));


        RoyaleLabel logoImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getLogo()));
        logoImage.setAlignmentX(CENTER_ALIGNMENT);

        //Send Email Part ---------------------------------------------------------
        RoyaleLabel recoverPasswordLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.RECOVER_PASSWORD), RoyaleLabel.LabelType.TITLE);
        recoverPasswordLabel.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleLabel generateNewPasswordText = new RoyaleLabel(LanguageManager.getSentence(Sentences.GENERATE_NEW_PASSWORD), RoyaleLabel.LabelType.TITLE);
        generateNewPasswordText.setAlignmentX(CENTER_ALIGNMENT);

        JPanel emailPanel = new JPanel();
        emailPanel.setAlignmentX(CENTER_ALIGNMENT);
        emailPanel.setOpaque(false);
        emailPanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.MAIL), RoyaleLabel.LabelType.PARAGRAPH));
        emailPanel.add(emailTextField = new RoyaleTextField());

        errorMailLabel = new RoyaleLabel("   ", RoyaleLabel.LabelType.ERROR);
        errorMailLabel.setAlignmentX(CENTER_ALIGNMENT);

        sendEmailButton =  new RoyaleButton(LanguageManager.getSentence(Sentences.SEND_EMAIL));
        sendEmailButton.setAlignmentX(CENTER_ALIGNMENT);


        //Verification Code Part ---------------------------------------------------------
        JPanel verificationCodePanel = new JPanel();
        verificationCodePanel.setAlignmentX(CENTER_ALIGNMENT);
        verificationCodePanel.setOpaque(false);
        verificationCodePanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.VERIFICATION_CODE), RoyaleLabel.LabelType.PARAGRAPH));
        verificationCodePanel.add(verificationCodeTextField = new RoyaleTextField());

        errorCodeLabel = new RoyaleLabel("   ", RoyaleLabel.LabelType.ERROR);
        errorCodeLabel.setAlignmentX(CENTER_ALIGNMENT);

        checkCodeButton = new RoyaleButton(LanguageManager.getSentence(Sentences.CHECK_CODE));
        checkCodeButton.setAlignmentX(CENTER_ALIGNMENT);

        checkCodePanel = new JPanel();
        checkCodePanel.setAlignmentX(CENTER_ALIGNMENT);
        checkCodePanel.setLayout(new BoxLayout(checkCodePanel, BoxLayout.Y_AXIS));
        checkCodePanel.setOpaque(false);
        checkCodePanel.add(verificationCodePanel);
        checkCodePanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.01))));
        checkCodePanel.add(errorCodeLabel);
        checkCodePanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.01))));
        checkCodePanel.add(checkCodeButton);
        checkCodePanel.setVisible(false); //Will be activated from the controller with the method setCheckCodePanelVisible();


        //Merge Email and VerificationCode parts
        sendEmailAndCheckCodePanel = new JPanel();
        sendEmailAndCheckCodePanel.setAlignmentX(CENTER_ALIGNMENT);
        sendEmailAndCheckCodePanel.setLayout(new BoxLayout(sendEmailAndCheckCodePanel, BoxLayout.Y_AXIS));
        sendEmailAndCheckCodePanel.setOpaque(false);
        sendEmailAndCheckCodePanel.add(generateNewPasswordText);
        sendEmailAndCheckCodePanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.03))));
        sendEmailAndCheckCodePanel.add(emailPanel);
        sendEmailAndCheckCodePanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.01))));
        sendEmailAndCheckCodePanel.add(errorMailLabel);
        sendEmailAndCheckCodePanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
        sendEmailAndCheckCodePanel.add(sendEmailButton);
        sendEmailAndCheckCodePanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.1))));
        sendEmailAndCheckCodePanel.add(checkCodePanel);


        //Passwords Part ---------------------------------------------------------
        RoyaleLabel passwordsLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.ENTER_PASSWORD), RoyaleLabel.LabelType.TITLE);
        passwordsLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel.setOpaque(false);
        passwordPanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.ENTER_NEW_PASSWORD), RoyaleLabel.LabelType.PARAGRAPH));
        passwordPanel.add(newPasswordTextField = new RoyalePasswordField());

        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordPanel.setAlignmentX(CENTER_ALIGNMENT);
        confirmPasswordPanel.setOpaque(false);
        confirmPasswordPanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.ENTER_NEW_PASSWORD), RoyaleLabel.LabelType.PARAGRAPH));
        confirmPasswordPanel.add(confirmNewPasswordTextField = new RoyalePasswordField());

        errorPasswordsLabel = new RoyaleLabel("   ", RoyaleLabel.LabelType.ERROR);
        errorPasswordsLabel.setAlignmentX(CENTER_ALIGNMENT);

        confirmNewPasswordButton = new RoyaleButton(LanguageManager.getSentence(Sentences.ENTER));
        confirmNewPasswordButton.setAlignmentX(CENTER_ALIGNMENT);


        newPasswordsPanel = new JPanel();
        newPasswordsPanel.setOpaque(false);
        newPasswordsPanel.setLayout(new BoxLayout(newPasswordsPanel, BoxLayout.Y_AXIS));
        newPasswordsPanel.setAlignmentX(CENTER_ALIGNMENT);
        newPasswordsPanel.add(passwordsLabel);
        newPasswordsPanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.03))));
        newPasswordsPanel.add(passwordPanel);
        newPasswordsPanel.add(confirmPasswordPanel);
        newPasswordsPanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.01))));
        newPasswordsPanel.add(errorPasswordsLabel);
        newPasswordsPanel.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
        newPasswordsPanel.add(confirmNewPasswordButton);



        returnToLoginLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.RETURN_LOGIN), RoyaleLabel.LabelType.LINK);
        returnToLoginLabel.setAlignmentX(CENTER_ALIGNMENT);


        centerPane.add(logoImage);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.05))));
        centerPane.add(sendEmailAndCheckCodePanel);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.1))));
        centerPane.add(returnToLoginLabel);

        add(centerPane);
    }



    /**
     * To be called whenever the {@code Screen} is created, so as to add a listener to its buttons
     * @param al The listener of the Buttons
     */
    public void addButtonListener(ActionListener al){
        sendEmailButton.addActionListener(al);
        sendEmailButton.setActionCommand(SEND_EMAIL_ACTION_COMMAND);

        checkCodeButton.addActionListener(al);
        checkCodeButton.setActionCommand(CHECK_CODE_ACTION_COMMAND);

        confirmNewPasswordButton.addActionListener(al);
        confirmNewPasswordButton.setActionCommand(CHANGE_PASSWORD_BUTTON_ACTION_COMMAND);
    }


    /**
     * To be called whenever the {@code Screen} is created, so as to add a listener to its clickable labels
     * @param ml The listener of the clickable labels
     */
    public void addLabelsListener(MouseListener ml){
        returnToLoginLabel.addMouseListener(ml);
        returnToLoginLabel.setActionCommand(RETURN_LOGIN_LABEL_ACTION_COMMAND);
        returnToLoginLabel.setClickable(true);
    }


    /**
     * Pauses all components so as the user is not able to click anything or carry out any action.
     */
    public void pauseAllComponents(){
        emailTextField.setEnabled(false);
        sendEmailButton.setEnabled(false);
        verificationCodeTextField.setEnabled(false);
        checkCodeButton.setEnabled(false);
        newPasswordTextField.setEnabled(false);
        confirmNewPasswordTextField.setEnabled(false);
        confirmNewPasswordButton.setEnabled(false);
    }

    /**
     * Enables all components so as the user can carry out actions again
     */
    public void enableAllComponents(){
        emailTextField.setEnabled(true);
        sendEmailButton.setEnabled(true);
        verificationCodeTextField.setEnabled(true);
        checkCodeButton.setEnabled(true);
        newPasswordTextField.setEnabled(true);
        confirmNewPasswordTextField.setEnabled(true);
        confirmNewPasswordButton.setEnabled(true);
    }


    /**
     * When called, it confirms that the code has been sent. Thus, we can now show the code confirmation view.
     */
    public void setCheckCodePanelVisible(){
        checkCodePanel.setVisible(true);
    }


    /**
     * Whenever the code sent to the email is correct, we can't delete the email and code views and show the user
     * the view to change his password.
     * <p>So, this method deletes email and code confirmation and shows the change password part.
     */
    public void goToNewPasswordPanel(){
        Component[] components = centerPane.getComponents();
        int sendEmailAndCheckcodePanelIndex = 0;

        for(int i = 0; i < components.length; i++){
            if(components[i].equals(sendEmailAndCheckCodePanel)) sendEmailAndCheckcodePanelIndex = i;
        }

        centerPane.remove(sendEmailAndCheckcodePanelIndex);
        centerPane.add(newPasswordsPanel, sendEmailAndCheckcodePanelIndex - 1);
        repaint();
        revalidate();
    }


    /**
     * Returns the text inside the Email TextField
     * @return The text inside the Email TextField
     */
    public String getTextEmailTextField(){
        return emailTextField.getText();
    }

    /**
     * Returns the text inside the Verification Code TextField
     * @return The text inside the Verification Code TextField
     */
    public String getTextVerificationCodeTextField(){
        return verificationCodeTextField.getText();
    }

    /**
     * Returns the char[] inside the Password TextField
     * @return char[] inside the Password TextField
     */
    public char[] getTextPasswordTextField(){
        return newPasswordTextField.getPassword();
    }

    /**
     * Returns the char[] inside the Confirm Password TextField
     * @return char[] inside the Confirm Password TextField
     */
    public char[] getTextConfirmPasswordTextField(){
        return confirmNewPasswordTextField.getPassword();
    }


    /**
     * Sets an error message below the email TextField
     * @param errorMessage The error message to show
     */
    public void setEmailErrorMessage(String errorMessage){
        errorMailLabel.setText(errorMessage);
    }

    /**
     * Clears the Email Error Message
     */
    public void emptyEmailErrorMessage(){
        errorMailLabel.setText("   ");
    }

    /**
     * Sets an error message below the verification code TextField
     * @param errorMessage The error message to show
     */
    public void setVerificationCodeErrorMessage(String errorMessage){
        errorCodeLabel.setText(errorMessage);
    }
    /**
     * Clears the Verification Code Error Message
     */
    public void emptyVerificationCodeErrorMessage(){
        errorCodeLabel.setText("   ");
    }

    /**
     * Sets an error message below the passwords TextFields
     * @param errorMessage The error message to show
     */
    public void setPasswordsErrorMessage(String errorMessage){
        errorPasswordsLabel.setText(errorMessage);
    }
    /**
     * Clears the Passwords Error Message
     */
    public void emptyPasswordsErrorMessage(){
        errorPasswordsLabel.setText("   ");
    }
}

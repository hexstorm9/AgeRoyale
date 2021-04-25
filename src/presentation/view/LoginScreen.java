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
 * The LoginScreen will be a JPanel put directly into the {@link RoyaleFrame} by means
 * of its method {@link RoyaleFrame#changeScreen(JPanel, RoyaleFrame.BackgroundStyle)}
 *
 * <p>A {@link presentation.controller.LoginScreenController} needs to create this class and
 * call the latter mentioned method {@link RoyaleFrame#changeScreen(JPanel, RoyaleFrame.BackgroundStyle)}.
 * After that, the controller should call the register listener methods in this class.
 *
 * @see presentation.controller.LoginScreenController
 * @see RoyaleFrame
 * @version 1.0
 */
public class LoginScreen extends JPanel {

    private RoyaleButton loginButton;
    private RoyaleTextField usernameTextField;
    private RoyalePasswordField passwordTextField;
    private RoyaleLabel forgetPasswordLabel, registerLabel;
    private RoyaleLabel errorLabel;

    public static final String LOGIN_BUTTON_ACTION_COMMAND = "login_button";
    public static final String REGISTER_LABEL_ACTION_COMMAND = "register_button";
    public static final String FORGOT_PASSWORD_LABEL_ACTION_COMMAND = "forgot_password_button";

    /**
     * Default LoginScreen constructor.
     * <p>Constructs the whole Login Panel depending on the height of the screen provided
     * @param screenHeight Height of the Frame that will add this Screen
     */
    public LoginScreen(int screenHeight){
        setLayout(new GridBagLayout());

        RoyaleLabel logoImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().getLogo()));
        logoImage.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleLabel loginText = new RoyaleLabel(LanguageManager.getSentence(Sentences.LOGIN), RoyaleLabel.LabelType.TITLE);
        loginText.setAlignmentX(CENTER_ALIGNMENT);


        JPanel groupTextFieldsPanel = new JPanel();
        groupTextFieldsPanel.setAlignmentX(CENTER_ALIGNMENT);
        groupTextFieldsPanel.setOpaque(false);
        GroupLayout groupLayout = new GroupLayout(groupTextFieldsPanel);
        groupTextFieldsPanel.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        RoyaleLabel usernameLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.USERNAME), RoyaleLabel.LabelType.PARAGRAPH);
        usernameTextField = new RoyaleTextField();

        RoyaleLabel passwordLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.PASSWORD), RoyaleLabel.LabelType.PARAGRAPH);
        passwordTextField = new RoyalePasswordField();

        GroupLayout.SequentialGroup horizontalGroup = groupLayout.createSequentialGroup();
        horizontalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(usernameLabel).addComponent(passwordLabel));
        horizontalGroup.addGroup(groupLayout.createParallelGroup().addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
        groupLayout.setHorizontalGroup(horizontalGroup);

        GroupLayout.SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
        verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel).addComponent(usernameTextField));
        verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel).addComponent(passwordTextField));
        groupLayout.setVerticalGroup(verticalGroup);


        errorLabel = new RoyaleLabel("   ", RoyaleLabel.LabelType.ERROR);
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);

        loginButton = new RoyaleButton(LanguageManager.getSentence(Sentences.ENTER));
        loginButton.setAlignmentX(CENTER_ALIGNMENT);

        forgetPasswordLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.PASSWORD_FORGOTTEN), RoyaleLabel.LabelType.LINK);
        forgetPasswordLabel.setAlignmentX(CENTER_ALIGNMENT);
        registerLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.DONT_HAVE_ACCOUNT), RoyaleLabel.LabelType.LINK);
        registerLabel.setAlignmentX(CENTER_ALIGNMENT);


        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.setOpaque(false);

        centerPane.add(logoImage);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.05))));
        centerPane.add(loginText);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.03))));
        centerPane.add(groupTextFieldsPanel);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.01))));
        centerPane.add(errorLabel);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
        centerPane.add(loginButton);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.1))));
        centerPane.add(forgetPasswordLabel);
        centerPane.add(registerLabel);

        add(centerPane, new GridBagConstraints());
    }


    /**
     * Adds Action listeners for all the buttons in the view
     * @param al ActionListener that will listen to button events
     */
    public void addButtonListener(ActionListener al){
        loginButton.addActionListener(al);
        loginButton.setActionCommand(LOGIN_BUTTON_ACTION_COMMAND);
    }


    /**
     * Adds MouseListener for all Labels in the view
     * @param ml MouseListener that will listen to Label events
     */
    public void addLabelsListener(MouseListener ml){
        forgetPasswordLabel.addMouseListener(ml);
        forgetPasswordLabel.setActionCommand(FORGOT_PASSWORD_LABEL_ACTION_COMMAND);
        forgetPasswordLabel.setClickable(true);

        registerLabel.addMouseListener(ml);
        registerLabel.setActionCommand(REGISTER_LABEL_ACTION_COMMAND);
        registerLabel.setClickable(true);
    }


    /**
     * Returns the current text inside the Username TextField
     * @return Current text inside Username TextField
     */
    public String getTextUsernameTextField(){
        return usernameTextField.getText();
    }

    /**
     * Returns the current text inside the password TextField with char[] format
     * @return Current text inside Password TextField
     */
    public char[] getTextPasswordTextField(){
        return passwordTextField.getPassword();
    }


    /**
     * Pauses all components so as the user is not able to click anything or carry out any action.
     */
    public void pauseAllComponents(){
        usernameTextField.setEnabled(false);
        passwordTextField.setEnabled(false);
        loginButton.setEnabled(false);
        registerLabel.setClickable(false);
        forgetPasswordLabel.setClickable(false);
    }


    /**
     * Enables all components so as the user can carry out actions again
     */
    public void enableAllComponents(){
        usernameTextField.setEnabled(true);
        passwordTextField.setEnabled(true);
        loginButton.setEnabled(true);
        registerLabel.setClickable(true);
        forgetPasswordLabel.setClickable(true);
    }


    /**
     * Shows an error message in a Label with the String provided
     * @param errorMessage The error message to be shown
     */
    public void setErrorMessage(String errorMessage){
        errorLabel.setText(errorMessage);
        repaint();
        revalidate();
    }

    /**
     * Clears the current empty error message.
     * <p>If there is no error message currently displayed, nothing will be done.
     */
    public void emptyErrorMessage(){
        errorLabel.setText("   ");
        repaint();
        revalidate();
    }

}

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

public class RegisterScreen extends JPanel {

    private RoyaleButton registerButton;
    private RoyaleTextField usernameTextField, mailTextField;
    private RoyalePasswordField passwordTextField, confirmPasswordTextField;
    private RoyaleLabel haveAccount;
    private RoyaleLabel errorLabel;

    public static final String LOGIN_LABEL_ACTION_COMMAND = "login_label";
    public static final String REGISTER_BUTTON_ACTION_COMMAND = "register_button";


    public RegisterScreen(int screenHeight){
        setLayout(new GridBagLayout());

        RoyaleLabel logoImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().getLogo()));
        logoImage.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleLabel registerText = new RoyaleLabel(LanguageManager.getSentence(Sentences.REGISTER), RoyaleLabel.LabelType.TITLE);
        registerText.setAlignmentX(CENTER_ALIGNMENT);


        JPanel groupTextFieldsPanel = new JPanel();
        groupTextFieldsPanel.setAlignmentX(CENTER_ALIGNMENT);
        groupTextFieldsPanel.setOpaque(false);
        GroupLayout groupLayout = new GroupLayout(groupTextFieldsPanel);
        groupTextFieldsPanel.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        RoyaleLabel usernameLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.USERNAME), RoyaleLabel.LabelType.PARAGRAPH);
        usernameTextField = new RoyaleTextField();

        RoyaleLabel mailLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.MAIL), RoyaleLabel.LabelType.PARAGRAPH);
        mailTextField = new RoyaleTextField();

        RoyaleLabel passwordLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.PASSWORD), RoyaleLabel.LabelType.PARAGRAPH);
        passwordTextField = new RoyalePasswordField();

        RoyaleLabel confirmPasswordLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.CONFIRM_PASSWORD), RoyaleLabel.LabelType.PARAGRAPH);
        confirmPasswordTextField = new RoyalePasswordField();

        GroupLayout.SequentialGroup horizontalGroup = groupLayout.createSequentialGroup();
        horizontalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(usernameLabel).addComponent(mailLabel).addComponent(passwordLabel).addComponent(confirmPasswordLabel));
        horizontalGroup.addGroup(groupLayout.createParallelGroup().addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(mailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(confirmPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
        groupLayout.setHorizontalGroup(horizontalGroup);

        GroupLayout.SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
        verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel).addComponent(usernameTextField));
        verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(mailLabel).addComponent(mailTextField));
        verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel).addComponent(passwordTextField));
        verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(confirmPasswordLabel).addComponent(confirmPasswordTextField));
        groupLayout.setVerticalGroup(verticalGroup);


        errorLabel = new RoyaleLabel("   ", RoyaleLabel.LabelType.ERROR);
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);

        registerButton = new RoyaleButton(LanguageManager.getSentence(Sentences.ENTER));
        registerButton.setAlignmentX(CENTER_ALIGNMENT);

        haveAccount = new RoyaleLabel(LanguageManager.getSentence(Sentences.HAVE_AN_ACCOUNT), RoyaleLabel.LabelType.LINK);
        haveAccount.setAlignmentX(CENTER_ALIGNMENT);

        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.setOpaque(false);

        centerPane.add(logoImage);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.03))));
        centerPane.add(registerText);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.03))));
        centerPane.add(groupTextFieldsPanel);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.01))));
        centerPane.add(errorLabel);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
        centerPane.add(registerButton);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.1))));
        centerPane.add(haveAccount);

        add(centerPane, new GridBagConstraints());
    }

    public void addButtonListener(ActionListener al){
        registerButton.addActionListener(al);
        registerButton.setActionCommand(REGISTER_BUTTON_ACTION_COMMAND);
    }

    public void addLabelsListener(MouseListener ml){
        haveAccount.addMouseListener(ml);
        haveAccount.setActionCommand(LOGIN_LABEL_ACTION_COMMAND);
        haveAccount.setClickable(true);
    }


    /**
     * Pauses all components so as the user is not able to click anything or carry out any action.
     */
    public void pauseAllComponents(){
        usernameTextField.setEnabled(false);
        passwordTextField.setEnabled(false);
        registerButton.setEnabled(false);
        haveAccount.setClickable(false);
    }

    /**
     * Enables all components so as the user can carry out actions again
     */
    public void enableAllComponents(){
        usernameTextField.setEnabled(true);
        passwordTextField.setEnabled(true);
        registerButton.setEnabled(true);
        haveAccount.setClickable(true);
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


    public String getTextUsernameTextField() {
        return usernameTextField.getText();
    }

    public String getTextMailTextField() {
        return mailTextField.getText();
    }

    public char[] getTextPasswordTextField() {
        return passwordTextField.getPassword();
    }

    public char[] getTextConfirmPasswordTextField() {
        return confirmPasswordTextField.getPassword();
    }
}

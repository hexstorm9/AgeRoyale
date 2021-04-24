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
    private RoyaleTextField usernameTextField;
    private RoyaleTextField mailTextField;
    private RoyalePasswordField passwordTextField;
    private RoyalePasswordField confirmPasswordTextField;
    private RoyaleLabel haveAccount;

    public static final String LOGIN_LABEL_ACTION_COMMAND = "login_label";
    public static final String REGISTER_BUTTON_ACTION_COMMAND = "register_button";

    public RegisterScreen(int screenHeight){
        setLayout(new GridBagLayout());

        RoyaleLabel logoImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().getLogo()));
        logoImage.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleLabel registerText = new RoyaleLabel(LanguageManager.getSentence(Sentences.REGISTER), RoyaleLabel.LabelType.TITLE);
        registerText.setAlignmentX(CENTER_ALIGNMENT);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
        usernamePanel.setOpaque(false);
        usernamePanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.USERNAME), RoyaleLabel.LabelType.PARAGRAPH));
        usernamePanel.add(usernameTextField = new RoyaleTextField());

        JPanel mailPanel = new JPanel();
        mailPanel.setAlignmentX(CENTER_ALIGNMENT);
        mailPanel.setOpaque(false);
        mailPanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.MAIL), RoyaleLabel.LabelType.PARAGRAPH));
        mailPanel.add(mailTextField = new RoyaleTextField());

        JPanel passwordPanel = new JPanel();
        passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel.setOpaque(false);
        passwordPanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.PASSWORD), RoyaleLabel.LabelType.PARAGRAPH));
        passwordPanel.add(passwordTextField = new RoyalePasswordField());

        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordPanel.setAlignmentX(CENTER_ALIGNMENT);
        confirmPasswordPanel.setOpaque(false);
        confirmPasswordPanel.add(new RoyaleLabel(LanguageManager.getSentence(Sentences.CONFIRM_PASSWORD), RoyaleLabel.LabelType.PARAGRAPH));
        confirmPasswordPanel.add(confirmPasswordTextField = new RoyalePasswordField());

        registerButton = new RoyaleButton(LanguageManager.getSentence(Sentences.ENTER));
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        registerButton.setFont(MenuGraphics.getInstance().getMainFont());

        haveAccount = new RoyaleLabel(LanguageManager.getSentence(Sentences.HAVE_AN_ACCOUNT), RoyaleLabel.LabelType.LINK);
        haveAccount.setAlignmentX(CENTER_ALIGNMENT);

        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.setOpaque(false);

        centerPane.add(logoImage);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.003))));
        centerPane.add(registerText);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.007))));
        centerPane.add(usernamePanel);
        centerPane.add(mailPanel);
        centerPane.add(passwordPanel);
        centerPane.add(confirmPasswordPanel);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.05))));
        centerPane.add(registerButton);
        centerPane.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.05))));
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

    public void pauseAllComponents(){
        usernameTextField.setEnabled(false);
        passwordTextField.setEnabled(false);
        registerButton.setEnabled(false);
        haveAccount.setClickable(false);
    }

    public String getUsernameTextField() {
        return usernameTextField.getText();
    }

    public String getMailTextField() {
        return mailTextField.getText();
    }

    public char[] getPasswordTextField() {
        return passwordTextField.getPassword();
    }

    public char[] getConfirmPasswordTextField() {
        return confirmPasswordTextField.getPassword();
    }
}

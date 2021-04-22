package presentation.view;


import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyaleTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The LoginScreen will be a
 */
public class LoginScreen extends JPanel {

    private RoyaleButton loginButton;
    private RoyaleTextField usernameTextField, passwordTextField;

    public static final String LOGIN_BUTTON_ACTION_COMMAND = "login_button";
    public static final String REGISTER_BUTTON_ACTION_COMMAND = "register_button";
    public static final String FORGOT_PASSWORD_BUTTON_ACTION_COMMAND = "forgot_password_button";


    public LoginScreen(){
        setLayout(new GridBagLayout());

        RoyaleLabel logoImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().getLogo()));
        logoImage.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleLabel loginText = new RoyaleLabel("Log In", RoyaleLabel.LabelType.TITLE);
        loginText.setAlignmentX(CENTER_ALIGNMENT);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
        usernamePanel.setOpaque(false);
        usernamePanel.add(new RoyaleLabel("Username", RoyaleLabel.LabelType.PARAGRAPH));
        usernamePanel.add(usernameTextField = new RoyaleTextField());

        JPanel passwordPanel = new JPanel();
        passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel.setOpaque(false);
        passwordPanel.add(new RoyaleLabel("Password", RoyaleLabel.LabelType.PARAGRAPH));
        passwordPanel.add(passwordTextField = new RoyaleTextField());

        loginButton = new RoyaleButton("Enter");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleLabel forgetPassword = new RoyaleLabel("Forget Password?", RoyaleLabel.LabelType.SMALL);
        forgetPassword.setAlignmentX(CENTER_ALIGNMENT);
        RoyaleLabel registerLabel = new RoyaleLabel("Don't have an Account? Register", RoyaleLabel.LabelType.SMALL);
        registerLabel.setAlignmentX(CENTER_ALIGNMENT);


        JPanel centerPane = new JPanel();
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.setOpaque(false);

        centerPane.add(logoImage);
        centerPane.add(loginText);
        centerPane.add(usernamePanel);
        centerPane.add(passwordPanel);
        centerPane.add(loginButton);
        centerPane.add(Box.createRigidArea(new Dimension(100, 100)));
        centerPane.add(forgetPassword);
        centerPane.add(registerLabel);

        add(centerPane, new GridBagConstraints());

    }


    public void addButtonsListener(ActionListener al){
        loginButton.addActionListener(al);
        loginButton.setActionCommand(LOGIN_BUTTON_ACTION_COMMAND);

    }


    public String getTextUsernameTextField(){
        return usernameTextField.getText();
    }

    public char[] getTextPasswordTextField(){
        return passwordTextField.getText().toCharArray();
    }

}

package presentation.view;


import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyaleTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class LoginScreen extends JPanel {

    private RoyaleButton loginButton;
    private RoyaleTextField usernameTextField, passwordTextField;

    private final static String LOGO_IMAGE_PATH = "./resources/sprites/logo.png";


    public LoginScreen(){
        setLayout(new GridBagLayout());

        JLabel logoImage = null;
        try{
            logoImage = new JLabel(new ImageIcon(ImageIO.read(new File(LOGO_IMAGE_PATH))));
            logoImage.setAlignmentX(CENTER_ALIGNMENT);
        }catch(IOException e){}

        RoyaleLabel loginText = new RoyaleLabel("Log In", 40);
        loginText.setAlignmentX(CENTER_ALIGNMENT);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
        usernamePanel.setOpaque(false);
        usernamePanel.add(new RoyaleLabel("Username", 26));
        usernamePanel.add(usernameTextField = new RoyaleTextField());

        JPanel passwordPanel = new JPanel();
        passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel.setOpaque(false);
        passwordPanel.add(new RoyaleLabel("Password", 26));
        passwordPanel.add(passwordTextField = new RoyaleTextField());

        loginButton = new RoyaleButton("Enter");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleLabel forgetPassword = new RoyaleLabel("Forget Password?", 16);
        forgetPassword.setAlignmentX(CENTER_ALIGNMENT);
        RoyaleLabel registerLabel = new RoyaleLabel("Don't have an Account? Register", 16);
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

    }



}

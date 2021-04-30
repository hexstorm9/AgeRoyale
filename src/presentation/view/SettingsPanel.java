package presentation.view;

import business.entities.LanguageManager;
import business.entities.Sentences;
import presentation.controller.SettingsPanelController;
import presentation.graphics.MenuGraphics;
import presentation.sound.MusicPlayer;
import presentation.sound.SoundPlayer;
import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyalePasswordField;
import presentation.view.customcomponents.RoyaleSlider;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class SettingsPanel extends FrontPanel {


    private SettingsPanelController settingsPanelController; //The controller that all buttons will be registered with


    //There will be multiple panels in this Settings Panel -->
    //      - The first Panel, the FrontPanel, is going to have a GridBagLayout and will occupy all the screen so as not to allow the user to click behind it
    //      - The second Panel, the Center Panel, will make the UI centered.
    //          Inside the CenterPanel, there is a JScrollPane. The JScrollPane is where other panels should be added.
    //      - A third panel, the Main Settings Panel (mainPanel) will contain the Exit, Change Language, Log Out buttons, etc
    //      - Other panels can be created (like creditsPanel and languagePanel) so as to be added to the JScrollPane of the
    //          CenterPanel
    private JScrollPane scrollPaneCentered;

    private MainPanel mainPanel;
    private CreditsPanel creditsPanel;
    private LanguagePanel languagePanel;
    private DeleteAccountPanel deleteAccountPanel;


    public static final String EXIT_BUTTON_ACTION_COMMAND = "exit_button";
    public static final String LOG_OUT_BUTTON_ACTION_COMMAND = "logout_button";
    public static final String CREDITS_BUTTON_ACTION_COMMAND = "credits_button";

    public static final String CHANGE_LANGUAGE_BUTTON_ACTION_COMMAND = "change_language_button";
    public static final String CHANGE_LANGUAGE_TO_ENGLISH_ACTION_COMMAND ="change_language_english";
    public static final String CHANGE_LANGUAGE_TO_SPANISH_ACTION_COMMAND ="change_language_spanish";
    public static final String CHANGE_LANGUAGE_TO_CATALAN_ACTION_COMMAND ="change_language_catalan";

    public static final String RETURN_TO_MAIN_MENU_ACTION_COMMAND = "return_to_main_menu";

    public static final String MUSIC_SLIDER_ACTION_COMMAND = "music_slider";
    public static final String SOUND_SLIDER_ACTION_COMMAND = "sound_slider";

    public static final String DELETE_ACCOUNT_BUTTON_ACTION_COMMAND = "delete_account_button";
    public static final String CONFIRM_DELETE_ACCOUNT_BUTTON_ACTION_COMMAND = "confirm_delete_account";



    public SettingsPanel(int panelWidth, int panelHeight){
        super(panelWidth, panelHeight);
        addMouseListener(settingsPanelController); //We need to capture all mouse events so as not to let the user allow to click behind the SettingsPanel

        final int CENTER_PANEL_HEIGHT = (int) (PANEL_HEIGHT * 0.6);
        final int CENTER_PANEL_WIDTH = setWoodTableVisible(CENTER_PANEL_HEIGHT);


        mainPanel = new MainPanel();

        scrollPaneCentered = new JScrollPane(mainPanel);
        scrollPaneCentered.setOpaque(false);
        scrollPaneCentered.getViewport().setOpaque(false);
        scrollPaneCentered.setBorder(null);
        scrollPaneCentered.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCentered.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneCentered.setPreferredSize(new Dimension(CENTER_PANEL_WIDTH, CENTER_PANEL_HEIGHT));
        scrollPaneCentered.setMinimumSize(new Dimension(CENTER_PANEL_WIDTH, CENTER_PANEL_HEIGHT));
        scrollPaneCentered.setMaximumSize(new Dimension(CENTER_PANEL_WIDTH, CENTER_PANEL_HEIGHT));
        scrollPaneCentered.getVerticalScrollBar().setPreferredSize(new Dimension(0,0)); //Hide the Vertical Scroll Bar if it appears

        add(scrollPaneCentered);
    }



    public void addButtonsListener(ActionListener al){
        mainPanel.addButtonsListener(al);
        for(Component c: mainPanel.getComponents()){
            if(c instanceof RoyaleButton) ((RoyaleButton) c).addActionListener(al);
        }

        if(deleteAccountPanel != null) deleteAccountPanel.addButtonsListener(al);
    }


    public void addLabelsListener(MouseListener ml){
        if(creditsPanel != null) creditsPanel.addLabelsListener(ml);
        if(languagePanel != null) languagePanel.addLabelsListener(ml);
        if(deleteAccountPanel != null) deleteAccountPanel.addLabelsListener(ml);
    }


    public void addSlidersListener(ChangeListener cl){
        mainPanel.addSlidersListener(cl);
    }


    public void showLanguagesPanel(){
        if(languagePanel != null) scrollPaneCentered.setViewportView(languagePanel);
        repaint();
        revalidate();
    }

    public void showCreditsPanel(){
        scrollPaneCentered.setViewportView(creditsPanel);
        repaint();
        revalidate();
    }

    public void showMainPanel(){
        scrollPaneCentered.setViewportView(mainPanel);
        repaint();
        revalidate();
    }

    public void showDeleteAccountPanel(){
        scrollPaneCentered.setViewportView(deleteAccountPanel);
        repaint();
        revalidate();
    }



     /**
     * Adds player information to the settings panel
     * @param name Name of the Player
     * @param arena current Arena of the player
     * @param crowns Crowns of the Player
     */
    public void addPlayerInformation(String name, int arena, int crowns){
        JPanel playerInformationPanel = new JPanel();
        playerInformationPanel.setAlignmentX(CENTER_ALIGNMENT);
        playerInformationPanel.setOpaque(false);
        playerInformationPanel.setLayout(new BoxLayout(playerInformationPanel, BoxLayout.Y_AXIS));

        RoyaleLabel playerName = new RoyaleLabel(name, RoyaleLabel.LabelType.PARAGRAPH);
        playerName.setForeground(MenuGraphics.BLUE);
        playerName.setAlignmentX(CENTER_ALIGNMENT);
        RoyaleLabel playerLevel = new RoyaleLabel(Integer.toString(arena), RoyaleLabel.LabelType.PARAGRAPH);
        playerLevel.setAlignmentX(CENTER_ALIGNMENT);
        RoyaleLabel playerTrophies = new RoyaleLabel(Integer.toString(crowns), RoyaleLabel.LabelType.PARAGRAPH);
        playerTrophies.setAlignmentX(CENTER_ALIGNMENT);

        playerInformationPanel.add(playerName);
        playerInformationPanel.add(playerLevel);
        playerInformationPanel.add(playerTrophies);

        mainPanel.add(Box.createRigidArea(new Dimension(30, 30)), 0);
        mainPanel.add(playerInformationPanel, 1);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 30)), 2);
    }


    /**
     * Adds the credits button to the settings panel as the last element before the EXIT button
     */
    public void addCreditsButton(){
        RoyaleButton creditsButton = new RoyaleButton("Credits");
        creditsButton.setAlignmentX(CENTER_ALIGNMENT);
        creditsButton.setActionCommand(CREDITS_BUTTON_ACTION_COMMAND);
        mainPanel.add(creditsButton);
        mainPanel.add(Box.createRigidArea(new Dimension(15, 15)));

        creditsPanel = new CreditsPanel();
    }


    /**
     * Adds the logout button to the settings panel as the last element before the EXIT button
     */
    public void addLogOutButton(){
        RoyaleButton logOutButton = new RoyaleButton("Log Out");
        logOutButton.setAlignmentX(CENTER_ALIGNMENT);
        logOutButton.setActionCommand(LOG_OUT_BUTTON_ACTION_COMMAND);
        mainPanel.add(logOutButton);
        mainPanel.add(Box.createRigidArea(new Dimension(15, 15)));
    }


    public void addLanguagesButton(){
        RoyaleButton changeLanguageButton = new RoyaleButton("Change Language");
        changeLanguageButton.setActionCommand(CHANGE_LANGUAGE_BUTTON_ACTION_COMMAND);
        changeLanguageButton.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(changeLanguageButton);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 30)));

        languagePanel = new LanguagePanel();
    }


    public void addDeleteAccountButton(){
        RoyaleButton deleteAccountButton = new RoyaleButton("Delete Account");
        deleteAccountButton.setActionCommand(DELETE_ACCOUNT_BUTTON_ACTION_COMMAND);
        deleteAccountButton.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(deleteAccountButton);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 30)));

        deleteAccountPanel = new DeleteAccountPanel();
    }


    public char[] getDeleteAccountPasswordFieldText(){
        return deleteAccountPanel.passwordTextField.getPassword();
    }

    public char[] getDeleteAccountConfirmPasswordFieldText(){
        return deleteAccountPanel.confirmPasswordTextField.getPassword();
    }

    public void setDeleteAccountError(String error){
        deleteAccountPanel.errorLabel.setText(error);
    }

    public void clearDeleteAccountError(){
        deleteAccountPanel.errorLabel.setText("   ");
    }

    public void pauseAllDeleteAccountComponents(){
        deleteAccountPanel.passwordTextField.setEnabled(false);
        deleteAccountPanel.confirmPasswordTextField.setEnabled(false);
        deleteAccountPanel.deleteAccountButton.setEnabled(false);
        deleteAccountPanel.returnToSettingsLabel.setClickable(false);
    }

    public void enableAllDeleteAccountComponents(){
        deleteAccountPanel.passwordTextField.setEnabled(true);
        deleteAccountPanel.confirmPasswordTextField.setEnabled(true);
        deleteAccountPanel.deleteAccountButton.setEnabled(true);
        deleteAccountPanel.returnToSettingsLabel.setClickable(true);
    }


    private class MainPanel extends JPanel{

        //This one will go on the MainPanel CENTER directly, with GridBagLayout and default constraints
        private JPanel panelOnTheCenterOne;

        //This one will go on top of the panelOnTheCenterOne, and it will be centered thanks to the GridBagLayout
        //It will have a BoxLayout Y_Axis so as to add components to it.
        private JPanel panelOnTheCenterTwo;

        private RoyaleButton exitButton;
        private RoyaleSlider musicSlider, soundSlider;


        public MainPanel(){
            setLayout(new BorderLayout());
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

            RoyaleLabel settingsLabel = new RoyaleLabel("Settings", RoyaleLabel.LabelType.TITLE);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new FlowLayout());
            exitButton = new RoyaleButton("Exit");
            exitButton.setActionCommand(EXIT_BUTTON_ACTION_COMMAND);
            bottomPanel.add(exitButton);

            panelOnTheCenterOne = new JPanel();
            panelOnTheCenterOne.setLayout(new GridBagLayout());
            panelOnTheCenterOne.setOpaque(false);

            panelOnTheCenterTwo = new JPanel();
            panelOnTheCenterTwo.setLayout(new BoxLayout(panelOnTheCenterTwo, BoxLayout.Y_AXIS));
            panelOnTheCenterTwo.setOpaque(false);


            musicSlider = new RoyaleSlider(JSlider.HORIZONTAL);
            musicSlider.setActionCommand(MUSIC_SLIDER_ACTION_COMMAND);
            musicSlider.setValue(MusicPlayer.getInstance().getVolume());
            RoyaleLabel musicLabel = new RoyaleLabel("Music", RoyaleLabel.LabelType.PARAGRAPH);

            soundSlider = new RoyaleSlider(JSlider.HORIZONTAL);
            soundSlider.setActionCommand(SOUND_SLIDER_ACTION_COMMAND);
            soundSlider.setValue(SoundPlayer.getInstance().getVolume());
            RoyaleLabel soundLabel = new RoyaleLabel("Sound", RoyaleLabel.LabelType.PARAGRAPH);

            JPanel musicAndSoundSlidersPanel = new JPanel();
            musicAndSoundSlidersPanel.setAlignmentX(CENTER_ALIGNMENT);
            musicAndSoundSlidersPanel.setOpaque(false);
            GroupLayout groupLayout = new GroupLayout(musicAndSoundSlidersPanel);
            musicAndSoundSlidersPanel.setLayout(groupLayout);
            groupLayout.setAutoCreateGaps(true);
            groupLayout.setAutoCreateContainerGaps(true);

            GroupLayout.SequentialGroup horizontalGroup = groupLayout.createSequentialGroup();
            horizontalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(musicLabel).addComponent(soundLabel));
            horizontalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(musicSlider).addComponent(soundSlider));
            groupLayout.setHorizontalGroup(horizontalGroup);

            GroupLayout.SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
            verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(musicLabel).addComponent(musicSlider));
            verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(soundLabel).addComponent(soundSlider));
            groupLayout.setVerticalGroup(verticalGroup);

            panelOnTheCenterTwo.add(musicAndSoundSlidersPanel);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(50, 50)));


            panelOnTheCenterOne.add(panelOnTheCenterTwo, new GridBagConstraints());


            add(settingsLabel, BorderLayout.NORTH);
            add(panelOnTheCenterOne, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        /**
         * Add to the MainPanel CENTER JPanel
         * @param comp
         * @return
         */
        @Override
        public Component add(Component comp) {
            return panelOnTheCenterTwo.add(comp);
        }

        @Override
        public Component add(Component comp, int index) {
            return panelOnTheCenterTwo.add(comp, index);
        }

        public void addButtonsListener(ActionListener al){
            exitButton.addActionListener(al);
            if(panelOnTheCenterTwo.getComponents() == null) return;

            //Add ActionListener to all the RoyaleButtons we have in the panelOnTheCenterTwo
            for(Component c: panelOnTheCenterTwo.getComponents())
                if(c instanceof RoyaleButton) ((RoyaleButton)c).addActionListener(al);
        }


        public void addSlidersListener(ChangeListener cl){
            soundSlider.addChangeListener(cl);
            musicSlider.addChangeListener(cl);
        }

    }



    private class LanguagePanel extends JPanel{

        //This one will go on the MainPanel CENTER directly, with GridBagLayout and default constraints
        private JPanel panelOnTheCenterOne;

        //This one will go on top of the panelOnTheCenterOne, and it will be centered thanks to the GridBagLayout
        //It will have a BoxLayout Y_Axis so as to add components to it.
        private JPanel panelOnTheCenterTwo;

        private RoyaleLabel returnToSettingsLabel;
        private RoyaleLabel englishLabel, spanishLabel, catalanLabel;


        public LanguagePanel(){
            setLayout(new BorderLayout());
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));


            panelOnTheCenterOne = new JPanel();
            panelOnTheCenterOne.setLayout(new GridBagLayout());
            panelOnTheCenterOne.setOpaque(false);

            panelOnTheCenterTwo = new JPanel();
            panelOnTheCenterTwo.setLayout(new BoxLayout(panelOnTheCenterTwo, BoxLayout.Y_AXIS));
            panelOnTheCenterTwo.setOpaque(false);

            englishLabel = new RoyaleLabel("    English", RoyaleLabel.LabelType.LINK);
            englishLabel.setClickable(true);
            englishLabel.setActionCommand(CHANGE_LANGUAGE_TO_ENGLISH_ACTION_COMMAND);
            spanishLabel = new RoyaleLabel("    Español", RoyaleLabel.LabelType.LINK);
            spanishLabel.setClickable(true);
            spanishLabel.setActionCommand(CHANGE_LANGUAGE_TO_SPANISH_ACTION_COMMAND);
            catalanLabel = new RoyaleLabel("    Català", RoyaleLabel.LabelType.LINK);
            catalanLabel.setClickable(true);
            catalanLabel.setActionCommand(CHANGE_LANGUAGE_TO_CATALAN_ACTION_COMMAND);

            RoyaleLabel englishFlag = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().getEnglishFlag()));
            RoyaleLabel spanishFlag = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().getSpanishFlag()));
            RoyaleLabel catalanFlag = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().getCatalanFlag()));

            JPanel englishPanel = new JPanel();
            englishPanel.setOpaque(false);
            englishPanel.add(englishFlag);
            englishPanel.add(englishLabel);
            JPanel spanishPanel = new JPanel();
            spanishPanel.setOpaque(false);
            spanishPanel.add(spanishFlag);
            spanishPanel.add(spanishLabel);
            JPanel catalanPanel = new JPanel();
            catalanPanel.setOpaque(false);
            catalanPanel.add(catalanFlag);
            catalanPanel.add(catalanLabel);

            panelOnTheCenterTwo.add(englishPanel);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(30, 30)));
            panelOnTheCenterTwo.add(spanishPanel);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(30, 30)));
            panelOnTheCenterTwo.add(catalanPanel);


            panelOnTheCenterOne.add(panelOnTheCenterTwo, new GridBagConstraints());

            returnToSettingsLabel = new RoyaleLabel("Return to Settings", RoyaleLabel.LabelType.LINK);
            returnToSettingsLabel.setActionCommand(RETURN_TO_MAIN_MENU_ACTION_COMMAND);
            returnToSettingsLabel.setClickable(true);


            add(new RoyaleLabel("Languages", RoyaleLabel.LabelType.TITLE), BorderLayout.NORTH);
            add(panelOnTheCenterOne, BorderLayout.CENTER);
            add(returnToSettingsLabel, BorderLayout.SOUTH);
        }

        public void addLabelsListener(MouseListener ml){
            returnToSettingsLabel.addMouseListener(ml);
            englishLabel.addMouseListener(ml);
            spanishLabel.addMouseListener(ml);
            catalanLabel.addMouseListener(ml);
        }

    }


    private class CreditsPanel extends JPanel{

        //This one will go on the MainPanel CENTER directly, with GridBagLayout and default constraints
        private JPanel panelOnTheCenterOne;

        //This one will go on top of the panelOnTheCenterOne, and it will be centered thanks to the GridBagLayout
        //It will have a BoxLayout Y_Axis so as to add components to it.
        private JPanel panelOnTheCenterTwo;

        private RoyaleLabel returnToSettingsLabel;

        public CreditsPanel(){
            setLayout(new BorderLayout());
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));


            panelOnTheCenterOne = new JPanel();
            panelOnTheCenterOne.setLayout(new GridBagLayout());
            panelOnTheCenterOne.setOpaque(false);

            panelOnTheCenterTwo = new JPanel();
            panelOnTheCenterTwo.setLayout(new BoxLayout(panelOnTheCenterTwo, BoxLayout.Y_AXIS));
            panelOnTheCenterTwo.setOpaque(false);

            RoyaleLabel gameMadeByLabel = new RoyaleLabel("WarRoyale has been produced and designed by:", RoyaleLabel.LabelType.PARAGRAPH);
            gameMadeByLabel.setAlignmentX(CENTER_ALIGNMENT);
            gameMadeByLabel.setForeground(MenuGraphics.BLUE);
            RoyaleLabel marcCano = new RoyaleLabel("Marc Cano", RoyaleLabel.LabelType.PARAGRAPH);
            marcCano.setAlignmentX(CENTER_ALIGNMENT);
            RoyaleLabel bielCarpi = new RoyaleLabel("Biel Carpi", RoyaleLabel.LabelType.PARAGRAPH);
            bielCarpi.setAlignmentX(CENTER_ALIGNMENT);
            RoyaleLabel rafaelMorera = new RoyaleLabel("Rafael Morera", RoyaleLabel.LabelType.PARAGRAPH);
            rafaelMorera.setAlignmentX(CENTER_ALIGNMENT);
            RoyaleLabel polSaula = new RoyaleLabel("Pol Saula", RoyaleLabel.LabelType.PARAGRAPH);
            polSaula.setAlignmentX(CENTER_ALIGNMENT);
            RoyaleLabel davidBassols = new RoyaleLabel("David Bassols", RoyaleLabel.LabelType.PARAGRAPH);
            davidBassols.setAlignmentX(CENTER_ALIGNMENT);

            panelOnTheCenterTwo.add(gameMadeByLabel);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(30, 30)));
            panelOnTheCenterTwo.add(marcCano);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOnTheCenterTwo.add(bielCarpi);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOnTheCenterTwo.add(rafaelMorera);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOnTheCenterTwo.add(polSaula);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOnTheCenterTwo.add(davidBassols);

            panelOnTheCenterOne.add(panelOnTheCenterTwo, new GridBagConstraints());


            returnToSettingsLabel = new RoyaleLabel("Return to Settings", RoyaleLabel.LabelType.LINK);
            returnToSettingsLabel.setActionCommand(RETURN_TO_MAIN_MENU_ACTION_COMMAND);
            returnToSettingsLabel.setClickable(true);


            add(new RoyaleLabel("Credits", RoyaleLabel.LabelType.TITLE), BorderLayout.NORTH);
            add(panelOnTheCenterOne, BorderLayout.CENTER);
            add(returnToSettingsLabel, BorderLayout.SOUTH);
        }

        public void addLabelsListener(MouseListener ml){
            returnToSettingsLabel.addMouseListener(ml);
        }

    }


    private class DeleteAccountPanel extends JPanel{

        //This one will go on the MainPanel CENTER directly, with GridBagLayout and default constraints
        private JPanel panelOnTheCenterOne;

        //This one will go on top of the panelOnTheCenterOne, and it will be centered thanks to the GridBagLayout
        //It will have a BoxLayout Y_Axis so as to add components to it.
        private JPanel panelOnTheCenterTwo;

        private RoyaleLabel returnToSettingsLabel;
        private RoyalePasswordField passwordTextField, confirmPasswordTextField;

        private RoyaleButton deleteAccountButton;
        private RoyaleLabel errorLabel;


        public DeleteAccountPanel(){
            setLayout(new BorderLayout());
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));


            panelOnTheCenterOne = new JPanel();
            panelOnTheCenterOne.setLayout(new GridBagLayout());
            panelOnTheCenterOne.setOpaque(false);

            panelOnTheCenterTwo = new JPanel();
            panelOnTheCenterTwo.setLayout(new BoxLayout(panelOnTheCenterTwo, BoxLayout.Y_AXIS));
            panelOnTheCenterTwo.setOpaque(false);

            RoyaleLabel deleteAccountLabel = new RoyaleLabel("Are you sure you want to delete your account?", RoyaleLabel.LabelType.PARAGRAPH);
            deleteAccountLabel.setAlignmentX(CENTER_ALIGNMENT);
            deleteAccountLabel.setForeground(MenuGraphics.RED);
            RoyaleLabel wellMissYou = new RoyaleLabel("We'll miss you :(", RoyaleLabel.LabelType.PARAGRAPH);
            wellMissYou.setForeground(MenuGraphics.YELLOW);
            wellMissYou.setAlignmentX(CENTER_ALIGNMENT);

            RoyaleLabel introducePassword = new RoyaleLabel("To continue, introduce your password:", RoyaleLabel.LabelType.PARAGRAPH);
            introducePassword.setAlignmentX(CENTER_ALIGNMENT);


            JPanel passwordsGroupPanel = new JPanel();
            passwordsGroupPanel.setAlignmentX(CENTER_ALIGNMENT);
            passwordsGroupPanel.setOpaque(false);
            GroupLayout groupLayout = new GroupLayout(passwordsGroupPanel);
            passwordsGroupPanel.setLayout(groupLayout);
            groupLayout.setAutoCreateGaps(true);
            groupLayout.setAutoCreateContainerGaps(true);

            RoyaleLabel passwordLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.PASSWORD), RoyaleLabel.LabelType.PARAGRAPH);
            passwordTextField = new RoyalePasswordField();

            RoyaleLabel confirmPasswordLabel = new RoyaleLabel(LanguageManager.getSentence(Sentences.CONFIRM_PASSWORD), RoyaleLabel.LabelType.PARAGRAPH);
            confirmPasswordTextField = new RoyalePasswordField();

            GroupLayout.SequentialGroup horizontalGroup = groupLayout.createSequentialGroup();
            horizontalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(passwordLabel).addComponent(confirmPasswordLabel));
            horizontalGroup.addGroup(groupLayout.createParallelGroup().addComponent(passwordTextField).addComponent(confirmPasswordTextField));
            groupLayout.setHorizontalGroup(horizontalGroup);

            GroupLayout.SequentialGroup verticalGroup = groupLayout.createSequentialGroup();
            verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel).addComponent(passwordTextField));
            verticalGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(confirmPasswordLabel).addComponent(confirmPasswordTextField));
            groupLayout.setVerticalGroup(verticalGroup);


            errorLabel = new RoyaleLabel("   ", RoyaleLabel.LabelType.ERROR);
            errorLabel.setAlignmentX(CENTER_ALIGNMENT);

            deleteAccountButton = new RoyaleButton("Delete Account");
            deleteAccountButton.setActionCommand(CONFIRM_DELETE_ACCOUNT_BUTTON_ACTION_COMMAND);
            deleteAccountButton.setBackground(MenuGraphics.RED);
            deleteAccountButton.setForeground(Color.WHITE);
            deleteAccountButton.setAlignmentX(CENTER_ALIGNMENT);

            panelOnTheCenterTwo.add(deleteAccountLabel);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOnTheCenterTwo.add(wellMissYou);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(40, 40)));
            panelOnTheCenterTwo.add(introducePassword);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(20, 20)));
            panelOnTheCenterTwo.add(passwordsGroupPanel);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOnTheCenterTwo.add(errorLabel);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(20, 20)));
            panelOnTheCenterTwo.add(deleteAccountButton);

            panelOnTheCenterOne.add(panelOnTheCenterTwo, new GridBagConstraints());


            returnToSettingsLabel = new RoyaleLabel("Return to Settings", RoyaleLabel.LabelType.LINK);
            returnToSettingsLabel.setActionCommand(RETURN_TO_MAIN_MENU_ACTION_COMMAND);
            returnToSettingsLabel.setClickable(true);


            add(new RoyaleLabel("Delete Account", RoyaleLabel.LabelType.TITLE), BorderLayout.NORTH);
            add(panelOnTheCenterOne, BorderLayout.CENTER);
            add(returnToSettingsLabel, BorderLayout.SOUTH);
        }

        public void addLabelsListener(MouseListener ml){
            returnToSettingsLabel.addMouseListener(ml);
        }

        public void addButtonsListener(ActionListener al){
            deleteAccountButton.addActionListener(al);
        }

    }

}

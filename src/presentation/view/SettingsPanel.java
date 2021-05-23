package presentation.view;

import business.entities.LanguageManager;
import business.entities.Sentences;
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


/**
 * {@code SettingsPanel} is a {@link FrontPanel} that represents the Settings for a determined {@link Screen}.
 * <p>It must be created and listened through its controller, a {@link presentation.controller.SettingsPanelController}
 *
 * <p><b>This class can be customized.</b> Initially, only the Exit button and music/sound sliders are shown.
 * Calling its {@code add()} methods, will make appear other things such as the Change Languages button,
 * Log Out button, etc.
 *
 * @version 1.0
 * @see presentation.controller.SettingsPanelController
 */
public class SettingsPanel extends FrontPanel {


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
    private ConfirmationBeforeExitingPanel confirmationBeforeExitingPanel;

    private TroopsGraphic troopsGraphic;



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

    public static final String CONFIRM_NEGATIVE_BUTTON_ACTION_COMMAND = "negative_button";
    public static final String CONFIRM_POSITIVE_LOGOUT_BUTTON_ACTION_COMMAND = "positive_logout_button";
    public static final String CONFIRM_POSITIVE_EXIT_BUTTON_ACTION_COMMAND = "positive_exit_button";


    /**
     * Default SettingsPanel Constructor.
     * @param panelWidth Width that this panel will have.
     * @param panelHeight Height that this panel will have.
     */
    public SettingsPanel(int panelWidth, int panelHeight){
        super(panelWidth, panelHeight);

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
        scrollPaneCentered.getVerticalScrollBar().setUnitIncrement(6);

        add(scrollPaneCentered);
    }



    /**
     * To be called whenever the {@link FrontPanel} is created , so as to add a listener to its buttons
     * @param al The listener of the Buttons
     */
    public void addButtonsListener(ActionListener al){
        mainPanel.addButtonsListener(al);
        for(Component c: mainPanel.getComponents()){
            if(c instanceof RoyaleButton) ((RoyaleButton) c).addActionListener(al);
        }

        if(deleteAccountPanel != null) deleteAccountPanel.addButtonsListener(al);
        if(confirmationBeforeExitingPanel != null) confirmationBeforeExitingPanel.addButtonsListener(al);
    }


    /**
     * To be called whenever the {@link FrontPanel} is created, so as to add a listener to its clickable labels
     * @param ml The listener of the clickable labels
     */
    public void addLabelsListener(MouseListener ml){
        if(creditsPanel != null) creditsPanel.addLabelsListener(ml);
        if(languagePanel != null) languagePanel.addLabelsListener(ml);
        if(deleteAccountPanel != null) deleteAccountPanel.addLabelsListener(ml);
    }


    /**
     * To be called whenever the {@link FrontPanel} is created, so as to add a listener to its sliders
     * @param cl The listener of the sliders
     */
    public void addSlidersListener(ChangeListener cl){
        mainPanel.addSlidersListener(cl);
    }


    /**
     * Shows the Change Languages Panel
     */
    public void showLanguagesPanel(){
        if(languagePanel != null) scrollPaneCentered.setViewportView(languagePanel);
        repaint();
        revalidate();
    }

    /**
     * Shows the Credits Panel
     */
    public void showCreditsPanel(){
        scrollPaneCentered.setViewportView(creditsPanel);
        repaint();
        revalidate();
    }

    /**
     * Shows the Main Panel
     */
    public void showMainPanel(){
        scrollPaneCentered.setViewportView(mainPanel);
        repaint();
        revalidate();
    }

    /**
     * Shows the Delete Account Panel
     */
    public void showDeleteAccountPanel(){
        scrollPaneCentered.setViewportView(deleteAccountPanel);
        repaint();
        revalidate();
    }

    /**
     * Shows the Panel that asks for confirmation before exiting the game
     * @param exitGameOrLogOut Whether when clicked YES, the System is exited or only Logged out.
     * {@code true} if you want to exit the game, {@code false} if your want to log out.
     */
    public void showConfirmationBeforeExitingGame(boolean exitGameOrLogOut){
        confirmationBeforeExitingPanel.modifyPanel(exitGameOrLogOut ? "Exiting": "Logging Out", exitGameOrLogOut);
        scrollPaneCentered.setViewportView(confirmationBeforeExitingPanel);
        repaint();
        revalidate();
    }




    //-------------------------------------------------------------------------------------
    //add() methods -----------------------------------------------------------------------

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
        RoyaleLabel playerLevel = new RoyaleLabel("Arena " + arena, RoyaleLabel.LabelType.PARAGRAPH);
        playerLevel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel playerCrownsPanel = new JPanel();
        playerCrownsPanel.setAlignmentX(CENTER_ALIGNMENT);
        playerCrownsPanel.setOpaque(false);
        RoyaleLabel playerCrowns = new RoyaleLabel(Integer.toString(crowns), RoyaleLabel.LabelType.PARAGRAPH);
        playerCrownsPanel.add(playerCrowns);
        ImageIcon crownsImage = new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getCrown(), playerCrowns.getFontMetrics(playerCrowns.getFont()).getHeight()));
        playerCrownsPanel.add(new RoyaleLabel(crownsImage));

        playerInformationPanel.add(playerName);
        playerInformationPanel.add(playerLevel);
        playerInformationPanel.add(playerCrownsPanel);

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


    /**
     * Adds the Change Languages button so as to be able to enter the change language menu
     */
    public void addLanguagesButton(){
        RoyaleButton changeLanguageButton = new RoyaleButton("Change Language");
        changeLanguageButton.setActionCommand(CHANGE_LANGUAGE_BUTTON_ACTION_COMMAND);
        changeLanguageButton.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(changeLanguageButton);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 30)));

        languagePanel = new LanguagePanel();
    }


    /**
     * Adds the Delete Account button so as to be able to enter the delete account menu
     */
    public void addDeleteAccountButton(){
        RoyaleButton deleteAccountButton = new RoyaleButton("Delete Account");
        deleteAccountButton.setActionCommand(DELETE_ACCOUNT_BUTTON_ACTION_COMMAND);
        deleteAccountButton.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(deleteAccountButton);
        mainPanel.add(Box.createRigidArea(new Dimension(30, 30)));

        deleteAccountPanel = new DeleteAccountPanel();
    }


    /**
     * Before exiting the game or logging out, asks the user whether he is sure of its decision or not.
     * If it is, that means he'll loose the game.
     * <p>Note that this method should only be called when the {@link SettingsPanel} belongs to a
     * {@link presentation.controller.BattleController}
     */
    public void addConfirmationBeforeExiting(){
        confirmationBeforeExitingPanel = new ConfirmationBeforeExitingPanel();
    }


    /**
     * Undo the previous call to {@link #addConfirmationBeforeExiting()}
     */
    public void removeConfirmationBeforeExiting(){
        confirmationBeforeExitingPanel = null;

    }


    /**
     * It will add a JPanel with a Graphic showing the number of troops that the user has, and the number of troops that the enemy has
     * <p>In order to update the stats, the method {@link #changeTroopsStats(int, int)} must be called.
     */
    public void addTroopsStats(){
        troopsGraphic = new TroopsGraphic(PANEL_WIDTH * 30/100);
        troopsGraphic.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(troopsGraphic);
        mainPanel.add(Box.createRigidArea(new Dimension(15, 50)));
    }

    //-------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------



    /**
     * To be called whenever the troops stats changed. It will update the graphic on the screen
     * @param playerTroops Number of player troops
     * @param enemyTroops Number of enemy troops
     */
    public void changeTroopsStats(int playerTroops, int enemyTroops){
        if(troopsGraphic == null) return;
        troopsGraphic.setStatsAndRepaint(playerTroops, enemyTroops);
    }


    /**
     * Returns the char[] inside the DeleteAccount Password TextField
     * @return char[] inside the DeleteAccount Password TextField
     */
    public char[] getDeleteAccountPasswordFieldText(){
        return deleteAccountPanel.passwordTextField.getPassword();
    }

    /**
     * Returns the char[] inside the DeleteAccount Confirm Password TextField
     * @return char[] inside the DeleteAccount Confirm Password TextField
     */
    public char[] getDeleteAccountConfirmPasswordFieldText(){
        return deleteAccountPanel.confirmPasswordTextField.getPassword();
    }

    /**
     * Sets an error message below the DeleteAccount passwords TextFields
     * @param error The error message to show
     */
    public void setDeleteAccountError(String error){
        deleteAccountPanel.errorLabel.setText(error);
    }

    /**
     * Clears the DeleteAccount passwords Error Message
     */
    public void clearDeleteAccountError(){
        deleteAccountPanel.errorLabel.setText("   ");
    }

    /**
     * Pauses all components so as the user is not able to click anything or carry out any action
     */
    public void pauseAllDeleteAccountComponents(){
        deleteAccountPanel.passwordTextField.setEnabled(false);
        deleteAccountPanel.confirmPasswordTextField.setEnabled(false);
        deleteAccountPanel.deleteAccountButton.setEnabled(false);
        deleteAccountPanel.returnToSettingsLabel.setClickable(false);
    }

    /**
     * Enables all components so as the user can carry out actions again
     */
    public void enableAllDeleteAccountComponents(){
        deleteAccountPanel.passwordTextField.setEnabled(true);
        deleteAccountPanel.confirmPasswordTextField.setEnabled(true);
        deleteAccountPanel.deleteAccountButton.setEnabled(true);
        deleteAccountPanel.returnToSettingsLabel.setClickable(true);
    }

    /**
     * Returns whether the confirmation before exiting is enabled or not
     * @return Whether the confirmation before exiting is enabled or not
     */
    public boolean isConfirmationBeforeExitingEnabled() {
        return confirmationBeforeExitingPanel != null;
    }


    private class MainPanel extends JPanel{

        //This one will go on the MainPanel CENTER directly, with GridBagLayout and default constraints
        private JPanel panelOnTheCenterOne;

        //This one will go on top of the panelOnTheCenterOne, and it will be centered thanks to the GridBagLayout
        //It will have a BoxLayout Y_Axis so as to add components to it.
        private JPanel panelOnTheCenterTwo;

        private RoyaleButton exitButton;
        private RoyaleSlider musicSlider, soundSlider;


        private MainPanel(){
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
         * {@inheritDoc}
         */
        @Override
        public Component add(Component comp) {
            return panelOnTheCenterTwo.add(comp);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Component add(Component comp, int index) {
            return panelOnTheCenterTwo.add(comp, index);
        }

        private void addButtonsListener(ActionListener al){
            exitButton.addActionListener(al);
            if(panelOnTheCenterTwo.getComponents() == null) return;

            //Add ActionListener to all the RoyaleButtons we have in the panelOnTheCenterTwo
            for(Component c: panelOnTheCenterTwo.getComponents())
                if(c instanceof RoyaleButton) ((RoyaleButton)c).addActionListener(al);
        }


        private void addSlidersListener(ChangeListener cl){
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


        private LanguagePanel(){
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

            RoyaleLabel englishFlag = new RoyaleLabel(new ImageIcon(MenuGraphics.getEnglishFlag()));
            RoyaleLabel spanishFlag = new RoyaleLabel(new ImageIcon(MenuGraphics.getSpanishFlag()));
            RoyaleLabel catalanFlag = new RoyaleLabel(new ImageIcon(MenuGraphics.getCatalanFlag()));

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

        private void addLabelsListener(MouseListener ml){
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

        private CreditsPanel(){
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


        private DeleteAccountPanel(){
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

        private void addLabelsListener(MouseListener ml){
            returnToSettingsLabel.addMouseListener(ml);
        }

        private void addButtonsListener(ActionListener al){
            deleteAccountButton.addActionListener(al);
        }
    }






    private class ConfirmationBeforeExitingPanel extends JPanel{

        //This one will go on the MainPanel CENTER directly, with GridBagLayout and default constraints
        private JPanel panelOnTheCenterOne;

        //This one will go on top of the panelOnTheCenterOne, and it will be centered thanks to the GridBagLayout
        //It will have a BoxLayout Y_Axis so as to add components to it.
        private JPanel panelOnTheCenterTwo;

        private RoyaleButton noButton, yesButton;
        private RoyaleLabel titleLabel;


        private ConfirmationBeforeExitingPanel(){
            setLayout(new BorderLayout());
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

            panelOnTheCenterOne = new JPanel();
            panelOnTheCenterOne.setLayout(new GridBagLayout());
            panelOnTheCenterOne.setOpaque(false);

            panelOnTheCenterTwo = new JPanel();
            panelOnTheCenterTwo.setLayout(new BoxLayout(panelOnTheCenterTwo, BoxLayout.Y_AXIS));
            panelOnTheCenterTwo.setOpaque(false);


            titleLabel = new RoyaleLabel("Logging Out", RoyaleLabel.LabelType.TITLE);

            RoyaleLabel confirmationMessage = new RoyaleLabel("Are you sure you want to exit? You'll loose the Game", RoyaleLabel.LabelType.PARAGRAPH);
            confirmationMessage.setAlignmentX(CENTER_ALIGNMENT);

            JPanel yesOrNoPanel = new JPanel();
            yesOrNoPanel.setOpaque(false);
            yesOrNoPanel.setAlignmentX(CENTER_ALIGNMENT);
            yesOrNoPanel.setLayout(new BoxLayout(yesOrNoPanel, BoxLayout.X_AXIS));
            noButton = new RoyaleButton("No");
            yesButton = new RoyaleButton("Yes");
            yesOrNoPanel.add(noButton);
            yesOrNoPanel.add(Box.createRigidArea(new Dimension(80, 30)));
            yesOrNoPanel.add(yesButton);

            panelOnTheCenterTwo.add(confirmationMessage);
            panelOnTheCenterTwo.add(Box.createRigidArea(new Dimension(30, 50)));
            panelOnTheCenterTwo.add(yesOrNoPanel);


            panelOnTheCenterOne.add(panelOnTheCenterTwo, new GridBagConstraints());
            add(titleLabel, BorderLayout.NORTH);
            add(panelOnTheCenterOne, BorderLayout.CENTER);


            noButton.setActionCommand(CONFIRM_NEGATIVE_BUTTON_ACTION_COMMAND);
            yesButton.setActionCommand(CONFIRM_POSITIVE_LOGOUT_BUTTON_ACTION_COMMAND);
        }

        private void modifyPanel(String title, boolean exitGameOrLogOutOnYesPressed){
            titleLabel.setText(title);
            if(exitGameOrLogOutOnYesPressed) //If we want to exit the game on YES pressed
                yesButton.setActionCommand(CONFIRM_POSITIVE_EXIT_BUTTON_ACTION_COMMAND);
            else //If we want to log out on YES pressed
                yesButton.setActionCommand(CONFIRM_POSITIVE_LOGOUT_BUTTON_ACTION_COMMAND);
        }

        private void addButtonsListener(ActionListener al){
            noButton.addActionListener(al);
            yesButton.addActionListener(al);
        }
    }





    private class TroopsGraphic extends JPanel{

        private int playerTroops, enemyTroops;
        private final int BAR_MAX_WIDTH;
        private final int BAR_HEIGHT;
        private final int GRAPHIC_HEIGHT;


        private TroopsGraphic(int preferredWidth){
            playerTroops = 0;
            enemyTroops = 0;
            setOpaque(false);

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


            RoyaleLabel troopsLabel = new RoyaleLabel("Troops", RoyaleLabel.LabelType.PARAGRAPH);
            troopsLabel.setAlignmentX(CENTER_ALIGNMENT);
            troopsLabel.setForeground(Color.WHITE);

            add(troopsLabel);


            BAR_MAX_WIDTH = preferredWidth;
            BAR_HEIGHT = troopsLabel.getFontMetrics(troopsLabel.getFont()).getHeight()/2;
            GRAPHIC_HEIGHT = getPreferredSize().height + BAR_HEIGHT * 4;

            setPreferredSize(new Dimension(BAR_MAX_WIDTH, GRAPHIC_HEIGHT));
            setMaximumSize(new Dimension(BAR_MAX_WIDTH, GRAPHIC_HEIGHT));
            setMinimumSize(new Dimension(BAR_MAX_WIDTH, GRAPHIC_HEIGHT));
        }

        private void setStatsAndRepaint(int playerTroops, int enemyTroops){
            this.playerTroops = playerTroops;
            this.enemyTroops = enemyTroops;
            repaint();
            revalidate();
        }


        @Override
        protected void paintComponent(Graphics g) {
            //Let's paint the Graphic below the "Troops" text
            //-->

            final double maxTroops = playerTroops > enemyTroops ? playerTroops: enemyTroops;
            final int maxBarWidthWithoutText = BAR_MAX_WIDTH - 50;
            g.setFont(MenuGraphics.getMainFont());

            //Painting the Blue Bar (player troops)
            g.setColor(Color.BLUE);
            final int blueBarWidth = playerTroops == 0 ? 1: (int)((playerTroops / maxTroops) * maxBarWidthWithoutText);
            g.fillRoundRect(0, (int)(GRAPHIC_HEIGHT - BAR_HEIGHT * 2.5), blueBarWidth, BAR_HEIGHT, 10, 10);

            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(playerTroops), blueBarWidth + 10,(int)(GRAPHIC_HEIGHT - BAR_HEIGHT * 2.5) + this.getFontMetrics(this.getFont()).getHeight());


            //Painting the Red Bar (enemy troops)
            g.setColor(Color.RED);
            final int redBarWidth = enemyTroops == 0 ? 1: (int)((enemyTroops / maxTroops) * maxBarWidthWithoutText);
            g.fillRoundRect(0, GRAPHIC_HEIGHT - BAR_HEIGHT, redBarWidth, BAR_HEIGHT, 10, 10);

            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(enemyTroops), redBarWidth + 10,GRAPHIC_HEIGHT - BAR_HEIGHT + this.getFontMetrics(this.getFont()).getHeight());


            super.paintComponent(g);
        }
    }

}

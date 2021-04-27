package presentation.view;

import presentation.controller.SettingsPanelController;
import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class SettingsPanel extends JPanel {

    private final int PANEL_HEIGHT, PANEL_WIDTH;
    private Color backgroundColor = new Color(0, 0, 0, 220);

    private SettingsPanelController settingsPanelController; //The controller that all buttons will be registered with


    //There will be multiple panels in this Settings Panel -->
    //      - The first Panel, the Settings Panel, is going to have a BorderLayout and will occupy all the screen so as not to allow the user to click behind it
    //      - The second Panel, the Center Panel, will make the UI centered. This is where other panels should be added.
    //      - A third panel, the Main Settings Panel (mainPanel) will contain the Exit, Change Language, Log Out buttons, etc
    //      - Other panels can be created (like creditsPanel and languagePanel) so as to be added to the centerPanel (first delete the panel that will be inside the
    //          centerPanel, and then add the new one.
    private JPanel centerPanel;
    private JPanel mainPanel; //The panel that will be on the center of the SettingsPanel

    private CreditsPanel creditsPanel;
    private LanguagePanel languagePanel;


    public static final String EXIT_BUTTON_ACTION_COMMAND = "exit_button";
    public static final String LOG_OUT_BUTTON_ACTION_COMMAND = "logout_button";
    public static final String CHANGE_LANGUAGE_BUTTON_ACTION_COMMAND = "change_language_button";
    public static final String CREDITS_BUTTON_ACTION_COMMAND = "credits_button";

    public static final String RETURN_TO_MAIN_MENU_ACTION_COMMAND = "return_to_main_menu";



    public SettingsPanel(int panelWidth, int panelHeight){

        PANEL_HEIGHT = panelHeight;
        PANEL_WIDTH = panelWidth;

        setOpaque(false);
        setLayout(new BorderLayout());
        addMouseListener(settingsPanelController); //We need to capture all mouse events so as not to let the user allow to click behind the SettingsPanel

        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());


        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);


        RoyaleLabel settingsLabel = new RoyaleLabel("Settings", RoyaleLabel.LabelType.TITLE);
        settingsLabel.setAlignmentX(CENTER_ALIGNMENT);

        RoyaleButton exitButton = new RoyaleButton("Exit");
        exitButton.setActionCommand(EXIT_BUTTON_ACTION_COMMAND);
        exitButton.setAlignmentX(CENTER_ALIGNMENT);


        mainPanel.add(settingsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(100,(int)(PANEL_HEIGHT*0.10))));
        mainPanel.add(Box.createRigidArea(new Dimension(100,(int)(PANEL_HEIGHT*0.02))));
        mainPanel.add(exitButton);

        centerPanel.add(mainPanel, new GridBagConstraints());
        add(centerPanel, BorderLayout.CENTER);
    }



    public void addButtonsListener(ActionListener al){
        if(languagePanel != null) languagePanel.addButtonsListener(al);

        for(Component c: mainPanel.getComponents()){
            if(c instanceof RoyaleButton) ((RoyaleButton) c).addActionListener(al);
        }
    }


    public void addLabelsListener(MouseListener ml){
        if(creditsPanel != null) creditsPanel.addLabelsListener(ml);
        if(languagePanel != null) languagePanel.addLabelsListener(ml);
    }


    public void showLanguagesPanel(){
        centerPanel.removeAll();
        centerPanel.add(languagePanel);
        repaint();
        revalidate();
    }

    public void showCreditsPanel(){
        centerPanel.removeAll();
        centerPanel.add(creditsPanel);
        repaint();
        revalidate();
    }

    public void showMainPanel(){
        centerPanel.removeAll();
        centerPanel.add(mainPanel);
        repaint();
        revalidate();
    }


     /**
     * Adds player information to the settings panel
     * @param name Name of the Player
     * @param level Level of the Player
     * @param trophies Trophies of the Player
     */
    public void addPlayerInformation(String name, String level, String trophies){
        JPanel playerInformationPanel = new JPanel();
        playerInformationPanel.setAlignmentX(CENTER_ALIGNMENT);
        playerInformationPanel.setOpaque(false);
        playerInformationPanel.setLayout(new BoxLayout(playerInformationPanel, BoxLayout.Y_AXIS));

        RoyaleLabel playerName = new RoyaleLabel(name, RoyaleLabel.LabelType.PARAGRAPH);
        playerName.setAlignmentX(CENTER_ALIGNMENT);
        RoyaleLabel playerLevel = new RoyaleLabel(level, RoyaleLabel.LabelType.PARAGRAPH);
        playerLevel.setAlignmentX(CENTER_ALIGNMENT);
        RoyaleLabel playerTrophies = new RoyaleLabel(trophies, RoyaleLabel.LabelType.PARAGRAPH);
        playerTrophies.setAlignmentX(CENTER_ALIGNMENT);

        playerInformationPanel.add(playerName);
        playerInformationPanel.add(playerLevel);
        playerInformationPanel.add(playerTrophies);

        mainPanel.add(playerInformationPanel, 1);
    }


    /**
     * Adds the credits button to the settings panel as the last element before the EXIT button
     */
    public void addCreditsButton(){
        RoyaleButton creditsButton = new RoyaleButton("Credits");
        creditsButton.setAlignmentX(CENTER_ALIGNMENT);
        creditsButton.setActionCommand(CREDITS_BUTTON_ACTION_COMMAND);
        mainPanel.add(creditsButton, mainPanel.getComponents().length - 1);

        creditsPanel = new CreditsPanel();
    }


    /**
     * Adds the logout button to the settings panel as the last element before the EXIT button
     */
    public void addLogOutButton(){
        RoyaleButton logOutButton = new RoyaleButton("Log Out");
        logOutButton.setAlignmentX(CENTER_ALIGNMENT);
        logOutButton.setActionCommand(LOG_OUT_BUTTON_ACTION_COMMAND);
        mainPanel.add(logOutButton, mainPanel.getComponents().length - 1);
    }


    public void addLanguagesButton(){
        RoyaleButton changeLanguageButton = new RoyaleButton("Change Language");
        changeLanguageButton.setActionCommand(CHANGE_LANGUAGE_BUTTON_ACTION_COMMAND);
        changeLanguageButton.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(changeLanguageButton, mainPanel.getComponents().length - 1);

        languagePanel = new LanguagePanel();
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT, 12, 12);

        super.paintComponent(g2d);
    }



    private class LanguagePanel extends JPanel{

        private RoyaleLabel returnToSettingsLabel;

        public LanguagePanel(){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);

            RoyaleLabel languagesLabel = new RoyaleLabel("Languages", RoyaleLabel.LabelType.TITLE);
            languagesLabel.setAlignmentX(CENTER_ALIGNMENT);

            returnToSettingsLabel = new RoyaleLabel("Return to Settings", RoyaleLabel.LabelType.LINK);
            returnToSettingsLabel.setActionCommand(RETURN_TO_MAIN_MENU_ACTION_COMMAND);
            returnToSettingsLabel.setAlignmentX(CENTER_ALIGNMENT);
            returnToSettingsLabel.setClickable(true);

            add(languagesLabel);
            add(Box.createRigidArea(new Dimension(100,(int)(PANEL_HEIGHT*0.10))));
            add(Box.createRigidArea(new Dimension(100,(int)(PANEL_HEIGHT*0.10))));
            add(returnToSettingsLabel);
        }

        public void addButtonsListener(ActionListener al){

        }

        public void addLabelsListener(MouseListener ml){
            returnToSettingsLabel.addMouseListener(ml);
        }

    }


    private class CreditsPanel extends JPanel{

        private RoyaleLabel returnToSettingsLabel;

        public CreditsPanel(){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);

            RoyaleLabel creditsLabel = new RoyaleLabel("Credits", RoyaleLabel.LabelType.TITLE);
            creditsLabel.setAlignmentX(CENTER_ALIGNMENT);

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

            returnToSettingsLabel = new RoyaleLabel("Return to Settings", RoyaleLabel.LabelType.LINK);
            returnToSettingsLabel.setActionCommand(RETURN_TO_MAIN_MENU_ACTION_COMMAND);
            returnToSettingsLabel.setAlignmentX(CENTER_ALIGNMENT);
            returnToSettingsLabel.setClickable(true);

            add(creditsLabel);
            add(Box.createRigidArea(new Dimension(100,(int)(PANEL_HEIGHT*0.10))));
            add(marcCano);
            add(bielCarpi);
            add(rafaelMorera);
            add(polSaula);
            add(davidBassols);
            add(Box.createRigidArea(new Dimension(100,(int)(PANEL_HEIGHT*0.10))));
            add(returnToSettingsLabel);
        }

        public void addLabelsListener(MouseListener ml){
            returnToSettingsLabel.addMouseListener(ml);
        }

    }

}

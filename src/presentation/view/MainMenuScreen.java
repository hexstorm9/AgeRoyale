package presentation.view;

import business.entities.LanguageManager;
import business.entities.Sentences;
import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MainMenuScreen extends Screen {

    //JPanels that will be put on the CENTER of this MainMenuScreen (the main one is the battleMenuPanel)
    private CardsMenuPanel cardsMenuPanel;
    private BattleMenuPanel battleMenuPanel;
    private RankingsMenuPanel rankingMenuPanel;

    private RoyaleButton cardsMenuButton, battleMenuButton, rankingMenuButton;
    private RoyaleButton playButton;
    private RoyaleLabel arenaImage, chestImage, chestLabel1, chestLabel2, chestLabel3, chestLabel4, chestLabel5;
    private RoyaleLabel usernameLogo, usernameLabel, crownImage, crownsLabel;

    public static final String CARDS_MENU_BUTTON_ACTION_COMMAND = "cards_menu";
    public static final String BATTLE_MENU_BUTTON_ACTION_COMMAND = "battle_menu";
    public static final String RANKINGS_MENU_BUTTON_ACTION_COMMAND = "ranking_menu";

    public static final String PLAY_BUTTON_COMMAND = "play_menu";
    public static final String UNLOCK_CHEST1_COMMAND = "unlock_chest1";
    public static final String UNLOCK_CHEST2_COMMAND = "unlock_chest2";
    public static final String UNLOCK_CHEST3_COMMAND = "unlock_chest3";
    public static final String UNLOCK_CHEST4_COMMAND = "unlock_chest4";
    public static final String UNLOCK_CHEST5_COMMAND = "unlock_chest5";

    private final int SCREEN_HEIGHT;

    public MainMenuScreen(String username, int crowns, int battleWins, int battlePlays, int arena, int screenHeight){
        setLayout(new BorderLayout());
        SCREEN_HEIGHT = screenHeight;

        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.GRAY);
        northPanel.setAlignmentX(LEFT_ALIGNMENT);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setOpaque(false);
        usernameLogo = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getUsernameLogo(), (int) (screenHeight*0.05))));
        usernameLabel = new RoyaleLabel(username, RoyaleLabel.LabelType.PARAGRAPH);
        usernamePanel.add(usernameLogo);
        usernamePanel.add(Box.createRigidArea(new Dimension(10,(int)(screenHeight*0.03))));
        usernamePanel.add(usernameLabel);
        //usernamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel crownsPanel = new JPanel();
        crownsPanel.setLayout(new BoxLayout(crownsPanel, BoxLayout.X_AXIS));
        crownsPanel.setOpaque(false);
        crownImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getCrown(), (int) (screenHeight*0.05))));
        crownsLabel = new RoyaleLabel(String.valueOf(crowns), RoyaleLabel.LabelType.PARAGRAPH);
        crownsPanel.add(crownImage);
        crownsPanel.add(Box.createRigidArea(new Dimension(10,(int)(screenHeight*0.03))));
        crownsPanel.add(crownsLabel);
        //crownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        northPanel.add(usernamePanel);
        northPanel.add(Box.createRigidArea(new Dimension(50,(int)(screenHeight*0.03))));
        northPanel.add(crownsPanel);

        //build north panel displaying username, crowns, battlewins, battleplays and arena
        //Search from icons in www.freepik.com
        //Load icons with the MenuGraphics class and call a getter to get them --> menuGraphics.getInstance().getIconX();
        //To resize images (icons), use the MenuGraphics class (it has a method for resizing images). You'll need to resize the images to have the same height as the text

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLACK);
        cardsMenuButton = new RoyaleButton("Cards");
        cardsMenuButton.setActionCommand(CARDS_MENU_BUTTON_ACTION_COMMAND);
        battleMenuButton = new RoyaleButton("Battle");
        battleMenuButton.setActionCommand(BATTLE_MENU_BUTTON_ACTION_COMMAND);
        rankingMenuButton = new RoyaleButton("Rankings");
        rankingMenuButton.setActionCommand(RANKINGS_MENU_BUTTON_ACTION_COMMAND);
        southPanel.add(cardsMenuButton);
        southPanel.add(battleMenuButton);
        southPanel.add(rankingMenuButton);

        battleMenuPanel = new BattleMenuPanel(arena, screenHeight);
        cardsMenuPanel = new CardsMenuPanel();
        rankingMenuPanel = new RankingsMenuPanel();

        add(northPanel, BorderLayout.NORTH);
        add(battleMenuPanel, BorderLayout.CENTER); //We'll start with the battleMenuPanel on the center
        add(southPanel, BorderLayout.SOUTH);
    }

    public void addButtonsListener(ActionListener al){
        cardsMenuButton.addActionListener(al);
        battleMenuButton.addActionListener(al);
        rankingMenuButton.addActionListener(al);

        cardsMenuPanel.addButtonsListener(al);
        battleMenuPanel.addButtonsListener(al);
        rankingMenuPanel.addButtonsListener(al);
    }

    public void addLabelsListener(MouseListener ml){
        battleMenuPanel.addLabelsListener(ml);
    }

    public void putCardsMenuPanelToCenter(){
        //If what's inside the CENTER is the CardsMenu, return
        if(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER) == cardsMenuPanel) return;

        //Remove current component from the CENTER
        remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
        add(cardsMenuPanel, BorderLayout.CENTER); //Add the cardsMenuPanel to the CENTER
        repaint();
        revalidate();
    }

    public void putBattleMenuPanelToCenter(){
        //If what's inside the CENTER is the BattleMenu, return
        if(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER) == battleMenuPanel) return;

        //Remove current component from the CENTER
        remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
        add(battleMenuPanel, BorderLayout.CENTER); //Add the battleMenuPanel to the CENTER
        repaint();
        revalidate();
    }

    public void putRankingsMenuPanelToCenter(){
        //If what's inside the CENTER is the rankingsMenu, return
        if(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER) == rankingMenuPanel) return;

        //Remove current component from the CENTER
        remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
        add(rankingMenuPanel, BorderLayout.CENTER); //Add the rankingsMenuPanel to the CENTER
        repaint();
        revalidate();
    }

    private class CardsMenuPanel extends JPanel{
        public CardsMenuPanel(){
            setOpaque(false);
            setLayout(new GridBagLayout()); //To make everything centered

            JPanel centerPanel = new JPanel(); //The only thing we'll add in this cardsMenuPanel
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setOpaque(false);

            JPanel cardsGridPanel = new JPanel();
            cardsGridPanel.setAlignmentX(CENTER_ALIGNMENT);
            cardsGridPanel.setLayout(new GridLayout(2, 4));

            cardsGridPanel.add(new CardPanel("Giant", 1, 6));
            cardsGridPanel.add(new CardPanel("Giant", 1, 6));
            cardsGridPanel.add(new CardPanel("Giant", 1, 6));
            cardsGridPanel.add(new CardPanel("Giant", 1, 6));
            cardsGridPanel.add(new CardPanel("Giant", 1, 6));
            cardsGridPanel.add(new CardPanel("Giant", 1, 6));
            cardsGridPanel.add(new CardPanel("Giant", 1, 6));
            cardsGridPanel.add(new CardPanel("Giant", 1, 6));

            RoyaleLabel deckLabel = new RoyaleLabel("Deck", RoyaleLabel.LabelType.TITLE);
            deckLabel.setAlignmentX(CENTER_ALIGNMENT);

            centerPanel.add(deckLabel);
            centerPanel.add(Box.createRigidArea(new Dimension(50, SCREEN_HEIGHT * 3/100)));
            centerPanel.add(cardsGridPanel);
            add(centerPanel, new GridBagConstraints());
        }

        public void addButtonsListener(ActionListener al){

        }

        private class CardPanel extends JPanel{
            public CardPanel(String cardName, int cardLevel, int totalCards){
                //Each card should occupy 25% of screen height

                add(new RoyaleLabel(cardName, RoyaleLabel.LabelType.PARAGRAPH));
                add(new RoyaleLabel(Integer.toString(cardLevel), RoyaleLabel.LabelType.PARAGRAPH));
                add(new RoyaleLabel(Integer.toString(totalCards), RoyaleLabel.LabelType.PARAGRAPH));

                //Set size of the card to occupy 25% of the screen height
                Dimension d = getPreferredSize();
                d.height = SCREEN_HEIGHT * 25/100;
                setPreferredSize(d);
                setMinimumSize(d);
                setMaximumSize(d);
            }
        }
    }

    private class BattleMenuPanel extends JPanel{
        private int currentArena;

        public BattleMenuPanel(int currentArena, int screenHeight){
            this.currentArena = currentArena; //We need to know the current arena in order to load the gif of the arena
            setOpaque(false);
            //add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*1))));

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.setOpaque(false);

            //depenent de la arena es carregaran diferents gifs
            center.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
            arenaImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getArenaGif(), (int) (screenHeight*0.5))));
            arenaImage.setAlignmentX(CENTER_ALIGNMENT);
            center.add(arenaImage);
            center.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.03))));

            playButton = new RoyaleButton(LanguageManager.getSentence(Sentences.PLAY_GAME));
            playButton.setAlignmentX(CENTER_ALIGNMENT);
            center.add(playButton);
            center.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.03))));

            JPanel chest = new JPanel();
            chest.setLayout(new BoxLayout(chest, BoxLayout.X_AXIS));
            chest.setOpaque(false);

            JPanel chestContainer1 = new JPanel();
            chestContainer1.setLayout(new BoxLayout(chestContainer1, BoxLayout.Y_AXIS));
            chestContainer1.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getChest(),  (int) (screenHeight*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel1 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel1.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer1.add(chestImage);
            chestContainer1.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
            chestContainer1.add(chestLabel1);

            JPanel chestContainer2 = new JPanel();
            chestContainer2.setLayout(new BoxLayout(chestContainer2, BoxLayout.Y_AXIS));
            chestContainer2.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getChest(), (int) (screenHeight*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel2 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel2.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer2.add(chestImage);
            chestContainer2.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
            chestContainer2.add(chestLabel2);

            JPanel chestContainer3 = new JPanel();
            chestContainer3.setLayout(new BoxLayout(chestContainer3, BoxLayout.Y_AXIS));
            chestContainer3.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getChest(), (int) (screenHeight*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel3 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel3.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer3.add(chestImage);
            chestContainer3.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
            chestContainer3.add(chestLabel3);

            JPanel chestContainer4 = new JPanel();
            chestContainer4.setLayout(new BoxLayout(chestContainer4, BoxLayout.Y_AXIS));
            chestContainer4.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getChest(), (int) (screenHeight*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel4 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel4.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer4.add(chestImage);
            chestContainer4.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
            chestContainer4.add(chestLabel4);

            JPanel chestContainer5 = new JPanel();
            chestContainer5.setLayout(new BoxLayout(chestContainer5, BoxLayout.Y_AXIS));
            chestContainer5.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.getInstance().scaleImage(MenuGraphics.getInstance().getChest(), (int) (screenHeight*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel5 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel5.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer5.add(chestImage);
            chestContainer5.add(Box.createRigidArea(new Dimension(100,(int)(screenHeight*0.02))));
            chestContainer5.add(chestLabel5);

            chest.add(chestContainer1);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(screenHeight*0.001))));
            chest.add(chestContainer2);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(screenHeight*0.001))));
            chest.add(chestContainer3);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(screenHeight*0.001))));
            chest.add(chestContainer4);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(screenHeight*0.001))));
            chest.add(chestContainer5);

            center.add(chest);
            add(center, new GridBagConstraints());
        }

        public void addButtonsListener(ActionListener al){
            playButton.addActionListener(al);
            playButton.setActionCommand(PLAY_BUTTON_COMMAND);
        }

        public void addLabelsListener(MouseListener ml){
            chestLabel1.addMouseListener(ml);
            chestLabel1.setActionCommand(UNLOCK_CHEST1_COMMAND);
            chestLabel1.setClickable(true);

            chestLabel2.addMouseListener(ml);
            chestLabel2.setActionCommand(UNLOCK_CHEST2_COMMAND);
            chestLabel2.setClickable(true);

            chestLabel3.addMouseListener(ml);
            chestLabel3.setActionCommand(UNLOCK_CHEST3_COMMAND);
            chestLabel3.setClickable(true);

            chestLabel4.addMouseListener(ml);
            chestLabel4.setActionCommand(UNLOCK_CHEST4_COMMAND);
            chestLabel4.setClickable(true);

            chestLabel5.addMouseListener(ml);
            chestLabel5.setActionCommand(UNLOCK_CHEST5_COMMAND);
            chestLabel5.setClickable(true);
        }
    }

    private class RankingsMenuPanel extends JPanel{

        public RankingsMenuPanel(){
            setOpaque(false);
            setLayout(new GridBagLayout());

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setOpaque(false);

            ScrollableRankingPanel globalRankingByCrowns = new ScrollableRankingPanel("Global Ranking by Crowns");
            ScrollableRankingPanel globalRankingByWinRatio = new ScrollableRankingPanel("Global Ranking by Win Ratio");
            ScrollableRankingPanel latestBattles = new ScrollableRankingPanel("Latest Battles");


            JTabbedPane rankingsTabbedPane = new JTabbedPane();
            rankingsTabbedPane.add("Global Ranking", globalRankingByCrowns);
            rankingsTabbedPane.add("Win Ratio", globalRankingByWinRatio);
            rankingsTabbedPane.add("Latest Battles", latestBattles);

            RoyaleLabel rankingsLabel = new RoyaleLabel("Rankings", RoyaleLabel.LabelType.TITLE);
            rankingsLabel.setAlignmentX(CENTER_ALIGNMENT);


            centerPanel.add(rankingsLabel);
            centerPanel.add(Box.createRigidArea(new Dimension(50, SCREEN_HEIGHT * 3/100)));
            centerPanel.add(rankingsTabbedPane);

            add(centerPanel, new GridBagConstraints());
        }

        public void addButtonsListener(ActionListener al){

        }

        private class ScrollableRankingPanel extends JPanel{

            public ScrollableRankingPanel(String ranking){
                add(new RoyaleLabel(ranking, RoyaleLabel.LabelType.PARAGRAPH));
            }

        }
    }

    public void pauseAllComponents(){
        chestLabel1.setClickable(false);
        chestLabel2.setClickable(false);
        chestLabel3.setClickable(false);
        chestLabel4.setClickable(false);
        chestLabel5.setClickable(false);
        playButton.setEnabled(false);
    }



}

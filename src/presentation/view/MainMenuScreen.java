package presentation.view;

import business.Card;
import business.entities.Cards;
import business.entities.Player;
import presentation.graphics.MenuGraphics;
import business.entities.LanguageManager;
import business.entities.Sentences;
import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;


/**
 * The MainMenuScreen will be a {@link Screen} providing the user with the ability to see rankings, play battles, see
 * the cards they own...
 * <p>It has three tabs:
 * <ul>
 *     <li>1. Battle Tab: Where you can see your Chests, Arena and play new battles</li>
 *     <li>2. Rankings Tab: Where you can see the rankings of the Game and the latest battles played</li>
 *     <li>3. Cards Tab: Where you can see the cards you own, the level they have and your deck</li>
 * </ul>
 *
 * <p>A {@code MainMenuScreen} has to be created an controlled by a {@link presentation.controller.MainMenuController}
 *
 * @see presentation.controller.MainMenuController
 * @version 1.0
 */
public class MainMenuScreen extends Screen {

    //JPanels that will be put on the CENTER of this MainMenuScreen (the main one is the battleMenuPanel)
    private CardsMenuPanel cardsMenuPanel;
    private BattleMenuPanel battleMenuPanel;
    private RankingsMenuPanel rankingMenuPanel;

    private RoyaleButton cardsMenuButton, battleMenuButton, rankingMenuButton;
    private RoyaleButton playButton;
    private RoyaleLabel usernameLogo, usernameLabel, crownImage, crownsLabel;

    private Player currentPlayer;

    public static final String CARDS_MENU_BUTTON_ACTION_COMMAND = "cards_menu";
    public static final String BATTLE_MENU_BUTTON_ACTION_COMMAND = "battle_menu";
    public static final String RANKINGS_MENU_BUTTON_ACTION_COMMAND = "ranking_menu";

    public static final String CARD_PANEL_1_ACTION_COMMAND = "card_1";
    public static final String CARD_PANEL_2_ACTION_COMMAND = "card_2";
    public static final String CARD_PANEL_3_ACTION_COMMAND = "card_3";
    public static final String CARD_PANEL_4_ACTION_COMMAND = "card_4";
    public static final String CARD_PANEL_5_ACTION_COMMAND = "card_5";
    public static final String CARD_PANEL_6_ACTION_COMMAND = "card_6";
    public static final String CARD_PANEL_7_ACTION_COMMAND = "card_7";
    public static final String CARD_PANEL_8_ACTION_COMMAND = "card_8";


    public static final String PLAY_BUTTON_COMMAND = "play_menu";
    public static final String UNLOCK_CHEST1_COMMAND = "unlock_chest1";
    public static final String UNLOCK_CHEST2_COMMAND = "unlock_chest2";
    public static final String UNLOCK_CHEST3_COMMAND = "unlock_chest3";
    public static final String UNLOCK_CHEST4_COMMAND = "unlock_chest4";
    public static final String UNLOCK_CHEST5_COMMAND = "unlock_chest5";


    /**
     * Default MainMenuScreen Constructor.
     *
     * @param currentPlayer The current player that is playing (so as to get its information)
     * @param screenWidth The desired width of the screen
     * @param screenHeight The desired height of the screen
     */
    public MainMenuScreen(Player currentPlayer, int screenWidth, int screenHeight){
        super(screenHeight);
        this.currentPlayer = currentPlayer;

        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(new Color(0, 0, 0, 120));
                g2d.fillRoundRect(getLocation().x + 50, getLocation().y + 20, screenWidth - 100, getPreferredSize().height - 40, 50, 50);
                super.paintComponent(g);
            }
        };
        northPanel.setOpaque(false);
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setOpaque(false);
        usernameLogo = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getUsernameLogo(), (int) (SCREEN_HEIGHT*0.05))));
        usernameLabel = new RoyaleLabel(currentPlayer.getName(), RoyaleLabel.LabelType.PARAGRAPH);
        usernamePanel.add(usernameLogo);
        usernamePanel.add(Box.createRigidArea(new Dimension(10, 10)));
        usernamePanel.add(usernameLabel);

        JPanel crownsPanel = new JPanel();
        crownsPanel.setLayout(new BoxLayout(crownsPanel, BoxLayout.X_AXIS));
        crownsPanel.setOpaque(false);
        crownsLabel = new RoyaleLabel(currentPlayer.getCrowns() + " ", RoyaleLabel.LabelType.PARAGRAPH);
        crownImage = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getCrown(), crownsLabel.getFontMetrics(crownsLabel.getFont()).getHeight())));
        crownsPanel.add(crownsLabel);
        crownsPanel.add(crownImage);

        JPanel winRatePanel = new JPanel();
        winRatePanel.setOpaque(false);
        winRatePanel.setLayout(new BoxLayout(winRatePanel, BoxLayout.X_AXIS));
        final int winRate = (int)((currentPlayer.getBattleWins()/(double)currentPlayer.getBattlePlays()) * 100);
        RoyaleLabel percentageWinRateLabel = new RoyaleLabel(winRate + "%", RoyaleLabel.LabelType.PARAGRAPH);
        percentageWinRateLabel.setForeground(winRate >= 50? Color.GREEN: MenuGraphics.RED);
        winRatePanel.add(new RoyaleLabel("Win Rate: ", RoyaleLabel.LabelType.PARAGRAPH));
        winRatePanel.add(percentageWinRateLabel);

        northPanel.add(usernamePanel);
        northPanel.add(Box.createRigidArea(new Dimension(50,(int)(SCREEN_HEIGHT*0.03))));
        northPanel.add(crownsPanel);
        northPanel.add(Box.createRigidArea(new Dimension(50,(int)(SCREEN_HEIGHT*0.03))));
        northPanel.add(winRatePanel);


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

        battleMenuPanel = new BattleMenuPanel(currentPlayer.getArena());
        cardsMenuPanel = new CardsMenuPanel();
        rankingMenuPanel = new RankingsMenuPanel();


        add(northPanel, BorderLayout.NORTH);
        add(battleMenuPanel, BorderLayout.CENTER); //We'll start with the battleMenuPanel on the center
        add(southPanel, BorderLayout.SOUTH);
    }


    /**
     * To be called whenever the {@code Screen} is created, so as to add a listener to its buttons
     * @param al The listener of the Buttons
     */
    public void addButtonsListener(ActionListener al){
        cardsMenuButton.addActionListener(al);
        battleMenuButton.addActionListener(al);
        rankingMenuButton.addActionListener(al);

        battleMenuPanel.addButtonsListener(al);
        rankingMenuPanel.addButtonsListener(al);
    }


    /**
     * To be called whenever the {@code Screen} is created, so as to add a listener to its labels
     * @param ml The listener of the labels
     */
    public void addLabelsListener(MouseListener ml){
        battleMenuPanel.addLabelsListener(ml);
    }

    /**
     * To be called whenever the {@code Screen} is created, so as to add a listener to its panels
     * @param ml The listener of the Panels
     */
    public void addPanelsListener(MouseListener ml){
        cardsMenuPanel.addPanelsListener(ml);
    }


    /**
     * Shows the Cards Tab, hiding the other two tabs
     */
    public void putCardsMenuPanelToCenter(){
        //If what's inside the CENTER is the CardsMenu, return
        if(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER) == cardsMenuPanel) return;

        //Remove current component from the CENTER
        remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
        add(cardsMenuPanel, BorderLayout.CENTER); //Add the cardsMenuPanel to the CENTER
        repaint();
        revalidate();
    }

    /**
     * Shows the Battle Tab, hiding the other two tabs
     */
    public void putBattleMenuPanelToCenter(){
        //If what's inside the CENTER is the BattleMenu, return
        if(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER) == battleMenuPanel) return;

        //Remove current component from the CENTER
        remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
        add(battleMenuPanel, BorderLayout.CENTER); //Add the battleMenuPanel to the CENTER
        repaint();
        revalidate();
    }

    /**
     * Shows the Rankings tab, hiding the other two tabs
     */
    public void putRankingsMenuPanelToCenter(){
        //If what's inside the CENTER is the rankingsMenu, return
        if(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER) == rankingMenuPanel) return;

        //Remove current component from the CENTER
        remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
        add(rankingMenuPanel, BorderLayout.CENTER); //Add the rankingsMenuPanel to the CENTER
        repaint();
        revalidate();
    }


    /**
     * Pauses all components so as the user is not able to click anything or carry out any action
     */
    public void pauseAllComponents(){
        battleMenuPanel.pauseAllComponents();
    }


    /**
     * CardsMenuPanel is a class that will be put in a tab of the {@link MainMenuScreen}, providing a visual way for the
     * user to see its Cards status
     */
    public class CardsMenuPanel extends JPanel{

        private JPanel centerPanel;
        private JPanel cardsGridPanel;
        private Image woodTable;
        private int woodTableXCoord, woodTableYCoord;

        private CardsMenuPanel(){
            setOpaque(false);
            setLayout(new GridBagLayout()); //To make everything centered

            centerPanel = new JPanel(); //The only thing we'll add in this cardsMenuPanel
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setOpaque(false);

            cardsGridPanel = new JPanel();
            cardsGridPanel.setOpaque(false);
            cardsGridPanel.setAlignmentX(CENTER_ALIGNMENT);
            cardsGridPanel.setLayout(new GridLayout(2, 4));

            ArrayList<CardPanel> everyUserCard = new ArrayList<>();
            for(Cards c: Cards.values()){
                if(c.isTower()) continue;
                String cardName = c.toString().substring(0, 1).toUpperCase() + c.toString().substring(1);
                int cardLevel = currentPlayer.getPlayerCards().get(c);
                int userCards = 1;
                int cardsToNextLevel = 10;

                everyUserCard.add(new CardPanel(cardName, cardLevel, userCards, cardsToNextLevel, CARD_PANEL_1_ACTION_COMMAND));
            }

            for(int i = 0; i < 8; i++)
                cardsGridPanel.add(everyUserCard.get(i));


            RoyaleLabel deckLabel = new RoyaleLabel("Deck", RoyaleLabel.LabelType.TITLE);
            deckLabel.setAlignmentX(CENTER_ALIGNMENT);

            centerPanel.add(deckLabel);
            centerPanel.add(Box.createRigidArea(new Dimension(50, SCREEN_HEIGHT * 3/100)));
            centerPanel.add(cardsGridPanel);
            add(centerPanel, new GridBagConstraints());

            //Now that we added everything, let's add the woodTable and scale it accordingly
            woodTable = MenuGraphics.scaleImage(MenuGraphics.getWoodTableLight(), cardsGridPanel.getPreferredSize().height);
            //woodTableXCoord and woodTableYCoord will be calculated on runtime, when the screen is already displayed so as to
            //be able to use the cardsGridPanel.getLocationOnScreen() method
        }


        private void addPanelsListener(MouseListener ml){
            Component[] cardPanels =  cardsGridPanel.getComponents();
            if(cardPanels == null) return;

            for(Component c: cardPanels) c.addMouseListener(ml);
        }

        @Override
        protected void paintComponent(Graphics g) {
            //If woodTableXCoord is 0 (meaning it hasn't been initialized yet) let's calculate woodTableXCoord and woodTableYCoord
            if(woodTableXCoord == 0){
                JFrame parentJFrame = (JFrame) SwingUtilities.getWindowAncestor(cardsGridPanel); //Obtain a reference to the current JFrame

                final Point cardsGridPanelPosition = SwingUtilities.convertPoint(centerPanel, cardsGridPanel.getLocation().x,
                        cardsGridPanel.getLocation().y, parentJFrame.getContentPane());
                woodTableXCoord = cardsGridPanelPosition.x - (woodTable.getWidth(null) - cardsGridPanel.getPreferredSize().width)/2;

                int northHeight = ((BorderLayout) this.getParent().getLayout()).getLayoutComponent(BorderLayout.NORTH).getHeight();
                woodTableYCoord = cardsGridPanelPosition.y - northHeight;
            }

            Graphics2D g2d = (Graphics2D) g;
            //Draw the woodTable before everything else
            g2d.drawImage(woodTable, woodTableXCoord, woodTableYCoord, null);

            super.paintComponent(g);
        }


        /**
         * CardPanel will be a class representing the information for a single {@code Card} of the user.
         * <p>It will be put inside a {@link CardsMenuPanel}
         */
        public class CardPanel extends JPanel{
            private String actionCommand;

            public CardPanel(String cardName, int cardLevel, int userCards, int cardsToNextLevel, String actionCommand){
                this.actionCommand = actionCommand;

                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
                setOpaque(false);


                RoyaleLabel cardNameLabel = new RoyaleLabel(cardName, RoyaleLabel.LabelType.PARAGRAPH);
                cardNameLabel.setForeground(MenuGraphics.ORANGE);
                cardNameLabel.setAlignmentX(CENTER_ALIGNMENT);


                RoyaleLabel levelLabel = new RoyaleLabel("Level " + cardLevel, RoyaleLabel.LabelType.PARAGRAPH);
                levelLabel.setAlignmentX(CENTER_ALIGNMENT);


                RoyaleLabel numberCardsLabel = new RoyaleLabel(userCards + " / " + cardsToNextLevel , RoyaleLabel.LabelType.PARAGRAPH);
                numberCardsLabel.setForeground(MenuGraphics.LIGHT_GREY);
                numberCardsLabel.setAlignmentX(CENTER_ALIGNMENT);


                Image cardTestScaled = MenuGraphics.scaleImage(MenuGraphics.getCardSprite(Cards.fromString(cardName)) , (SCREEN_HEIGHT * 25/100) * 60/100);
                RoyaleLabel cardTestImage = new RoyaleLabel(new ImageIcon(cardTestScaled));
                cardTestImage.setAlignmentX(CENTER_ALIGNMENT);


                add(cardNameLabel);
                add(cardTestImage);
                add(levelLabel);
                add(Box.createRigidArea(new Dimension(10, 10)));
                add(numberCardsLabel);
            }

            /**
             * Returns the ActionCommand that this card has
             * @return The ActionCommand of this card
             */
            public String getActionCommand(){
                return actionCommand;
            }
        }

    }




    private class BattleMenuPanel extends JPanel{

        private int currentArena;
        private RoyaleLabel arenaImage, chestImage, chestLabel1, chestLabel2, chestLabel3, chestLabel4, chestLabel5;

        private BattleMenuPanel(int currentArena){
            this.currentArena = currentArena; //We need to know the current arena in order to load the gif of the arena
            setOpaque(false);

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.setOpaque(false);

            //depenent de la arena es carregaran diferents gifs
            center.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.02))));
            arenaImage = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getArenaGif(currentArena), (int)(SCREEN_HEIGHT*0.5))));
            arenaImage.setAlignmentX(CENTER_ALIGNMENT);
            center.add(arenaImage);
            center.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.03))));

            playButton = new RoyaleButton(LanguageManager.getSentence(Sentences.PLAY_GAME));
            playButton.setAlignmentX(CENTER_ALIGNMENT);
            center.add(playButton);
            center.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.03))));

            JPanel chest = new JPanel();
            chest.setLayout(new BoxLayout(chest, BoxLayout.X_AXIS));
            chest.setOpaque(false);

            JPanel chestContainer1 = new JPanel();
            chestContainer1.setLayout(new BoxLayout(chestContainer1, BoxLayout.Y_AXIS));
            chestContainer1.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getChest(),  (int) (SCREEN_HEIGHT*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel1 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel1.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer1.add(chestImage);
            chestContainer1.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.02))));
            chestContainer1.add(chestLabel1);

            JPanel chestContainer2 = new JPanel();
            chestContainer2.setLayout(new BoxLayout(chestContainer2, BoxLayout.Y_AXIS));
            chestContainer2.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getChest(), (int) (SCREEN_HEIGHT*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel2 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel2.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer2.add(chestImage);
            chestContainer2.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.02))));
            chestContainer2.add(chestLabel2);

            JPanel chestContainer3 = new JPanel();
            chestContainer3.setLayout(new BoxLayout(chestContainer3, BoxLayout.Y_AXIS));
            chestContainer3.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getChest(), (int) (SCREEN_HEIGHT*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel3 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel3.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer3.add(chestImage);
            chestContainer3.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.02))));
            chestContainer3.add(chestLabel3);

            JPanel chestContainer4 = new JPanel();
            chestContainer4.setLayout(new BoxLayout(chestContainer4, BoxLayout.Y_AXIS));
            chestContainer4.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getChest(), (int) (SCREEN_HEIGHT*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel4 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel4.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer4.add(chestImage);
            chestContainer4.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.02))));
            chestContainer4.add(chestLabel4);

            JPanel chestContainer5 = new JPanel();
            chestContainer5.setLayout(new BoxLayout(chestContainer5, BoxLayout.Y_AXIS));
            chestContainer5.setOpaque(false);
            chestImage = new RoyaleLabel(new ImageIcon(MenuGraphics.scaleImage(MenuGraphics.getChest(), (int) (SCREEN_HEIGHT*0.1))));
            chestImage.setAlignmentX(CENTER_ALIGNMENT);
            chestLabel5 = new RoyaleLabel(LanguageManager.getSentence(Sentences.UNLOCK_CHEST), RoyaleLabel.LabelType.LINK);
            chestLabel5.setAlignmentX(CENTER_ALIGNMENT);
            chestContainer5.add(chestImage);
            chestContainer5.add(Box.createRigidArea(new Dimension(100,(int)(SCREEN_HEIGHT*0.02))));
            chestContainer5.add(chestLabel5);

            chest.add(chestContainer1);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(SCREEN_HEIGHT*0.001))));
            chest.add(chestContainer2);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(SCREEN_HEIGHT*0.001))));
            chest.add(chestContainer3);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(SCREEN_HEIGHT*0.001))));
            chest.add(chestContainer4);
            chest.add(Box.createRigidArea(new Dimension(35,(int)(SCREEN_HEIGHT*0.001))));
            chest.add(chestContainer5);

            center.add(chest);
            add(center, new GridBagConstraints());
        }

        private void addButtonsListener(ActionListener al){
            playButton.addActionListener(al);
            playButton.setActionCommand(PLAY_BUTTON_COMMAND);
        }

        private void addLabelsListener(MouseListener ml){
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

        private void pauseAllComponents(){
            chestLabel1.setClickable(false);
            chestLabel2.setClickable(false);
            chestLabel3.setClickable(false);
            chestLabel4.setClickable(false);
            chestLabel5.setClickable(false);
            playButton.setEnabled(false);
        }
    }



    private class RankingsMenuPanel extends JPanel{

        private RankingsMenuPanel(){
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

        private void addButtonsListener(ActionListener al){

        }


        private class ScrollableRankingPanel extends JPanel{

            public ScrollableRankingPanel(String ranking){
                add(new RoyaleLabel(ranking, RoyaleLabel.LabelType.PARAGRAPH));
            }

        }
    }




}

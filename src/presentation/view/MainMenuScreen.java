package presentation.view;

import presentation.view.customcomponents.RoyaleButton;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuScreen extends Screen {

    //JPanels that will be put on the CENTER of this MainMenuScreen (the main one is the battleMenuPanel)
    private CardsMenuPanel cardsMenuPanel;
    private BattleMenuPanel battleMenuPanel;
    private RankingsMenuPanel rankingMenuPanel;

    private RoyaleButton cardsMenuButton, battleMenuButton, rankingMenuButton;

    public static final String CARDS_MENU_BUTTON_ACTION_COMMAND = "cards_menu";
    public static final String BATTLE_MENU_BUTTON_ACTION_COMMAND = "battle_menu";
    public static final String RANKINGS_MENU_BUTTON_ACTION_COMMAND = "ranking_menu";

    private final int SCREEN_HEIGHT;


    public MainMenuScreen(String username, int crowns, int battleWins, int battlePlays, int arena, int screenHeight){
        setLayout(new BorderLayout());
       SCREEN_HEIGHT = screenHeight;

        JPanel northPanel = new JPanel();
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


        battleMenuPanel = new BattleMenuPanel(arena);
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

        public BattleMenuPanel(int currentArena){
            this.currentArena = currentArena; //We need to know the current arena in order to load the gif of the arena

            setOpaque(false);
            add(new RoyaleLabel("We're in the Battle Menu Panel", RoyaleLabel.LabelType.TITLE));
        }

        public void addButtonsListener(ActionListener al){

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

}

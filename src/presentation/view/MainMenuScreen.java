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


    public MainMenuScreen(String username, int crowns, int battleWins, int battlePlays, int arena, int screenHeight){
        setLayout(new BorderLayout());

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
            add(new RoyaleLabel("We're in the cards Menu Panel", RoyaleLabel.LabelType.TITLE));
        }

        public void addButtonsListener(ActionListener al){

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
            add(new RoyaleLabel("We're in the Rankings Menu Panel", RoyaleLabel.LabelType.TITLE));

        }

        public void addButtonsListener(ActionListener al){

        }
    }

}

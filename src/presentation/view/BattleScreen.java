package presentation.view;

import business.entities.Cards;
import presentation.controller.BattleController;
import presentation.graphics.BattleGraphics;
import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * {@code BattleScreen} is the {@link Screen} that shows the whole Battle.
 * <p>It has to be controlled and listened through a {@link BattleController}
 *
 * @version 1.0
 * @see BattleController
 */
public class BattleScreen extends Screen {

    private BattleController battleController;

    private BattlePanel battlePanel;
    private SouthPanel southPanel;


    /**
     * Default BattleScreen Constructor.
     * <p>In order to paint itself, a reference to the {@link BattleController} that controls the {@code Screen} is needed.
     *
     * @param battleController The BattleController that controls this Screen
     * @param screenHeight The height that this Screen will have
     */
    public BattleScreen(BattleController battleController, int screenHeight){
        super(screenHeight);

        this.battleController = battleController;

        setLayout(new BorderLayout());

        battlePanel = new BattlePanel();
        battlePanel.addMouseListener(battleController);
        southPanel = new SouthPanel();

        add(battlePanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }


    /**
     * Draws again this {@link Screen} with updated information (asks for that updated information to the
     * {@link BattleController}.
     * <p>It should be called from the GameLoop, and it will determine the FPS at which the game runs.
     *
     * <p>It is not necessary to call this method from the Event-Dispatch Thread (EDT). It is thread-safe.
     */
    public void draw(){
        battlePanel.repaint();
        southPanel.goldProgressBar.repaint();
    }


    /**
     * Notifies the Screen that the Player cards shown need to be updated
     */
    public void updateCardsToShow (){ southPanel.updateCardsToShow();}



    /**
     * Returns the current card selected by the player. Null if there is no selected card
     * @return Card selected by the Player
     */
    public Cards getCardSelected(){ return southPanel.getCardSelected(); }

    /**
     * Sets the card selected by the Player (in order to apply an special effect to it)
     * @param c Card selected by the Player
     */
    public void setCardSelected(Cards c){ southPanel.setCardSelected(c); }


    /**
     * Returns the {@link Dimension} of the BattlePanel (the {@link JPanel} where the battle will take place)
     * @return {@link Dimension} representing the width and height of the BattlePanel
     *
     * @see Dimension
     */
    public Dimension getBattlePanelSize(){
        return new Dimension(getSize().width, getSize().height - southPanel.getSize().height);
    }


    /**
     * A JPanel inside the {@link BattleScreen} that will paint the whole Battle.
     */
    public class BattlePanel extends JPanel{

        private BattlePanel(){
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            //TODO: Drawable[] drawables = battleController.getDrawables();
            battleController.paintBattle(g);
            super.paintComponent(g);
        }

    }




    /**
     * A JPanel inside the {@link BattleScreen} that will hold information regarding to
     * the User Cards and User Gold
     */
    public class SouthPanel extends JPanel{

        private ArrayList<Cards> cardsToShow;

        private JPanel centerPanel;
        private JPanel cardsPanel;
        private GoldProgressBar goldProgressBar;

        private Image woodTile; //The background tile
        private int verticalWoodTiles, horizontalWoodTiles;


        private SouthPanel(){
            setPreferredSize(new Dimension(getPreferredSize().width, SCREEN_HEIGHT * 20/100));
            setLayout(new GridBagLayout());
            setOpaque(false);
            woodTile = BattleGraphics.getWoodTile();

            centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setOpaque(false);

            cardsPanel = new JPanel();
            cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.X_AXIS));
            cardsPanel.setOpaque(false);


            goldProgressBar = new GoldProgressBar(getPreferredSize().height * 20/100,500, getPreferredSize().height * 7/100);

            centerPanel.add(cardsPanel);
            centerPanel.add(goldProgressBar);
            add(centerPanel, new GridBagConstraints());
        }


        private void updateCardsToShow(){
            cardsPanel.removeAll();
            cardsPanel.setPreferredSize(new Dimension(cardsPanel.getPreferredSize().width, getPreferredSize().height * 75/100));
            cardsToShow = battleController.getCardsToShow();

            for(Cards c: cardsToShow){
                CardPanel cp = new CardPanel(c, getPreferredSize().height * 65/100, getPreferredSize().height * 75/100);
                cp.addMouseListener(battleController);
                cardsPanel.add(cp);
            }

            repaint();
            revalidate();
        }



        private void setCardSelected(Cards c){
            Component[] cardPanels = cardsPanel.getComponents();
            for(int i = 0; i < cardPanels.length; i++){
                CardPanel cp = (CardPanel) cardPanels[i];
                if(cp.getCardHolding() == c){
                    if(!cp.getSelected()) cp.setSelected(true);
                }
                else{
                    if(cp.getSelected()) cp.setSelected(false);
                }
            }
        }

        private Cards getCardSelected(){
            Component[] cardPanels = cardsPanel.getComponents();
            for(int i = 0; i < cardPanels.length; i++){
                CardPanel cp = (CardPanel) cardPanels[i];
                if(cp.getSelected()) return cp.cardHolding;
            }

            return null;
        }


        @Override
        protected void paintComponent(Graphics g){
            //Initialize (calculate) vertical and horizontalWoodTiles if they haven't been yet
            if(verticalWoodTiles == 0 || horizontalWoodTiles == 0){
                verticalWoodTiles = (getSize().height / woodTile.getHeight(null)) + 1;
                horizontalWoodTiles = (getSize().width / woodTile.getWidth(null)) + 1;
            }

            //Draw all necessary woodTiles
            for(int i = 0; i < horizontalWoodTiles; i++){
                for(int j = 0; j < verticalWoodTiles; j++) {
                    g.drawImage(woodTile, woodTile.getWidth(null) * i, woodTile.getHeight(null) * j, null);
                }
            }

            super.paintComponent(g);
        }


        /**
         * A JPanel inside the {@link SouthPanel} that holds a single Card the user can throw.
         */
        public class CardPanel extends JPanel{

            private Cards cardHolding;
            private boolean selected;

            private Image cardImage;
            private final int CARD_HEIGHT;
            private final int CARD_HEIGHT_SELECTED;


            private CardPanel(Cards c, int cardHeight, int cardHeightSelected){
                cardHolding = c;
                CARD_HEIGHT = cardHeight;
                CARD_HEIGHT_SELECTED = cardHeightSelected;

                setLayout(new BorderLayout());
                setOpaque(false);

                cardImage = MenuGraphics.scaleImage(BattleGraphics.getCardSprite(c), cardHeight);
                add(new RoyaleLabel(new ImageIcon(cardImage)), BorderLayout.CENTER);
            }


            private void setSelected(boolean selected){
                this.selected = selected;

                if(selected) cardImage = MenuGraphics.scaleImage(BattleGraphics.getCardSprite(cardHolding), CARD_HEIGHT_SELECTED);
                else cardImage = MenuGraphics.scaleImage(BattleGraphics.getCardSprite(cardHolding), CARD_HEIGHT);

                removeAll();
                add(new RoyaleLabel(new ImageIcon(cardImage)), BorderLayout.CENTER);

                repaint();
                revalidate();
            }

            private boolean getSelected(){ return selected; }

            /**
             * Returns the {@link Cards} that this JPanel is holding
             * @return {@link Cards} associated to this JPanel
             */
            public Cards getCardHolding(){
                return cardHolding;
            }
        }


        private class GoldProgressBar extends JPanel{

            private final int BAR_HEIGHT, BAR_WIDTH;
            private final int PANEL_HEIGHT, PANEL_WIDTH;

            private final int PROGRESS_BAR_Y_POS;

            private final Color barBackground = new Color(0 , 0, 0, 100);
            private final Color barForeground = new Color(255, 203, 0);

            private Font goldFont;
            private final int FONT_WIDTH, FONT_HEIGHT;

            private GoldProgressBar(int panelHeight, int panelWidth, int barHeight){
                PANEL_WIDTH = panelWidth;
                PANEL_HEIGHT = panelHeight;

                goldFont = MenuGraphics.getMainFont();
                FONT_WIDTH = getFontMetrics(goldFont).getWidths()[0] * 3;
                FONT_HEIGHT = getFontMetrics(goldFont).getHeight();

                BAR_HEIGHT = barHeight;
                BAR_WIDTH = PANEL_WIDTH - FONT_WIDTH;

                setOpaque(false);
                setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
                setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
                setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

                PROGRESS_BAR_Y_POS = (PANEL_HEIGHT/2) - (BAR_HEIGHT/2);
            }

            @Override
            protected void paintComponent(Graphics g) {
                double currentGold = battleController.getPlayerCurrentGold(); //CurrentGold can range from 0 to 100

                g.setColor(barBackground);
                g.fillRoundRect(0, PROGRESS_BAR_Y_POS, BAR_WIDTH, BAR_HEIGHT, 20, 20);

                g.setColor(barForeground);
                g.fillRoundRect(0, PROGRESS_BAR_Y_POS, (int)(currentGold*((double)BAR_WIDTH/100)), BAR_HEIGHT, 20, 20);

                g.setFont(goldFont);
                g.drawString(Integer.toString((int)(currentGold/10)), BAR_WIDTH + FONT_WIDTH/3, PROGRESS_BAR_Y_POS + FONT_HEIGHT/2);
                super.paintComponent(g);
            }
        }

    }


}

package presentation.view;

import business.entities.Cards;
import presentation.controller.BattleController;
import presentation.graphics.BattleGraphics;
import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleGoldProgressBar;
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
     * Returns a {@link JPanel} that represents the BattlePanel inside this {@code BattleScreen}
     * @return A {@link JPanel} that represents the BattlePanel inside this {@code BattleScreen}
     */
    public JPanel getBattlePanel(){ return battlePanel;}

    /**
     * Notifies the Screen that the Player cards shown need to be updated
     */
    public void updateCardsToShow (){ southPanel.updateCardsToShow();}


    public void updateGoldProgressBar(int value){ southPanel.updateGoldProgressBar(value);}


    /**
     * Returns the current card selected by the player
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //TODO: Drawable[] drawables = battleController.getDrawables();
            battleController.paintBattle(g);
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
        private JPanel goldPanel;

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


            goldPanel = new JPanel();
            goldPanel.setLayout(new BoxLayout(goldPanel, BoxLayout.X_AXIS));
            goldPanel.setOpaque(false);

            RoyaleLabel goldText = new RoyaleLabel("10", RoyaleLabel.LabelType.PARAGRAPH);
            goldText.setForeground(Color.YELLOW);
            FontMetrics goldTextFontMetrics = goldText.getFontMetrics(goldText.getFont());
            RoyaleGoldProgressBar goldProgressBar = new RoyaleGoldProgressBar(goldTextFontMetrics.getHeight());
            goldPanel.add(goldProgressBar);
            goldPanel.add(goldText);


            centerPanel.add(cardsPanel);
            centerPanel.add(goldPanel);
            add(centerPanel, new GridBagConstraints());
        }


        private void updateCardsToShow(){
            cardsPanel.removeAll();
            cardsToShow = battleController.getCardsToShow();

            for(Cards c: cardsToShow){
                CardPanel cp = new CardPanel(c, getPreferredSize().height * 65/100);
                cp.addMouseListener(battleController);
                cardsPanel.add(cp);
            }

            repaint();
            revalidate();
        }


        private void updateGoldProgressBar(int value){

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


            private CardPanel(Cards c, int cardHeight){
                cardHolding = c;
                CARD_HEIGHT = cardHeight;

                setLayout(new BorderLayout());
                setOpaque(false);

                cardImage = MenuGraphics.scaleImage(BattleGraphics.getCardSprite(c), cardHeight);
                add(new RoyaleLabel(new ImageIcon(cardImage)), BorderLayout.CENTER);
            }


            private void setSelected(boolean selected){
                this.selected = selected;

                if(selected) cardImage = MenuGraphics.scaleImage(BattleGraphics.getCardSprite(cardHolding), CARD_HEIGHT + CARD_HEIGHT*20/100);
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
    }


}

package presentation.view;

import business.entities.Cards;
import presentation.controller.BattleController;
import presentation.graphics.BattleGraphics;
import presentation.graphics.MenuGraphics;
import presentation.view.customcomponents.RoyaleGoldProgressBar;
import presentation.view.customcomponents.RoyaleLabel;
import presentation.view.customcomponents.RoyaleProgressBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class BattleScreen extends JPanel {

    private BattleController battleController;

    private BattlePanel battlePanel;
    private SouthPanel southPanel;

    private final int SCREEN_HEIGHT;


    public BattleScreen(BattleController battleController, int screenHeight){
        SCREEN_HEIGHT = screenHeight;
        this.battleController = battleController;

        setLayout(new BorderLayout());

        battlePanel = new BattlePanel();
        battlePanel.addMouseListener(battleController);
        southPanel = new SouthPanel();

        add(battlePanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }



    public JPanel getBattlePanel(){ return battlePanel;}

    public void updateCardsToShow (){ southPanel.updateCardsToShow();}

    public void updateGoldProgressBar(int value){ southPanel.updateGoldProgressBar(value);}

    public Cards getCardSelected(){ return southPanel.getCardSelected(); }

    public void setCardSelected(Cards c){ southPanel.setCardSelected(c); }


    public Dimension getBattlePanelSize(){
        return new Dimension(getSize().width, getSize().height - southPanel.getSize().height);
    }



    public class BattlePanel extends JPanel{

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            battleController.paintBattle(g);
        }

    }



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


        public void updateCardsToShow(){
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

            public Cards getCardHolding(){
                return cardHolding;
            }



        }

    }


}

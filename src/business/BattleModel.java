package business;

import business.entities.Cards;
import business.entities.Player;
import presentation.controller.BattleController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BattleModel {

    private BattleController battleController; //The battleController that controls this battleModel

    private ArrayList<Card> playerCards;
    private ArrayList<Card> enemyCards;
    private Map map;

    private PhysicsSystem physicsSystem;


    private Player player;
    private int playerGold; //From 0 to 10
    private ArrayList<Cards> playerCurrentCardsToThrow; //There will only be 4 cards to throw at a time
    private final int PLAYER_CURRENT_CARDS = 4;


    public BattleModel(BattleController battleController, Player player, Dimension battlePanelDimension){
        this.battleController = battleController;
        this.player = player;

        map = new Map(battlePanelDimension);
        enemyCards = new ArrayList<>();
        playerCards = new ArrayList<>();
        playerGold = 100;

        playerCurrentCardsToThrow = new ArrayList<>();
        for(int i = 0; i < PLAYER_CURRENT_CARDS; i++) generateNewCardToThrow();
    }



    public void update(){
        for(Card c: playerCards) c.update();
        for(Card c: enemyCards) c.update();
    }


    public void draw(Graphics g){
        map.draw(g);
        for(Card c: playerCards) c.draw(g);
        for(Card c: enemyCards) c.draw(g);
    }


    /**
     * To be called whenever the player wants to throw (invoke) a Card to the map
     * @param c The Card the player is throwing
     * @param xPos The initial X position of the Card
     * @param yPos The initial Y position of the Card
     *
     * @return Boolean telling whether the Card has been thrown or not (if not, it means
     * the x and y pos were not inside the map or the player had no enough gold)
     */
    public boolean playerThrowCard(Cards c, int xPos, int yPos){
        Vector2 cardPosition = new Vector2(xPos, yPos);

        //If the position introduced is outside the map, return false
        if(!map.isPositionInsideTheMap(cardPosition)) return false;

        //If the player has no enough gold to invoke that card, return false
        if(playerGold < c.getGoldCost()) return false;


        //If the position is correct and the player has enough gold, let's invoke a new Card and update playerCurrentCardsToThrow
        playerCards.add(new Card(c, player.getPlayerCards().get(c), cardPosition, map.getCardHeight()));
        playerGold -= c.getGoldCost();

        playerCurrentCardsToThrow.remove(c);
        generateNewCardToThrow();
        battleController.gameStatsChanged(playerCards.size(), enemyCards.size());
        return true;
    }



    /**
     * When called, it will add 1 gold to the player's gold
     * <p>The {@link presentation.controller.BattleController} increases the gold of the player through a visible
     * loading bar, and it will call this method when necessary.
     */
    public void addPlayerGold(){
        if(playerGold >= 10) return;
        playerGold++;
    }


    /**
     * Returns the current gold of the player
     * @return current gold of the player
     */
    public int getCurrentGold(){
        return playerGold;
    }


    public ArrayList<Cards> getPlayerCurrentCardsToThrow(){
        return playerCurrentCardsToThrow;
    }


    /**
     * Generates a new {@link Cards} that is not yet inside {@link #playerCurrentCardsToThrow} and puts it inside
     * {@link #playerCurrentCardsToThrow}
     */
    private void generateNewCardToThrow(){
        Cards newCard;
        Random r = new Random();
        do{
            newCard = Cards.values()[r.nextInt(Cards.values().length)];
        }while(playerCurrentCardsToThrow.contains(newCard));

        playerCurrentCardsToThrow.add(newCard);
        System.out.println(newCard.toString());
    }


}

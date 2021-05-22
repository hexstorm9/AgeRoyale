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
    private ArrayList<Cards> playerCurrentCardsToThrow; //There will only be 4 cards to throw at a time
    private final int PLAYER_CURRENT_CARDS = 4;

    private double playerGold; //From 0 to 100
    private double enemyGold; //From 0 to 100
    private final double amountOfGoldEveryUpdate; //Tells the amount of gold that will be increased in every update
    private final double amountOfGoldEveryEnemyKilled; //Tells the amount of gold that will be increased in every enemy killed
    private static final int GOLD_ON_START = 50; //The amount of gold both player and enemy will have on start

    /**
     * It will save how many updates have already happened (an updates counter)
     */
    private int updatesCounter;


    /**
     * In a normal battle, {@code battleMovements} will be used as an array to save all the movements done.
     * When reproducing a battle, {@code battleMovements} will be used as the array to read all the movements from the old battle
     */
    private ArrayList<Movement> battleMovements;


    /**
     * Default BattleModel Constructor.
     * @param battleController
     * @param player
     * @param battlePanelDimension
     */
    public BattleModel(BattleController battleController, Player player, Dimension battlePanelDimension){
        this.battleController = battleController;
        this.player = player;

        map = new Map(battlePanelDimension);
        enemyCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        physicsSystem = new PhysicsSystem(this, map);
        updatesCounter = 0;
        battleMovements = new ArrayList<>(); //Create the BattleMovements arraylist so as to add them when they happen

        playerCurrentCardsToThrow = new ArrayList<>();
        for(int i = 0; i < PLAYER_CURRENT_CARDS; i++) generateNewCardToThrow();


        //Let's create both towers
        playerCards.add(new Card(Cards.TOWER, 1, Card.Status.PLAYER, map.getTowerPosition(Card.Status.PLAYER),
                map.getTowerHeight(), physicsSystem));
        enemyCards.add(new Card(Cards.TOWER, 1, Card.Status.ENEMY, map.getTowerPosition(Card.Status.ENEMY),
                map.getTowerHeight(), physicsSystem));


        //Update Game Stats
        battleController.gameStatsChanged(playerCards.size(), enemyCards.size());


        //Define Gold
        playerGold = GOLD_ON_START;
        enemyGold = GOLD_ON_START;
        amountOfGoldEveryUpdate = 0.08;
        amountOfGoldEveryEnemyKilled = 5;
    }



    public void update(){
        updatesCounter++;

        //Update all Cards and check whether some card is totally dead or not.
        for(int i = 0; i < playerCards.size(); i++){
            if(!playerCards.get(i).isTotallyDead()) playerCards.get(i).update();
            else{
                //If that player card that just died is the tower, let's end the game (the enemy won)
                if(playerCards.get(i).getCardType().isTower()){
                    endGame(Card.Status.ENEMY);
                    return;
                }
                playerCards.remove(playerCards.get(i));
                addGold(Card.Status.ENEMY, amountOfGoldEveryEnemyKilled); //For every player death, add gold to the enemy
                battleController.gameStatsChanged(playerCards.size(), enemyCards.size());
            }
        }
        for(int i = 0; i < enemyCards.size(); i++){
            if(!enemyCards.get(i).isTotallyDead()) enemyCards.get(i).update();
            else{
                //If that enemy card that just died is the tower, let's end the game (the player won)
                if(enemyCards.get(i).getCardType().isTower()){
                    endGame(Card.Status.PLAYER);
                    return;
                }
                enemyCards.remove(enemyCards.get(i));
                addGold(Card.Status.PLAYER, amountOfGoldEveryEnemyKilled); //For every enemy death, add gold to the player
                battleController.gameStatsChanged(playerCards.size(), enemyCards.size());
            }
        }

        //Update Player and Enemy gold
        addGold(Card.Status.PLAYER, amountOfGoldEveryUpdate);
        addGold(Card.Status.ENEMY, amountOfGoldEveryUpdate);
    }


    /**
     * Updates the gold either of the enemy or the player
     * @param status Tells to which player (enemy or player) the gold should be updated
     * @param amount The amount of gold to increase
     */
    public void addGold(Card.Status status, double amount){
        if(status == Card.Status.PLAYER){
            playerGold = playerGold + amount > 100? 100: playerGold + amount;
        }
        else if(status == Card.Status.ENEMY){
            enemyGold = enemyGold + amount > 100? 100: enemyGold + amount;
        }
    }


    public void draw(Graphics g){
        map.draw(g);
        for(Card c: playerCards) c.draw(g);
        for(Card c: enemyCards) c.draw(g);
    }


    /**
     * Ends the Battle completely.
     * <p>To be called whenever the Tower has died.
     *
     * @param winner The winner of the game (either the Player or the Enemy)
     */
    private void endGame(Card.Status winner){
        Random r = new Random();
        //The crowns a player looses/wins can be ranged from 20 to 35. If it has lost, multiply it for -1
        final int crownsOfTheBattle = (20 + r.nextInt(15)) * (winner == Card.Status.PLAYER? 1: -1);

        battleController.endGame(crownsOfTheBattle);

        //Create a new thread to update the Player information (so as not to block this gameLoop thread)
        Thread updatePlayerThread = new Thread(() ->
                player.updateAfterBattlePlayed(crownsOfTheBattle, winner == Card.Status.PLAYER));
        updatePlayerThread.start();
    }


    /**
     * To be called whenever the player or the bot want to throw (invoke) a Card to the map
     * @param c The Card to be thrown
     * @param cardStatus The state of the card
     * @param xPos The initial X position of the Card
     * @param yPos The initial Y position of the Card
     *
     * @return Boolean telling whether the Card has been thrown or not (if not, it means
     * the x and y pos were not inside the map or the player/enemy had no enough gold)
     */
    public synchronized boolean throwCard(Cards c, Card.Status cardStatus, int xPos, int yPos){
        Vector2 cardPosition = new Vector2(xPos, yPos);

        if(cardStatus == Card.Status.PLAYER){
            //If the position introduced is outside the map, return false
            if(!map.canCardBeThrownToTheLeftMap(cardPosition)) return false;

            //If the player has no enough gold to invoke that card, return false
            if(playerGold < c.getGoldCost()) return false;


            //If the position is correct and the player has enough gold, let's invoke a new Card and update playerCurrentCardsToThrow
            playerCards.add(new Card(c, player.getPlayerCards().get(c), Card.Status.PLAYER, cardPosition, map.getCardHeight(), physicsSystem));
            playerGold -= c.getGoldCost();

            playerCurrentCardsToThrow.remove(c);
            generateNewCardToThrow();
        }
        else if(cardStatus == Card.Status.ENEMY){
            //If the position introduced is outside the map, return false
            if(!map.canCardBeThrownToTheRightMap(cardPosition)) return false;

            //If the enemy has no enough gold to invoke that card, return false
            if(enemyGold < c.getGoldCost()) return false;

            enemyCards.add(new Card(c, 1, Card.Status.ENEMY, cardPosition, map.getCardHeight(), physicsSystem));
            enemyGold -= c.getGoldCost();
        }

        //Let's create a new Movement and save it to the array of movements
        Movement cardThrownMovement = new Movement(c, cardStatus, cardPosition, updatesCounter);
        battleMovements.add(cardThrownMovement);

        battleController.gameStatsChanged(playerCards.size(), enemyCards.size());
        return true;
    }




    /**
     * Returns a {@code Double} from 0 to 100 indicating the current Gold that the Player Has
     * @return {@code Double} from 0 to 100 indicating the current Gold that the Player Has
     */
    public double getPlayerCurrentGold(){
        return playerGold;
    }


    /**
     * Returns a {@code Double} from 0 to 100 indicating the current Gold that the enemy has
     * @return {@code Double} from 0 to 100 indicating the current Gold that the enemy has
     */
    public double getEnemyCurrentGold() {
        return enemyGold;
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
        }while(playerCurrentCardsToThrow.contains(newCard) || newCard.isTower());

        playerCurrentCardsToThrow.add(newCard);
    }


    public ArrayList<Card> getPlayerCards(){ return playerCards;}
    public ArrayList<Card> getEnemyCards(){ return enemyCards;}


    /**
     * Returns whether there is some player on the Enemy Map or not.
     * @return Whether there is some player on the enemy Map or not.
     */
    public boolean isPlayerOnEnemyMap() {
        for(Card c: playerCards)
            if(map.isPositionOnTheRightMap(c.position)) return true;

        return false;
    }


    /**
     * Returns the array of {@link Movement} representing what happened during the battle.
     * <p>To be called only when the battle has ended
     * @return The array of {@link Movement} representing what happened during this battle.
     */
    public Movement[] getBattleMovements(){
        Movement[] movements = new Movement[battleMovements.size()];
        movements = battleMovements.toArray(movements);

        return movements;
    }

}

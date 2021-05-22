package business;

import business.entities.Cards;

/**
 * A {@code Movement} object will save a battle movement from either the player or the bot, by saving what card was thrown,
 * where and at what time.
 *
 * @version 1.0
 */
public class Movement implements Comparable{

    private Card.Status playerOrEnemy;
    private Vector2 cardPosition;
    private Cards cardThrown;
    private int updateThrown; //The update of the game the user/bot threw the Card (the time it threw it)

    /**
     * Default Movement Constructor.
     *
     * @param cardThrown The Card that was thrown
     * @param playerOrEnemy Whether that Card was thrown by the Player or the Enemy
     * @param cardPosition The position where that Card was thrown
     * @param updateThrown The update of the game when the Card was thrown
     */
    public Movement(Cards cardThrown, Card.Status playerOrEnemy, Vector2 cardPosition, int updateThrown){
        this.cardThrown = cardThrown;
        this.playerOrEnemy = playerOrEnemy;
        this.cardPosition = cardPosition;
        this.updateThrown = updateThrown;
    }


    /**
     * Returns the Card that the Movement holds
     * @return The Card that the Movement holds
     */
    public Cards getCardThrown(){
        return cardThrown;
    }


    /**
     * Returns whether that movement belongs to the Player or the Enemy
     * @return Whether that movement belongs to the Player or the Enemy
     */
    public Card.Status getPlayerOrEnemy() {
        return playerOrEnemy;
    }

    /**
     * Returns the position where that Card was thrown
     * @return The position where that Card was thrown
     */
    public Vector2 getCardPosition() {
        return cardPosition;
    }

    /**
     * Returns the game update at which that Card was thrown
     * @return The game update at which that Card was thrown
     */
    public int getUpdateThrown() {
        return updateThrown;
    }


    /**
     * Compares this Movement with the provided Movement for order.
     * <p>Returns a negative integer, zero, or a positive integer depending whether this Movement was done before, at the same time, or after the provided Movement
     * @param o The Object to compare it to. If it's not another Movement, the method will return 0
     * @return A negative integer, zero, or a positive integer depending whether this Movement was done before, at the same time, or after the provided Movement
     */
    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Movement)) return 0;

        Movement movementProvided = (Movement) o;
        if(movementProvided.getUpdateThrown() > updateThrown) return -1;
        else if(movementProvided.getUpdateThrown() < updateThrown) return 1;
        else return 0;
    }

}

package business;

import business.entities.Cards;
import presentation.graphics.BattleGraphics;

import java.awt.*;
import java.util.HashMap;


/**
 * The {@code abstract} Card class defines how a Card will be in the game.
 * <p>It declares its main, shared attributes and provides the two most important methods in a card,
 * {@link #update()} and {@link #draw(Graphics)}
 *
 * @version 1.0
 */
public class Card {

    public enum State{
        /**
         * The {@code Card} won't move, but it will be constantly checking for enemies in range (to start attacking them)
         */
        IDLE,

        /**
         * The {@code Card} will start attacking another Card until the health of the enemy is 0 (dead). Then, it will return to the
         * {@link #initialState}
         */
        ATTACKING,

        /**
         * The {@code Card} will move to the next spot, constantly checking for enemies in range (to start attacking them)
         */
        MOVING
    }

    /**
     * The initial state of a Card will be the "default" state of it.
     * <p>When the attacking state ends, the card will automatically return to this initial state.
     *
     * <p>That means that the initial state defines the card behaviour. If it is {@code MOVING}, the card will start moving when instantiated
     * and continue moving when it stops attacking. If it is {@code IDLE}, the card will start idling when instantiated and continue idling
     * when it stops attacking.
     */
    protected State initialState;
    private State currentState;


    private HashMap<Orientation, Image[]> idleSprites;
    private final int updatesToNextIdleSprite;

    private HashMap<Orientation, Image[]> movingSprites;
    private final int updatesToNextMovingSprite;

    private HashMap<Orientation, Image[]> attackingSprites;
    private final int updatesToNextAttackingSprite;

    private int updatesWithCurrentSprite; //To count when we'll need to change to the next sprite from the Image[] array
    private int currentSpriteIndex; //Current index of the Image[] array


    public enum Orientation{
        RIGHT,
        LEFT
    }
    private Orientation currentOrientation;


    protected final int CARD_HEIGHT;

    protected int goldCost;
    protected Vector2 position;
    protected int health;
    protected int damage;
    protected int range;
    protected int attackingVelocity;
    protected int movingVelocity;

    protected Image currentSprite; //The sprite that will be drawn on the draw() method


    private Card currentlyAttackingCard;



    public Card(Cards cardType, int level, Vector2 initialPosition, final int CARD_HEIGHT){
        this.CARD_HEIGHT = CARD_HEIGHT;
        position = initialPosition;
        initializeAttributes(cardType, level);

        changeState(initialState);
        currentOrientation = Orientation.RIGHT;

        updatesToNextIdleSprite = movingVelocity;
        updatesToNextMovingSprite = movingVelocity;
        updatesToNextAttackingSprite = attackingVelocity;
    }


    /**
     * The logic of the Card.
     * <p>This will be called every x time, and will make the card attributes update and carry out actions
     * (i.e. see if it can attack another card, update its position, etc.)
     */
    public final void update(){
        if(currentState == State.IDLE){
            //If the card is idling, it will only check for enemies in range (and switch to the ATTACKING state if an enemy is in range)

            //Changing CurrentSprite
            if(updatesWithCurrentSprite >= updatesToNextIdleSprite){
                if(currentSpriteIndex >= idleSprites.get(currentOrientation).length) currentSpriteIndex = 0; //If we're in the last movingSprite, start again
                currentSprite = idleSprites.get(currentOrientation)[currentSpriteIndex]; //Assign new sprite to the currentSprite
                currentSpriteIndex++;
                updatesWithCurrentSprite = 0; //Reset updatesWithCurrentSprite
            }
            else updatesWithCurrentSprite++;

        }
        else if(currentState == State.MOVING){
            //If the card is moving, it will move and check for enemies in range (and switch to the ATTACKING state if an enemy is in range)

            //Changing CurrentSprite
            if(updatesWithCurrentSprite >= updatesToNextMovingSprite){
                if(currentSpriteIndex >= movingSprites.get(currentOrientation).length) currentSpriteIndex = 0;
                currentSprite = movingSprites.get(currentOrientation)[currentSpriteIndex]; //Add 1 to currentMovingSprite, and assign it to the currentSprite
                currentSpriteIndex++;
                updatesWithCurrentSprite = 0; //Reset updatesWithCurrentMovingSprite
            }
            else updatesWithCurrentSprite++;

        }
        else if(currentState == State.ATTACKING){
            //If the card is attacking, it will attack its enemy until it is dead (enemy card life == 0) and return to the initialState

            //Changing CurrentSprite
            if(updatesWithCurrentSprite >= updatesToNextAttackingSprite){
                if(currentSpriteIndex >= attackingSprites.get(currentOrientation).length) currentSpriteIndex = 0; //If we're in the last attackingSprite, start again
                currentSprite = attackingSprites.get(currentOrientation)[currentSpriteIndex]; //Add 1 to currentAttackingSprite, and assign it to the currentSprite
                currentSpriteIndex++;
                updatesWithCurrentSprite = 0; //Reset updatesWithCurrentAttackingSprite
            }

        }
        else updatesWithCurrentSprite++;
    }


    /**
     * The drawing of the Card.
     * <p>Every card should draw itself, and we'll draw ourselves in a {@link Graphics} object provided.
     * @param g Graphics object in which we'll draw {@code this}.
     */
    public final void draw(Graphics g){
        g.drawImage(currentSprite, position.x, position.y, null);
    }


    /**
     * When creating a new Card, attributes need to be initialized for that Card:
     * <ul>
     *     <li>Health</li>
     *     <li>Damage</li>
     *     <li>Range</li>
     *     <li>Attacking Velocity</li>
     *     <li>Moving Velocity</li>
     *     <li>Gold Cost</li>
     *     <li>Initial State</li>
     *     <li>Load Sprites (depending on the states that the Card uses)</li>
     * </ul>
     *
     * <p>Depending on the level of the Card, some attributes should be modified
     *
     * <p>Note that if some attribute listed are not initialized, they'll start with the default value 0.
     * In the case of objects, a {@link NullPointerException} will be thrown when accessing them.
     */
    private void initializeAttributes(Cards cardType, int level){
        goldCost = cardType.getGoldCost();

        health = cardType.getMaxHealth(level);
        damage = cardType.getDamage(level);
        range = cardType.getRange();
        attackingVelocity = cardType.getAttackingVelocity();
        movingVelocity = cardType.getMovingVelocity();
        if(movingVelocity == 0) initialState = State.IDLE;
        else initialState = State.MOVING;

        idleSprites = BattleGraphics.getSprites(cardType, State.IDLE, CARD_HEIGHT);
        movingSprites = BattleGraphics.getSprites(cardType, State.MOVING, CARD_HEIGHT);
        attackingSprites = BattleGraphics.getSprites(cardType, State.ATTACKING, CARD_HEIGHT);
    }


    /**
     * Setter to change the {@code state} of the Card
     * @param newState New State Desired
     */
    private final void changeState(State newState){
        currentState = newState;
        updatesWithCurrentSprite = 0;
        currentSpriteIndex = 0;
    }

    public void attack(){

    }


    public int getGoldCost(){ return goldCost; }


    public int getCurrentHealth(){ return health > 0 ? health: 0;}

}

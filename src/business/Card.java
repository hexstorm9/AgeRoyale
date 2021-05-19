package business;

import business.entities.Cards;
import presentation.graphics.BattleGraphics;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;


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
        WALKING,

        /**
         * Once the card's life reaches 0, it will go to the state of DYING
         * (it can't be attacked by other cards, and the dying Card won't do anything,
         * only reproduce the dying effect)
         */
        DYING;

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
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

    private HashMap<Orientation, Image[]> dyingSprites;
    private final int updatesToNextDyingSprite;


    private int updatesWithCurrentSprite; //To count when we'll need to change to the next sprite from the Image[] array
    private int currentSpriteIndex; //Current index of the Image[] array


    private int updatesWalking; //Counter for walking updates
    private int updatesAttacking; //Counter for attacking updates


    public enum Orientation{
        RIGHT,
        LEFT
    }
    private Orientation currentOrientation;


    public enum Status{
        PLAYER,
        ENEMY
    }
    private Status cardStatus;


    protected final int CARD_HEIGHT;

    protected int goldCost;
    protected Vector2 position; //The position of a Card will be its bottom-left point's position (not its upper-left).
    protected int health;
    protected int damage;
    protected int range;
    protected int attackingVelocity;
    protected int movingVelocity;
    private boolean totallyDead; //Once the health of a Card reaches 0, it will enter the DYING state and
                                 //reproduce the dying animation. When the animation is ended, totallyDead will become true


    protected Image currentSprite; //The sprite that will be drawn on the draw() method
    private PhysicsSystem physicsSystem; //The physics system that the card will use to move, find enemies, etc.

    private Card currentlyAttackingCard;



    public Card(Cards cardType, int level, Status cardStatus, Vector2 initialPosition, final int CARD_HEIGHT,
                PhysicsSystem physicsSystem){
        this.CARD_HEIGHT = CARD_HEIGHT;
        position = initialPosition;
        this.cardStatus = cardStatus;
        this.physicsSystem = physicsSystem;
        initializeAttributes(cardType, level);

        currentOrientation = cardStatus == Status.PLAYER ? Orientation.RIGHT: Orientation.LEFT;
        changeState(initialState);
        totallyDead = false;

        updatesToNextIdleSprite = attackingVelocity / 2;
        updatesToNextMovingSprite = movingVelocity;
        updatesToNextAttackingSprite = attackingVelocity * 2;
        updatesToNextDyingSprite = attackingVelocity / 4;
    }


    /**
     * The logic of the Card.
     * <p>This will be called every x time, and will make the card attributes update and carry out actions
     * (i.e. see if it can attack another card, update its position, etc.)
     */
    public final void update(){

        //Check if our health is bigger than 0. If not, we're dead and we'll start the DYING state
        if(!isAlive() && currentState != State.DYING && currentState != null){
            changeState(State.DYING);
            return;
        }


        if(currentState == State.IDLE){
            //If the card is idling, it will only check for enemies in range (and switch to the ATTACKING state if an enemy is in range)
            if((currentlyAttackingCard = physicsSystem.getEnemyInRange(this, position, range)) != null){
                changeState(State.ATTACKING);
                return;
            }

            //Changing CurrentSprite
            if(updatesWithCurrentSprite >= updatesToNextIdleSprite){
                if(currentSpriteIndex >= idleSprites.get(currentOrientation).length) currentSpriteIndex = 0; //If we're in the last movingSprite, start again
                currentSprite = idleSprites.get(currentOrientation)[currentSpriteIndex]; //Assign new sprite to the currentSprite
                currentSpriteIndex++;
                updatesWithCurrentSprite = 0; //Reset updatesWithCurrentSprite
            }
            else updatesWithCurrentSprite++;

        }
        else if(currentState == State.WALKING){
            //If the card is moving, it will move and check for enemies in range (and switch to the ATTACKING state if an enemy is in range)
            if((currentlyAttackingCard = physicsSystem.getEnemyInRange(this, position, range)) != null){
                changeState(State.ATTACKING);
                return;
            }

            if(updatesWalking >= 1/movingVelocity){
                position = physicsSystem.move(this, position);
                updatesWalking = 0;
            }
            else updatesWalking++;

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
            if(updatesAttacking >= (1/(double)attackingVelocity)*200){
                //Let's attack. Once the attack() returns false (meaning the enemy is now dead) let's change state again
                if(attack() == false){
                    changeState(initialState);
                    return;
                }
                updatesAttacking = 0;
            }
            else updatesAttacking++;

            //Changing CurrentSprite
            if(updatesWithCurrentSprite >= updatesToNextAttackingSprite){
                if(currentSpriteIndex >= attackingSprites.get(currentOrientation).length) currentSpriteIndex = 0; //If we're in the last attackingSprite, start again
                currentSprite = attackingSprites.get(currentOrientation)[currentSpriteIndex]; //Add 1 to currentAttackingSprite, and assign it to the currentSprite
                currentSpriteIndex++;
                updatesWithCurrentSprite = 0; //Reset updatesWithCurrentAttackingSprite
            }
            else updatesWithCurrentSprite++;
        }
        else if(currentState == State.DYING){
            //If the card is dying, it won't do anything (only reproduce the dying animation). Once the animation
            //is finished, the Card will enable its totallyDied boolean.

            //Changing CurrentSprite
            if(updatesWithCurrentSprite >= updatesToNextDyingSprite){
                //If we've already shown all dying sprites of the array, let's end DYING
                if(currentSpriteIndex >= dyingSprites.get(currentOrientation).length){
                    changeState(null); //We'll enter a null state (do nothing until the BattleModel kills us)
                    totallyDead = true;
                    return;
                }
                currentSprite = dyingSprites.get(currentOrientation)[currentSpriteIndex]; //Add 1 to currentAttackingSprite, and assign it to the currentSprite
                currentSpriteIndex++;
                updatesWithCurrentSprite = 0; //Reset updatesWithCurrentAttackingSprite
            }
            else updatesWithCurrentSprite++;
        }

    }


    /**
     * The drawing of the Card.
     * <p>Every card should draw itself, and we'll draw ourselves in a {@link Graphics} object provided.
     * @param g Graphics object in which we'll draw {@code this}.
     */
    public final void draw(Graphics g){
        //As the position of the card is its bottom-left corner, to obtain the Y of the card we'll subtract CARD_HEIGHT
        g.drawImage(currentSprite, (int)position.x, (int)position.y - CARD_HEIGHT, null);
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
        else initialState = State.WALKING;

        idleSprites = BattleGraphics.getSprites(cardType, State.IDLE, CARD_HEIGHT);
        movingSprites = BattleGraphics.getSprites(cardType, State.WALKING, CARD_HEIGHT);
        attackingSprites = BattleGraphics.getSprites(cardType, State.ATTACKING, CARD_HEIGHT);
        dyingSprites = BattleGraphics.getSprites(cardType, State.DYING, CARD_HEIGHT);
    }


    /**
     * Setter to change the {@code state} of the Card
     * @param newState New State Desired
     */
    private final void changeState(State newState){
        currentState = newState;
        updatesWalking = 0;
        updatesAttacking = 0;

        //Let's load the first sprite immediately, don't wait for the next update to come
        if(newState == State.IDLE) currentSprite = idleSprites.get(currentOrientation)[0];
        else if(newState == State.ATTACKING) currentSprite = attackingSprites.get(currentOrientation)[0];
        else if(newState == State.WALKING) currentSprite = movingSprites.get(currentOrientation)[0];
        else if(newState == State.DYING){
            System.out.println(cardStatus);
            System.out.println(attackingVelocity);
            currentSprite = dyingSprites.get(currentOrientation)[0];
        }

        updatesWithCurrentSprite = 0;
        currentSpriteIndex = 1;
    }


    /**
     * Uses the attribute {@link #currentlyAttackingCard} to start damaging the enemy card.
     * <p>Once the enemy card has 0 life, the method will return false (it has ended attacking)
     *
     * @return true if it's still attacking, and false if the enemy card has died (life = 0)
     */
    public boolean attack(){
        if(currentlyAttackingCard == null) return false;

        boolean enemyAlive = currentlyAttackingCard.receiveDamage(damage);
        if(!enemyAlive) currentlyAttackingCard = null;

        return enemyAlive;
    }


    public int getGoldCost(){ return goldCost; }

    /**
     * Returns whether the card is totally alive or not
     * @return If the card is alive or not
     */
    public boolean isAlive(){ return health > 0;}


    /**
     * Subtracts life from this card
     * @param damage The damage the card will take
     * @return true if this card is still alive. False if health is 0 (it has died)
     */
    public boolean receiveDamage(int damage){
        health -= damage;
        return isAlive();
    }


    public Status getStatus(){ return cardStatus;}

    public void setOrientation(Orientation orientation){
        this.currentOrientation = orientation;
    }


    /**
     * Returns whether the card is totally dead or not.
     * <p>Once a card is dead (life < 0), it changes its state to DYING. When this state ends,
     * totallyDead will become true.
     *
     * @return Whether the card is totally dead or not
     */
    public boolean isTotallyDead(){
        return totallyDead;
    }

}

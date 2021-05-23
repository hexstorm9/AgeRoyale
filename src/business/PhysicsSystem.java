package business;

/**
 * The {@code PhysicsSystem} class provides a system for the {@link Card}s in the Battle to interact
 * with its surroundings.
 * <p>It helps them attack and move (depending on the enemies there are on the map)
 *
 * <p>There should exit only one {@code PhysicsSystem} on the game, managed by the {@link BattleModel} and provided
 * to each of the {@link Card}s on the Battle.
 *
 * <p>In order for the physics system to interact with the environment, it will need references to the {@link Map}
 * of the Battle, and the {@link BattleModel} (class that manages the {@code cards} of the Battle)
 *
 *
 * @see Card
 * @see Map
 * @version 1.0
 */
public class PhysicsSystem {

    private BattleModel battleModel;
    private Map map;

    private Vector2 topLeftBridgeAccess, topRightBridgeAccess, bottomLeftBridgeAccess, bottomRightBridgeAccess;


    private final double DISTANCE_TO_MOVE_EACH_TIME; //Will depend on the resolution of the screen
    private final double RANGE_UNIT; //Tells the pixels that 1 point of range is worth. Will depend on the resolution of the screen


    /**
     * Default PhysicsSystem Constructor.
     * @param battleModel The battleModel that manages this {@link PhysicsSystem} (so as to be able to get
     *                    information about the {@code cards} that are playing right now
     * @param map The Map of the Battle (so as to be able to get information about boundaries, and to where
     *            should {@code cards} go.
     */
    public PhysicsSystem(BattleModel battleModel, Map map){
        this.battleModel = battleModel;
        this.map = map;

        Vector2[] bridgeAccesses = map.getBridgeAccesses();
        topLeftBridgeAccess = bridgeAccesses[0];
        topRightBridgeAccess = bridgeAccesses[1];
        bottomLeftBridgeAccess = bridgeAccesses[2];
        bottomRightBridgeAccess = bridgeAccesses[3];

        DISTANCE_TO_MOVE_EACH_TIME = map.getCardHeight() / 150.0;
        RANGE_UNIT = map.getCardHeight() / 2;
    }


    /**
     * Provided the position of a Card and its range, returns the closest enemy in range or {@code null}
     * if there are none.
     * @param card The Card that wants to know for enemies in range
     * @param position The position of the Card
     * @param range The range of the Card
     * @return The closest enemy {@link Card} in range, or {@code null} if none.
     */
    public Card getEnemyInRange(Card card, Vector2 position, int range){
        //Calculate the range in pixels.
        final double RANGE_IN_PIXELS = range * RANGE_UNIT;

        if(card.getStatus() == Card.Status.PLAYER){ //If a player wants to get an enemy, let's search for them
            for(Card enemyCard: battleModel.getEnemyCards()){
                //If the distance of the playerCard and the enemyCard are in range, return that enemyCard
                if(calculateDistance(position, enemyCard.position) < RANGE_IN_PIXELS){
                    card.setOrientation(position.x < enemyCard.position.x ? Card.Orientation.RIGHT: Card.Orientation.LEFT);
                    return enemyCard;
                }
            }
        }
        else if(card.getStatus() == Card.Status.ENEMY){ //If an enemy wants to get a player, let's search for them
            for(Card playerCard: battleModel.getPlayerCards()){
                //If the distance of the playerCard and the enemyCard are in range, return that enemyCard
                if(calculateDistance(position, playerCard.position) < RANGE_IN_PIXELS){
                    card.setOrientation(position.x < playerCard.position.x ? Card.Orientation.RIGHT: Card.Orientation.LEFT);
                    return playerCard;
                }
            }
        }

        //If nothing has been found in the Card's range, return null
        return null;
    }


    /**
     * Given the actual position of a card, returns the new position of the card
     *
     * @param card {@link Card} to calculate its new position
     * @param position Current position of the card
     * @return new position of the card
     */
    public Vector2 move(Card card, Vector2 position){

        if(map.isPositionOnTheLeftMap(position)){ //If the Card is on the left map

            double closestDistanceObjective = Double.MAX_VALUE;
            double bufferDistanceObjective;
            Vector2 closestObjectivePosition = new Vector2(0, 0);

            if(card.getStatus() == Card.Status.PLAYER){ //If the Card is a Player card, then we'll go to the closest bridgePoint or enemy in the left map

                //*******
                //Check if the card is within the bounds of the topLeft bridge access. If it is, move forward
                if(position.x > topLeftBridgeAccess.x && (position.y + 2 > topLeftBridgeAccess.y && position.y - 2 < topLeftBridgeAccess.y))
                    return new Vector2(position.x + DISTANCE_TO_MOVE_EACH_TIME, position.y);

                //Check if the card is within the bounds of the bottomLeft bridge access. If it is, move forward
                if(position.x > bottomLeftBridgeAccess.x && (position.y + 2 > bottomLeftBridgeAccess.y && position.y - 2 < bottomLeftBridgeAccess.y))
                    return new Vector2(position.x + DISTANCE_TO_MOVE_EACH_TIME, position.y);
                //*******


                //Let's calculate distance to enemies in this map
                for(Card enemyCard: battleModel.getEnemyCards()){
                    //If the enemy is in our map, calculate the distance between us
                    if(map.isPositionOnTheLeftMap(enemyCard.position)) {
                        //If the enemy has the closest distance to us until now, we've found a new closestObjective
                        if((bufferDistanceObjective = calculateDistance(position, enemyCard.position)) < closestDistanceObjective){
                            closestDistanceObjective = bufferDistanceObjective;
                            closestObjectivePosition = enemyCard.position;
                        }
                    }
                }

                //Let's calculate distance to top left bridge access. If it's closest than any other distance until now, let's go there
                if((bufferDistanceObjective = calculateDistance(position, topLeftBridgeAccess)) < closestDistanceObjective){
                    closestDistanceObjective = bufferDistanceObjective;
                    closestObjectivePosition = topLeftBridgeAccess;
                }

                //Let's calculate distance to bottom left bridge access. If it's closest than any other distance until now, let's go there
                if((bufferDistanceObjective = calculateDistance(position, bottomLeftBridgeAccess)) < closestDistanceObjective){
                    closestDistanceObjective = bufferDistanceObjective;
                    closestObjectivePosition = bottomLeftBridgeAccess;
                }

            }
            else if (card.getStatus() == Card.Status.ENEMY){ //If the card is an Enemy card, then we'll go to the closest player in the left map

                //Let's calculate distance to players in this map
                for(Card playerCard: battleModel.getPlayerCards()){
                    //If the enemy is in our map, calculate the distance between us
                    if(map.isPositionOnTheLeftMap(playerCard.position)) {
                        //If the enemy has the closest distance to us until now, we've found a new closestObjective
                        if((bufferDistanceObjective = calculateDistance(position, playerCard.position)) < closestDistanceObjective){
                            closestDistanceObjective = bufferDistanceObjective;
                            closestObjectivePosition = playerCard.position;
                        }
                    }
                }
            }


            if(closestDistanceObjective == Double.MAX_VALUE) return position;

            card.setOrientation(position.x < closestObjectivePosition.x ? Card.Orientation.RIGHT: Card.Orientation.LEFT);

            //Both distanceToMoveX and distanceToMoveY will have to add up to DISTANCE_TO_MOVE_EACH_TIME
            final Vector2 distanceToObjective = new Vector2(closestObjectivePosition.x - position.x, closestObjectivePosition.y - position.y);
            final Vector2 distanceToMoveNow = scaleVector2(distanceToObjective, DISTANCE_TO_MOVE_EACH_TIME);
            return new Vector2(position.x + distanceToMoveNow.x, position.y + distanceToMoveNow.y);

        }
        else if(map.isPositionOnTheRightMap(position)){ //If the Card is on the right map

            double closestDistanceObjective = Double.MAX_VALUE;
            double bufferDistanceObjective;
            Vector2 closestObjectivePosition = new Vector2(0, 0);

            if(card.getStatus() == Card.Status.PLAYER){ //If the card is a Player card, then we'll go to the closest enemy in the right map

                //Let's calculate distance to enemies in this map
                for(Card enemyCard: battleModel.getEnemyCards()){
                    //If the enemy is in our map, calculate the distance between us
                    if(map.isPositionOnTheRightMap(enemyCard.position)) {
                        //If the enemy has the closest distance to us until now, we've found a new closestObjective
                        if((bufferDistanceObjective = calculateDistance(position, enemyCard.position)) < closestDistanceObjective){
                            closestDistanceObjective = bufferDistanceObjective;
                            closestObjectivePosition = enemyCard.position;
                        }
                    }
                }
            }
            else if(card.getStatus() == Card.Status.ENEMY){ //If the card is an Enemy card, then we'll go to the closest bridgePoint or enemy in the map

                //*******
                //Check if the card is within the bounds of the bottomRight bridge access. If it is, move forward
                if(position.x < topRightBridgeAccess.x && (position.y + 2 > topRightBridgeAccess.y && position.y - 2 < topRightBridgeAccess.y))
                    return new Vector2(position.x - DISTANCE_TO_MOVE_EACH_TIME, position.y);

                //Check if the card is within the bounds of the bottomRight bridge access. If it is, move forward
                if(position.x < bottomRightBridgeAccess.x && (position.y + 2 > bottomRightBridgeAccess.y && position.y - 2 < bottomRightBridgeAccess.y))
                    return new Vector2(position.x - DISTANCE_TO_MOVE_EACH_TIME, position.y);
                //*******


                //Let's calculate distance to players in this map
                for(Card playerCard: battleModel.getPlayerCards()){
                    //If the enemy is in our map, calculate the distance between us
                    if(map.isPositionOnTheRightMap(playerCard.position)) {
                        //If the enemy has the closest distance to us until now, we've found a new closestObjective
                        if((bufferDistanceObjective = calculateDistance(position, playerCard.position)) < closestDistanceObjective){
                            closestDistanceObjective = bufferDistanceObjective;
                            closestObjectivePosition = playerCard.position;
                        }
                    }
                }

                //Let's calculate distance to top right bridge access. If it's closest than any other distance until now, let's go there
                if((bufferDistanceObjective = calculateDistance(position, topRightBridgeAccess)) < closestDistanceObjective){
                    closestDistanceObjective = bufferDistanceObjective;
                    closestObjectivePosition = topRightBridgeAccess;
                }

                //Let's calculate distance to bottom left bridge access. If it's closest than any other distance until now, let's go there
                if((bufferDistanceObjective = calculateDistance(position, bottomRightBridgeAccess)) < closestDistanceObjective){
                    closestDistanceObjective = bufferDistanceObjective;
                    closestObjectivePosition = bottomRightBridgeAccess;
                }
            }


            if(closestDistanceObjective == Double.MAX_VALUE) return position;

            card.setOrientation(position.x < closestObjectivePosition.x ? Card.Orientation.RIGHT: Card.Orientation.LEFT);

            //Both distanceToMoveX and distanceToMoveY will have to add up to DISTANCE_TO_MOVE_EACH_TIME
            final Vector2 distanceToObjective = new Vector2(closestObjectivePosition.x - position.x, closestObjectivePosition.y - position.y);
            final Vector2 distanceToMoveNow = scaleVector2(distanceToObjective, DISTANCE_TO_MOVE_EACH_TIME);
            return new Vector2(position.x + distanceToMoveNow.x, position.y + distanceToMoveNow.y);

        }
        else{ //If the Card is on the bridge, let's go to the next bridge point
            if(card.getStatus() == Card.Status.PLAYER){
                card.setOrientation(Card.Orientation.RIGHT);
                return new Vector2(position.x + DISTANCE_TO_MOVE_EACH_TIME, position.y); //If it's a Player Card, move to the right
            }
            else if(card.getStatus() == Card.Status.ENEMY){
                card.setOrientation(Card.Orientation.LEFT);
                return new Vector2(position.x - DISTANCE_TO_MOVE_EACH_TIME, position.y); //If it's an Enemy Card, move to the left
            }
        }

        //If nothing has been returned until now, let's not move
        return position;
    }



    /**
     * Calculates the distance between two points
     * @param point1 Point 1
     * @param point2 Point 2
     * @return The distance between the two points
     */
    private double calculateDistance(Vector2 point1, Vector2 point2){
        return Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
    }


    /**
     * Transforms (scales) a Vector2 given its new hypotenuse value
     * @param position The current Vector2 to be converted its hypotenuse
     * @param newHypotenuseValue Hypotenuse value of the new Vector2
     * @return New Vector2 with the same aspect ratio of the old one but scaled
     */
    private Vector2 scaleVector2(Vector2 position, double newHypotenuseValue){
        final double cathetusRatio = Math.abs(position.x/position.y);
        final boolean positionXNegative = position.x < 0;
        final boolean positionYNegative = position.y < 0;

        double newY = Math.sqrt((newHypotenuseValue*newHypotenuseValue)/(1+(cathetusRatio*cathetusRatio)));
        double newX = cathetusRatio * newY;

        if(positionXNegative) newX = -newX;
        if(positionYNegative) newY = -newY;

        return new Vector2(newX, newY);
    }


}

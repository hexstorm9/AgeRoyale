package business;

import business.entities.BattleInfo;
import business.entities.Cards;
import persistence.BattleHistoryDAO;

import java.util.Date;
import java.sql.SQLException;


/**
 * OldBattlesUtility provides a way to save and retrieve old battles.
 * <p>The battles are retrieved as {@link business.entities.BattleInfo} objects.
 *
 * <p>Internally, it serializes and deserializes the {@link Movement} of each {@link business.entities.BattleInfo} if they're needed.
 *
 * @version 1.0
 * @see business.entities.BattleInfo
 * @see Movement
 */
public class OldBattlesUtility {

    private BattleHistoryDAO battleHistoryDAO;

    /**
     * Default OldBattlesUtility constructor
     */
    public OldBattlesUtility() {
        this.battleHistoryDAO = new BattleHistoryDAO();
    }


    /**
     * Returns an array of simple {@link BattleInfo} representing the Battles that the user played.
     * @param user The user to check its battles
     * @return Array of simple {@link BattleInfo} representing the Battles that the user played
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo[] getBattlesByUser(String user) throws SQLException {
        BattleInfo[] battlesByUser = battleHistoryDAO.getBattlesByUser(user);
        return battlesByUser;
    }


    /**
     * Returns an array of simple {@link BattleInfo} representing the latest battles that were played
     * @param howMany How many battles want to be returned
     * @return Array of simple {@link BattleInfo} representing the latest Battles played
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo[] getLatestBattles(int howMany) throws SQLException {
        BattleInfo[] latestBattles = battleHistoryDAO.getLatestBattles(howMany);
        return latestBattles;
    }


    /**
     * Returns a complete {@link BattleInfo} representing the Battle with the ID specified
     * @param id The ID of the battle that wants to be returned
     * @return A complete {@link BattleInfo} representing the Battle with the ID specified
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo getCompleteBattleById(int id) throws SQLException {
        BattleInfo completeBattleById = battleHistoryDAO.getCompleteBattleById(id);

        //Let's deserialize its movements and return it
        completeBattleById.setMovements(deserializeMovements(completeBattleById.getSerializedMovements()));
        return completeBattleById;
    }


    /**
     * Provided an array of {@link Movement}, the name of the battle, the name of the player and whether the battle was won or not,
     * saves that Battle information remotely.
     * <p>Call this method from a thread that can be blocked, as the method can take some time
     *
     * @param movements The movements that occurred during the Battle
     * @param name The name of the battle. Can be {@code null}
     * @param playerName The name of the Player
     * @param won Whether the battle was won or not
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public void saveBattle(Movement[] movements, String name, String playerName, boolean won) throws SQLException {
        String movementsSerialized = serializeMovements(movements);
        battleHistoryDAO.saveBattle(movementsSerialized, name, playerName, won, new Date());
    }


    /**
     * Serializes the array of {@link Movement} provided into a {@code String} so as to be able to save them somewhere.
     * @param movements The array of movements to serialize
     * @return The movements serialized as a {@code String}
     */
    private String serializeMovements(Movement[] movements){
        /*SerializedMovements will follow the format:
        *  - A '/' will separate each movement
        *  - A '-' will separate each field of that movement
        *    and the positions fields are ordered will follow:
        *       1.PlayerOrEnemy (1 if it's player, 0 if it's enemy)
        *       2.cardPosition X
        *       3.cardPosition Y
        *       4.cardThrown (using Cards.toString())
        *       5.updateThrown
        */

        StringBuilder movementsSerialized = new StringBuilder(""); //Create a StringBuilder for the movementsSerialized and set the string to empty
        StringBuilder movement = new StringBuilder();

        for(int i = 0; i < movements.length; i++){
            movement.setLength(0); //Clear the StringBuilder so as to start adding new things
            movement.append(movements[i].getPlayerOrEnemy() == Card.Status.PLAYER ? 1: 0);
            movement.append("-"); //Next Field
            movement.append((int)movements[i].getCardPosition().x);
            movement.append("-"); //Next Field
            movement.append((int)movements[i].getCardPosition().y);
            movement.append("-"); //Next Field
            movement.append(movements[i].getCardThrown().toString());
            movement.append("-"); //Next Field
            movement.append(movements[i].getUpdateThrown());
            movement.append("/"); //Movement ended

            movementsSerialized.append(movement); //Add the serialized movement to the movementsSerialized String
        }


        //Return null if movementsSerialized is empty
        return movementsSerialized.toString().equals("")? null: movementsSerialized.toString();
    }


    /**
     * Deserializes the serialized {@code String} of Movements back into an array of {@link Movement}
     * @param movements The String of serialized movements
     * @return An array of {@link Movement} representing the serialized Movements provided
     */
    private Movement[] deserializeMovements(String movements){
        /*SerializedMovements will follow the format:
         *  - A '/' will separate each movement
         *  - A '-' will separate each field of that movement
         *    and the positions fields are ordered will follow:
         *       1.PlayerOrEnemy (1 if it's player, 0 if it's enemy)
         *       2.cardPosition X
         *       3.cardPosition Y
         *       4.cardThrown (using Cards.toString())
         *       5.updateThrown
         */

        String[] eachMovement = movements.split("/"); //Will give us each movement serialized
        Movement[] movementsDeserialized = new Movement[eachMovement.length];

        for(int i = 0; i < eachMovement.length; i++){
            String[] eachField = eachMovement[i].split("-");
            Card.Status playerOrEnemy = Integer.valueOf(eachField[0]) == 1? Card.Status.PLAYER: Card.Status.ENEMY;
            Vector2 cardPosition = new Vector2(Integer.valueOf(eachField[1]), Integer.valueOf(eachField[2]));
            Cards cardThrown = Cards.fromString(eachField[3]);
            int updateThrown = Integer.valueOf(eachField[4]);

            Movement currentMovement = new Movement(cardThrown, playerOrEnemy, cardPosition, updateThrown);
            movementsDeserialized[i] = currentMovement;
        }

        return movementsDeserialized.length == 0? null: movementsDeserialized;
    }

}

package business;

import business.entities.BattleInfo;
import persistence.BattleHistoryDAO;

import java.text.SimpleDateFormat;
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
        return null;
    }


    /**
     * Returns an array of simple {@link BattleInfo} representing the latest battles that were played
     * @param howMany How many battles want to be returned
     * @return Array of simple {@link BattleInfo} representing the latest Battles played
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo[] getLatestBattles(int howMany) throws SQLException {
        return null;
    }


    /**
     * Returns a complete {@link BattleInfo} representing the Battle with the ID specified
     * @param id The ID of the battle that wants to be returned
     * @return A complete {@link BattleInfo} representing the Battle with the ID specified
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo getCompleteBattleById(int id) throws SQLException {
        return null;
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
        return null;
    }


    /**
     * Deserializes the serialized {@code String} of Movements back into an array of {@link Movement}
     * @param movements The String of serialized movements
     * @return An array of {@link Movement} representing the serialized Movements provided
     */
    private Movement[] deserializeMovements(String movements){
        return null;
    }

}

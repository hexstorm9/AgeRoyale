package persistence;

import business.entities.BattleInfo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object for retrieving/updating information regarding BattleHistory
 */
public class BattleHistoryDAO {


    /**
     * Returns an array of simple {@link BattleInfo} representing the Battles that the user played.
     * @param user The user to check its battles
     * @return Array of simple {@link BattleInfo} representing the Battles that the user played
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo[] getBattlesByUser(String user) throws SQLException {
        final String getBattlesByUserQuery = "SELECT * FROM battle_history WHERE player_name='" + user + "' ORDER by battle_date desc";

        ArrayList<Object> resultsList;
        resultsList = JDBCConnector.getInstance().queryDatabase(getBattlesByUserQuery);

        //TODO: Read...
        //for(

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
     * Returns a complete {@link BattleInfo} representing the Battle with the ID specified.
     * <p>Note that the {@link BattleInfo} returned has only the serialized Battles. A deserialization is needed.
     *
     * @param id The ID of the battle that wants to be returned
     * @return A complete {@link BattleInfo} representing the Battle with the ID specified
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo getCompleteBattleById(int id) throws SQLException {
        return null;
    }


    /**
     * Provided the serialized movements, the name of the battle, the name of the player and whether the battle was won or not,
     * saves that Battle information to the database.
     *
     * @param movements The serialized {@code String} of {@link business.Movement} that occurred during the Battle
     * @param name The name of the battle. Can be {@code null}
     * @param playerName The name of the Player
     * @param won Whether the battle was won or not
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public void saveBattle(String movements, String name, String playerName, boolean won) throws SQLException {
        //TODO: Let's first do this one

    }



}

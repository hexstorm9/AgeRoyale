package persistence;

import business.entities.BattleInfo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Data Access Object for retrieving/updating information regarding BattleHistory
 */
public class BattleHistoryDAO {

    /**
     * This will be the String format that should be saved to a datetime field in the SQL Database
     */
    private SimpleDateFormat datetimeDatabaseDateFormat;


    /**
     * Default BattleHistoryDAO Constructor
     */
    public BattleHistoryDAO(){
        //This is the format that should be saved to a datetime field in the SQL Database
        datetimeDatabaseDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Returns an array of simple {@link BattleInfo} representing the Battles that the user played.
     * @param user The user to check its battles
     * @return Array of simple {@link BattleInfo} representing the Battles that the user played
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo[] getBattlesByUser(String user) throws SQLException{
        final String getBattlesByUserQuery = "SELECT id, name, player_name, won, battle_date FROM battle_history " +
                "WHERE player_name='" + user + "' ORDER by battle_date desc";

        ArrayList<Object> resultsList;
        resultsList = JDBCConnector.getInstance().queryDatabase(getBattlesByUserQuery);

        //Every Battle will have 5 fields (what we're asking on the query)
        BattleInfo[] userBattles = new BattleInfo[resultsList.size()/5];
        for(int i = 0, k = 0; i < resultsList.size(); i+=5, k++){

            int id = (int)resultsList.get(i);
            String name = (String)resultsList.get(i+1);
            String playerName = (String)resultsList.get(i+2);
            boolean won = (boolean)resultsList.get(i+3);
            Date battleDatePlayed = Date.from(((LocalDateTime)resultsList.get(i+4)).atZone(ZoneId.systemDefault()).toInstant());

            BattleInfo thisBattle = new BattleInfo(id, name, playerName, won, battleDatePlayed);
            userBattles[k] = thisBattle;
        }

        return userBattles.length == 0? null: userBattles;
    }


    /**
     * Returns an array of simple {@link BattleInfo} representing the latest battles that were played
     * @param howMany How many battles want to be returned
     * @return Array of simple {@link BattleInfo} representing the latest Battles played
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo[] getLatestBattles(int howMany) throws SQLException {
        final String getLatestBattlesQuery = "SELECT id, name, player_name, won, battle_date FROM battle_history " +
                "ORDER by battle_date desc LIMIT " + howMany + ";";

        ArrayList<Object> resultsList;
        resultsList = JDBCConnector.getInstance().queryDatabase(getLatestBattlesQuery);

        //Every Battle will have 5 fields (what we're asking on the query)
        BattleInfo[] latestBattles = new BattleInfo[resultsList.size()/5];
        for(int i = 0, k = 0; i < resultsList.size(); i+=5, k++){

            int id = (int)resultsList.get(i);
            String name = (String)resultsList.get(i+1);
            String playerName = (String)resultsList.get(i+2);
            boolean won = (boolean)resultsList.get(i+3);
            Date battleDatePlayed = Date.from(((LocalDateTime)resultsList.get(i+4)).atZone(ZoneId.systemDefault()).toInstant());

            BattleInfo thisBattle = new BattleInfo(id, name, playerName, won, battleDatePlayed);
            latestBattles[k] = thisBattle;
        }

        return latestBattles.length == 0? null: latestBattles;
    }


    /**
     * Returns a complete {@link BattleInfo} representing the Battle with the ID specified.
     * <p>Note that the {@link BattleInfo} returned has only the serialized Battles. <b>A deserialization is needed.</b>
     *
     * @param desiredBattleId The ID of the battle that wants to be returned
     * @return A complete {@link BattleInfo} representing the Battle with the ID specified. A deserialization is needed.
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public BattleInfo getCompleteBattleById(int desiredBattleId) throws SQLException {
        final String getCompleteBattleById = "SELECT id, name, player_name, won, battle_date, movements FROM battle_history " +
                "WHERE id = " + desiredBattleId + ";";

        ArrayList<Object> resultsList;
        resultsList = JDBCConnector.getInstance().queryDatabase(getCompleteBattleById);

        int id = (int)resultsList.get(0);
        String name = (String)resultsList.get(1);
        String playerName = (String)resultsList.get(2);
        boolean won = (boolean)resultsList.get(3);
        Date battleDatePlayed = Date.from(((LocalDateTime)resultsList.get(4)).atZone(ZoneId.systemDefault()).toInstant());
        String movements = (String)resultsList.get(5);

        return new BattleInfo(id, name, playerName, won, battleDatePlayed, movements);
    }


    /**
     * Provided the serialized movements, the name of the battle, the name of the player and whether the battle was won or not,
     * saves that Battle information to the database.
     *
     * @param movements The serialized {@code String} of {@link business.Movement} that occurred during the Battle
     * @param name The name of the battle. Can be {@code null}
     * @param playerName The name of the Player
     * @param won Whether the battle was won or not
     * @param datePlayed The {@link Date} when the Battle was played
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public void saveBattle(String movements, String name, String playerName, boolean won, Date datePlayed) throws SQLException {
        String stringDatePlayed = datetimeDatabaseDateFormat.format(datePlayed);
        final String insertBattleQuery = "INSERT INTO battle_history (movements, name, player_name, won, battle_date) VALUES ('" +
                movements + "', '" + name + "', '" + playerName + "', " + won + ", '" + stringDatePlayed + "');";

        JDBCConnector.getInstance().updateDatabase(insertBattleQuery);
    }



}

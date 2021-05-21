package persistence;


import business.entities.Player;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Data Access Object for retrieving/updating information from the Player.
 */
public class PlayerDAO {

    /**
     * Given a Player name, this method will return all of its attributes. If the Player name provided doesn't exist, the method will
     * return null. If it does, the array of Objects returned will be formatted:
     * <ul>
     *     <li>Position 0 -> The name of that Player (String)</ul>
     *     <li>Position 1 -> The Crowns (int)</ul>
     *     <li>Position 2 -> The Battle Wins (int)</ul>
     *     <li>Position 3 -> The Battle Plays (int)</ul>
     * </ul>
     * <p>The {@code Object[]} returned must be casted to the correct types.
     *
     * @param playerName The name of the player that wants to be loaded
     * @return Array of Objects with the Player information (Name (0), Crowns (1), Battle Wins (2), Battle Plays (3))
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     */
    public Object[] readPlayer(String playerName) throws SQLException {
        final String checkPlayerQuery = "SELECT * FROM player WHERE name = '" + playerName + "';";

        ArrayList<Object> resultsList;
        resultsList = JDBCConnector.getInstance().queryDatabase(checkPlayerQuery);

        Object[] objectsToBeReturned = new Object[4];
        objectsToBeReturned[0] = resultsList.get(0);
        objectsToBeReturned[1] = resultsList.get(1);
        objectsToBeReturned[2] = resultsList.get(2);
        objectsToBeReturned[3] = resultsList.get(3);

        return objectsToBeReturned;
    }


    /**
     * Updates the crowns of a given player in the database.
     * @param playerName The name of the Player
     * @param diff An integer (positive or negative) holding the crowns to add/subtract to the player
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     */
    public void updateCrowns(String playerName, int diff) throws SQLException{
        int playerCrowns = ((int)readPlayer(playerName)[1] + diff);

        //If playerCrowns given the difference is negative, let's assure a minimum value of 0
        if(playerCrowns < 0) playerCrowns = 0;

        final String updatePlayerCrownsQuery = "UPDATE player SET crowns = " + playerCrowns + " WHERE name = '" + playerName + "';";
        JDBCConnector.getInstance().updateDatabase(updatePlayerCrownsQuery);
    }


    /**
     * Updates the battles played of a given player in the database.
     * @param playerName The name of the Player
     * @param won Whether the battle played was won or not
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     */
    public void addBattlePlayed(String playerName, boolean won) throws SQLException{
        Object[] playerInfo = readPlayer(playerName);
        final int playerBattleWins = (int)playerInfo[2] + (won? 1: 0);
        final int playerBattlePlays = (int)playerInfo[3] + 1;

        final String updateBattleWinsQuery = "UPDATE player SET battle_wins = " + playerBattleWins + " WHERE name = '" + playerName + "';";
        final String updateBattlePlaysQuery = "UPDATE player SET battle_plays = " + playerBattlePlays + " WHERE name = '" + playerName + "';";

        //Run both queries
        JDBCConnector.getInstance().updateDatabase(new String[]{updateBattleWinsQuery, updateBattlePlaysQuery});
    }


    /**
     * Returns an array of {@link Player} ordered by win rate (battles won / total battles). From more win rate, to less.
     * <p>The size of the array will be defined by the param {@code playersToBeReturned}
     * @param playersNumberToBeReturned The size of the array to be returned
     * @return Array of Players ordered by win rate.
     *
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     * @see Player
     */
    public Player[] getPlayersByWinRate(int playersNumberToBeReturned) throws SQLException{
        final String playersByWinRateQuery = "SELECT * FROM player ORDER by (battle_wins/battle_plays) desc LIMIT " + playersNumberToBeReturned + ";";

        ArrayList<Object> objectsToBeReturned;
        objectsToBeReturned = JDBCConnector.getInstance().queryDatabase(playersByWinRateQuery);

        //ObjectsToBeReturned will hold Players 4 by 4 (the first 4 values will be the name of the first player, its
        // crowns, battle wins and battle plays). So let's loop through each of the 4 sets.
        Player[] playersToBeReturned = new Player[objectsToBeReturned.size()/4];
        for(int i = 0, k = 0; i < objectsToBeReturned.size(); i+=4, k++){
            playersToBeReturned[k] = convertInfoToPlayer(objectsToBeReturned.get(i), objectsToBeReturned.get(i + 1),
                    objectsToBeReturned.get(i + 2), objectsToBeReturned.get(i + 3));
        }

        return playersToBeReturned.length == 0? null: playersToBeReturned;
    }

    /**
     * Returns an array of {@link Player} ordered by crowns, from more crowns to less.
     * <p>The size of the array will be defined by the param {@code playersToBeReturned}
     * @param playersNumberToBeReturned The size of the array to be returned
     * @return Array of Players ordered by crowns.
     *
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     * @see Player
     */
    public Player[] getPlayersByCrowns(int playersNumberToBeReturned) throws SQLException{
        final String playersByWinRateQuery = "SELECT * FROM player ORDER by crowns desc LIMIT " + playersNumberToBeReturned + ";";

        ArrayList<Object> objectsToBeReturned;
        objectsToBeReturned = JDBCConnector.getInstance().queryDatabase(playersByWinRateQuery);

        //ObjectsToBeReturned will hold Players 4 by 4 (the first 4 values will be the name of the first player, its
        // crowns, battle wins and battle plays). So let's loop through each of the 4 sets.
        Player[] playersToBeReturned = new Player[objectsToBeReturned.size()/4];
        for(int i = 0, k = 0; i < objectsToBeReturned.size(); i+=4, k++){
            playersToBeReturned[k] = convertInfoToPlayer(objectsToBeReturned.get(i), objectsToBeReturned.get(i + 1),
                    objectsToBeReturned.get(i + 2), objectsToBeReturned.get(i + 3));
        }

        return playersToBeReturned.length == 0? null: playersToBeReturned;
    }


    /**
     * Converts the 4 {@code Object} provided into a Player.
     * @param name The name of the new Player. Must be a String.
     * @param crowns The crowns of the new Player. Must be an int.
     * @param battleWins The battle wins of the new Player. Must be an int.
     * @param battlePlays The battle plays of the new Player. Must be an int.
     * @return A new player with the information provided
     */
    private Player convertInfoToPlayer(Object name, Object crowns, Object battleWins, Object battlePlays){
        return new Player((String)name, (int)crowns, (int)battleWins, (int)battlePlays);
    }
}

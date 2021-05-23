package persistence;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Data Access Object for retrieving/updating information from the Player Cards.
 */
public class PlayerCardsDAO {


    /**
     * Given a player and the name of a Card, this method will return the level of that card for that player.
     * <p>If the player doesn't have that card, the method will return 0.
     * @param player Name of the Player
     * @param cardName Name of the Card which we want the information
     * @return The level that the player has that Card, or 0 if the player doesn't have the Card.
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     */
    public int readCard(String player, String cardName) throws SQLException{
        final String checkCardLevelQuery = "SELECT " + cardName + " FROM user_cards WHERE name = '" + player + "';";

        ArrayList<Object> resultsList;
        resultsList = JDBCConnector.getInstance().queryDatabase(checkCardLevelQuery);

        //The resultsList must have at least a result, the integer of the card for the player
        //Note that even when the player doesn't have the card, the card still has a value for that player (0)
        if(resultsList.isEmpty()) throw new SQLException("There is no Card " + cardName + " for Player " + player);

        return (int)resultsList.get(0);
    }


    /**
     * Given a player and the name of a Card, this method will increase by 1 the level of that card for that player.
     * <p>If the player didn't have the card yet (level 0), it will now be unlocked (level 1)
     * @param player Name of the Player
     * @param cardName Name of the Card we want to update
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     */
    public void cardLevelUp(String player, String cardName) throws SQLException {
        int cardLevel = readCard(player, cardName);
        int newCardLevel = cardLevel++;

        final String updateCardLevelQuery = "UPDATE user_cards SET " + cardName + " = " + newCardLevel + " WHERE name = '" + player + "';";
        JDBCConnector.getInstance().updateDatabase(updateCardLevelQuery);
    }



}

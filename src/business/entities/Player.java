package business.entities;

import java.sql.SQLException;
import java.util.HashMap;

public class Player {

    private String name;
    private int trophies;
    private int experience;
    private int battleWins;
    private int battlePlays;

    private HashMap<Cards, Integer> cardsHashMap;


    public Player(){}


    /**
     * Queries the database and loads all the values retrieved from it into this class' attributes.
     * @param playerNameOrEmail The name/email of the player that wants to be loaded
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public void initialize(String playerNameOrEmail) throws SQLException{

    }

}

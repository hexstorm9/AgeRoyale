package business.entities;

import java.sql.SQLException;
import java.util.HashMap;

public class Player {

    private String name;
    private String email;
    private int crowns;
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
        //TODO: Interact with the database and implement PlayerDAO
        name = "bielcarpi";
        email = "bielcarpi@outlook.com";
        crowns = 143;
        battleWins = 9;
        battlePlays = 13;
    }


    public String getName(){ return name;}


    public int getCrowns(){ return crowns;}

    public int getBattleWins(){ return battleWins;}
    public int getBattlePlays(){ return battlePlays;}

    public int getArena(){
        //There are two arenas at the moment of creating the class
        if(crowns < 300) return 1;
        else return 2;
    }

}

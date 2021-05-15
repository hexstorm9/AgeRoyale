package business.entities;

import persistence.PlayerDAO;

import java.sql.SQLException;
import java.util.HashMap;


public class Player {

    private String name;
    private int crowns;
    private int battleWins;
    private int battlePlays;

    private HashMap<Cards, Integer> playerCards;


    private PlayerDAO playerDAO;

    //private ChestInfo chestOne, chestTwo, chestThree, chestFour, chestFive;



    public Player(){}


    /**
     * Queries the database and loads all the values retrieved from it into this class' attributes.
     * @param playerNameOrEmail The name/email of the player that wants to be loaded
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public void initialize(String playerNameOrEmail) throws SQLException{
        //TODO: Interact with the database and implement PlayerDAO
        name = "bielcarpi";
        crowns = 143;
        battleWins = 9;
        battlePlays = 13;

        playerCards = new HashMap<>();
        playerCards.put(Cards.CARPI, 2);
        playerCards.put(Cards.CANO, 1);
        playerCards.put(Cards.ADAMS, 1);
        playerCards.put(Cards.DAVID, 1);
        playerCards.put(Cards.MALÉ, 1);
        playerCards.put(Cards.RAFA, 1);
        playerCards.put(Cards.SAULA, 1);
        playerCards.put(Cards.TRUMP, 1);
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

    public HashMap<Cards, Integer> getPlayerCards(){ return playerCards;}

}

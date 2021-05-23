package business.entities;

import persistence.PlayerCardsDAO;
import persistence.PlayerDAO;

import java.sql.SQLException;
import java.util.HashMap;


/**
 * {@code Player} class of the Game.
 * <p>It will serve as a container of the Player information, and an interface to modify itself
 * to the {@code database}.
 *
 * <p>Each game needs to have a Player, but this class can also be used as a container to store information
 * about other players.
 * <p><b>Never update a Player that is not the Player of the Game</b>
 *
 * @version 1.0
 */
public class Player {

    private String name;
    private int crowns;
    private int battleWins;
    private int battlePlays;

    private HashMap<Cards, Integer> playerCards;


    private PlayerDAO playerDAO;
    private PlayerCardsDAO playerCardsDAO;



    /**
     * Default Player Constructor.
     * <p>Loads all its attributes from the DB.
     */
    public Player(String name) throws SQLException{
        this.name = name;
        updateAttributesFromDB();

        playerDAO = new PlayerDAO();
        playerCardsDAO = new PlayerCardsDAO();
    }


    /**
     * Player Constructor when we know the player attributes already.
     * <p>This kind of Player won't be able to interact with the DB (the method {@link #updateAttributesFromDB()}
     * won't do anything) and its playerCard {@code HashMap} will be empty.
     */
    public Player(String name, int crowns, int battleWins, int battlePlays){
        this.name = name;
        this.crowns = crowns;
        this.battlePlays = battlePlays;
        this.battleWins = battleWins;
    }


    /**
     * Queries the database and loads all the values retrieved from it into this class' attributes.
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public void updateAttributesFromDB() throws SQLException{
        try{
            playerDAO = new PlayerDAO();
            playerCardsDAO = new PlayerCardsDAO();
        }catch(NullPointerException e){
            //When the Player is created from the second constructor, it won't be able to update its attributes from the DB
            //The PlayerDAO and PlayerCardsDAO will be null
            return;
        }

        Object[] information = playerDAO.readPlayer(name);

        name = (String) information[0];
        crowns = (int) information[1];
        battleWins = (int) information[2];
        battlePlays = (int) information[3];

        playerCards = new HashMap<>();
        //Let's loop through all the Cards of the game and search the level of that card for the current player
        for(Cards c: Cards.values()){
            if(c.isTower()) continue; //We don't want to read the towers
            int cardLevel = playerCardsDAO.readCard(name, c.toString());
            if(cardLevel > 0) playerCards.put(c, cardLevel); //If the player has that card, let's put it into the playerCards HashMap
        }
    }


    /**
     * Returns the name of the player
     * @return Name of the player
     */
    public String getName(){ return name;}


    /**
     * Returns the current crowns of the player
     * @return Current crowns of the player
     */
    public int getCrowns(){ return crowns;}

    /**
     * Returns the battle Wins of the player
     * @return Battle wins of the player
     */
    public int getBattleWins(){ return battleWins;}

    /**
     * Returns the battle plays of the player
     * @return Battle plays of the player
     */
    public int getBattlePlays(){ return battlePlays;}


    /**
     * Returns the arena the player is in
     * @return The arena the player is in
     */
    public int getArena(){
        //There is only an arena for the moment
        return 1;
    }

    /**
     * Returns a {@code HashMap<Cards, Integer>} for this player, where each {@link Cards} in the HashMap maps to an
     * {@link Integer} representing its level.
     * @return {@code HashMap<Cards, Integer>} for this player, where each {@link Cards} in the HashMap maps to an
     * {@link Integer} representing its level.
     */
    public HashMap<Cards, Integer> getPlayerCards(){ return playerCards;}



    /**
     * Updates the Player information after a battle (both locally and remotely)
     * <p>Do not call the method from the EDT, it would block it.
     *
     * @param crownsDiff The difference of crowns
     * @param won Whether the battle was won or not
     */
    public void updateAfterBattlePlayed(int crownsDiff, boolean won){
        crowns += crownsDiff;
        battlePlays++;
        battleWins += won ? 1: 0;
        try{
            playerDAO.updateCrowns(name, crownsDiff);
            playerDAO.addBattlePlayed(name, won);
        }catch(SQLException e){ e.printStackTrace();}
    }

}

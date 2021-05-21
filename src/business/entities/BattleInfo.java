package business.entities;

import business.Movement;

import java.util.Date;


/**
 * BattleInfo holds the information of a Battle.
 * <p>There can be two types of {@code BattleInfo}, depending on the constructor used:
 * <ul>
 *     <li>A simple battleInfo, without the battle movements information, so as to have access to simple information</li>
 *     <li>A complete battleInfo, with the battle movements information</li>
 * </ul>
 *
 * @version 1.0
 * @see business.Movement
 */
public class BattleInfo {

    private int id;
    private String name;
    private String playerName;
    private boolean won;
    private Date datePlayed;


    private String serializedMovements; //The movements will be retrieved from the DB as a String, it's the task of other class to convert them to a Movement[]
    private Movement[] movements;


    /**
     * Simple BattleInfo Constructor.
     * <p>Creates a new BattleInfo object, but without movements information
     * @param id The id of the battle
     * @param name The name of the battle
     * @param playerName The name of the player
     * @param won Whether the player won or not
     * @param datePlayed The date the battle was played
     */
    public BattleInfo(int id, String name, String playerName, boolean won, Date datePlayed){
        this.id = id;
        this.name = name;
        this.playerName = playerName;
        this.won = won;
        this.datePlayed = datePlayed;
    }


    /**
     * Complete BattleInfo Constructor.
     * <p>Creates a new BattleInfo object, with movements information.
     * @param id The id of the battle
     * @param name The name of the battle
     * @param playerName The name of the player
     * @param won Whether the player won or not
     * @param datePlayed The date the battle was played
     * @param serializedMovements The movements of the Battle as a String
     */
    public BattleInfo(int id, String name, String playerName, boolean won, Date datePlayed, String serializedMovements){
        this(id, name, playerName, won, datePlayed);
        this.serializedMovements = serializedMovements;
    }


    /**
     * Returns the ID of the Battle
     * @return The ID of the Battle
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the Battle
     * @return The name of the Battle
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player name of the Battle
     * @return The player name of the Battle
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns whether the Player won or not
     * @return Whether the Player won or not
     */
    public boolean isWon() {
        return won;
    }

    /**
     * Returns the Date when the Battle was played
     * @return The Date when the Battle was played
     */
    public Date getDatePlayed() {
        return datePlayed;
    }

    /**
     * Returns the serialized movements of the Battle (the movements as a String)
     * @return The serialized movements of the Battle
     */
    public String getSerializedMovements() {
        return serializedMovements;
    }

    /**
     * Returns all the {@link Movement} of the Battle
     * @return All the {@link Movement} of the Battle
     */
    public Movement[] getMovements() {
        return movements;
    }

    /**
     * Sets the Movements of the Battle, once the serializedMovements have been deserialized
     * @param movements Array of movements of the Battle
     */
    public void setMovements(Movement[] movements){
        this.movements = movements;
    }

}

package business;

import business.entities.Cards;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * BattleBot is a class that will simulate an enemy player on the right map side
 * <p>It will throw random Cards into the map, based on the gold it has.
 * <p>Note that a {@code BattleBot} will run in its own {@link Thread}. When instantiated, use {@link #startBot()} and {@link #stopBot()}
 * to start and stop it.
 *
 * @see BattleModel
 * @version 1.0
 */
public class BattleBot implements Runnable{

    private Thread botThread; //Thread in which the bot will run

    private BattleModel battleModel; //Battle in which the bot will participate. We need to know the gold
    private Dimension battlePanelSize; //Size of the battlePanel

    private Cards[] offensiveTroops; //The offensive troops that the Bot has
    private Cards[] defensiveTroops; //The defensive troops that the Bot has

    /**
     * Time in ms that the Bot will wait until trying to throw another card
     */
    private static final int TIME_UNTIL_NEXT_THROW = 3000;


    /**
     * Default BattleBot constructor.
     * <p>Creates a new BattleBot but it doesn't start it. In order to do so, call {@link #startBot()}
     *
     * @param battleModel A reference to the BattleModel, so as to be able to throw cards
     * @param battlePanelSize The size of the BattlePanel, so as to select a correct position to throw the card into
     */
    public BattleBot(BattleModel battleModel, Dimension battlePanelSize){
        botThread = new Thread(this);
        this.battleModel = battleModel;
        this.battlePanelSize = battlePanelSize;


        ArrayList<Cards> offensiveTroopsBuffer = new ArrayList<>();
        ArrayList<Cards> defensiveTroopsBuffer = new ArrayList<>();
        for(Cards c: Cards.values()){ //The BattleBot will have all the cards unlocked
            if(!c.isTower()){
                if(c.isDefensive())
                    defensiveTroopsBuffer.add(c);
                else
                    offensiveTroopsBuffer.add(c);
            }
        }

        offensiveTroops = new Cards[offensiveTroopsBuffer.size()];
        offensiveTroops = offensiveTroopsBuffer.toArray(offensiveTroops);

        defensiveTroops = new Cards[defensiveTroopsBuffer.size()];
        defensiveTroops = defensiveTroopsBuffer.toArray(defensiveTroops);
    }


    /**
     * Starts the bot in its own Thread.
     */
    public void startBot(){
        botThread.start();
    }


    /**
     * Stops the bot.
     */
    public void stopBot(){
        botThread.interrupt();
    }


    /**
     * Do not call from outside the class.
     * <p>Method that will run on the new thread of the BattleBot.
     */
    @Override
    public void run() {
        Random r = new Random();
        boolean correctPosition = false;
        int xPos = 0, yPos = 0;

        //Do not stop, unless an interruption of the thread is called
        while(true){
            try{
                //We'll wait for X seconds before trying to instantiate a new troop every time.
                //If there are enemies on the Bot's map, we'll randomly select an offensive or defensive troop
                //If there aren't, we'll randomly select an offensive troop
                //(always taking into account the enemy's gold)

                Thread.sleep(TIME_UNTIL_NEXT_THROW);

                //Select the card that will be tried to throw (depending on whether there are enemies on the Bot's map or not)
                Cards cardToThrow;
                if(battleModel.isPlayerOnEnemyMap())
                    cardToThrow = r.nextBoolean() ? offensiveTroops[r.nextInt(offensiveTroops.length)]: defensiveTroops[r.nextInt(defensiveTroops.length)];
                else
                    cardToThrow = offensiveTroops[r.nextInt(offensiveTroops.length)];


                //If the bot hasn't enough gold to launch the Card it randomly selected, let's wait another X seconds
                if(battleModel.getEnemyCurrentGold() < cardToThrow.getGoldCost()) continue;


                //Now that we have the Card to throw and enough Gold, let's try to throw it until we find a correct position
                correctPosition = false;
                while(!correctPosition){
                    xPos = r.nextInt(battlePanelSize.width);
                    yPos = r.nextInt(battlePanelSize.height);

                    correctPosition = battleModel.throwCard(cardToThrow, Card.Status.ENEMY, xPos, yPos);
                    if(correctPosition) System.out.println("Bot card thrown --> " + cardToThrow.toString() + " in " + xPos + "x " + yPos + "y");
                }
            }catch(InterruptedException e){
                break; //Whenever an InterruptedException occurs, break from the infinite loop (if the thread has
                        //been interrupted, it means the program stopped it calling stopBot())
            }
        }
    }


}

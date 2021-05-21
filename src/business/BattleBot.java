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
     */
    public BattleBot(BattleModel battleModel, Dimension battlePanelSize){
        botThread = new Thread(this);
        this.battleModel = battleModel;
        this.battlePanelSize = battlePanelSize;


        ArrayList<Cards> offensiveTroopsBuffer = new ArrayList<>();
        ArrayList<Cards> defensiveTroopsBuffer = new ArrayList<>();
        for(Cards c: Cards.values()){ //The BattleBot will have all the cards unlocked
            if(!c.isTower()){
                if(c.isDefensive()) defensiveTroopsBuffer.add(c);
                else offensiveTroopsBuffer.add(c);
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
                Thread.sleep(TIME_UNTIL_NEXT_THROW); //Check every X seconds whether it can instantiate a troop with the amount of gold it has

                if(battleModel.isPlayerOnEnemyMap()){

                }

                Cards cardToThrow = offensiveTroops[r.nextInt(offensiveTroops.length)];

                if(battleModel.getEnemyCurrentGold() >= cardToThrow.getGoldCost())

                while(!correctPosition){

                    xPos = r.nextInt(battlePanelSize.width);
                    yPos = r.nextInt(battlePanelSize.height);

                    correctPosition = battleModel.throwCard(cardToThrow, Card.Status.ENEMY, xPos, yPos);
                    if(correctPosition) System.out.println("Bot card thrown --> " + cardToThrow.toString() + " in " + xPos + "x " + yPos + "y");
                }

            }catch(InterruptedException e){
                break; //Whenever an InterruptedException occurs, break from the infinite loop (as the thread has
                        //been interrupted)
            }
        }
    }


}

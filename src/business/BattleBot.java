package business;

import business.entities.Cards;

import java.awt.*;
import java.util.Random;

/**
 * BattleBot is a class that will simulate an enemy player on the right map side
 * <p>It will only throw random Cards into the map
 *
 * @see BattleModel
 * @version 1.0
 */
public class BattleBot implements Runnable{

    private Thread botThread; //Thread in which the bot will run
    private BattleModel battleModel; //Battle in which the bot will participate
    private Dimension battlePanelSize; //Size of the battlePanel

    private Cards[] botCards;


    /**
     * Default BattleBot constructor.
     * <p>Creates a new BattleBot but it doesn't start it. In order to do so, call {@link #startBot()}
     */
    public BattleBot(BattleModel battleModel, Dimension battlePanelSize){
        botThread = new Thread(this);
        this.battleModel = battleModel;
        this.battlePanelSize = battlePanelSize;

        botCards = Cards.values(); //The bot will have all the cards
    }


    /**
     * Starts the bot
     */
    public void startBot(){
        botThread.start();
    }


    /**
     * Stops the bot
     */
    public void stopBot(){
        botThread.interrupt();
    }


    @Override
    public void run() {
        while(true){
            Random r = new Random();
            boolean correctPosition = false;
            int xPos = 0, yPos = 0;

            try{
                Thread.sleep(10000);
                while(!correctPosition){
                    Cards cardToThrow = botCards[r.nextInt(botCards.length)];
                    xPos = r.nextInt(battlePanelSize.width);
                    yPos = r.nextInt(battlePanelSize.height);
                    if(cardToThrow.isTower()) continue; //If it's tower, let's repeat the loop

                    correctPosition = battleModel.throwCard(cardToThrow, Card.Status.ENEMY, xPos, yPos);
                    if(correctPosition) System.out.println("Bot card thrown --> " + cardToThrow.toString() + " in " + xPos + "x " + yPos + "y");
                }

            }catch(Exception e){e.printStackTrace();}
        }
    }


}

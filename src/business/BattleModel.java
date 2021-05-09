package business;

import java.awt.*;
import java.util.ArrayList;

public class BattleModel {

    private ArrayList<Card> playerCards;
    private ArrayList<Card> enemyCards;
    private Map map;


    public BattleModel(Dimension battlePanelDimension){
        map = new Map(battlePanelDimension);
        enemyCards = new ArrayList<>();
        playerCards = new ArrayList<>();
    }



    public void update(){
        for(Card c: playerCards) c.update();
        for(Card c: enemyCards) c.update();
    }


    public void draw(Graphics g){
        for(Card c: playerCards) c.draw(g);
        for(Card c: enemyCards) c.draw(g);
        map.draw(g);
    }


    public Card getRivalCardInRange(Card currentCard, int range, Vector2 currentPosition){
        if(playerCards.contains(currentCard)){

        }
        else{

        }

        return null;
    }


}

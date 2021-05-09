package business;

import java.awt.*;


public abstract class Card {

    protected BattleModel battleModel;

    protected Vector2 position;
    protected int health;
    protected int damage;
    protected int range;
    protected int attackingVelocity;

    private Card currentlyAttackingCard;


    public Card(int level, Vector2 initialPosition, BattleModel battleModel){
        position = initialPosition;
        this.battleModel = battleModel;
        initializeAttributes(level);
    }



    public void update(){
    }


    public void draw(Graphics g){

    }


    public abstract void initializeAttributes(int level);

    public void attack(){

    }




}

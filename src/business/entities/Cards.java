package business.entities;

import java.util.Arrays;
import java.util.Locale;

/**
 * The Cards {@code enum} will contain all the Cards information of the game
 */
public enum Cards {
    ADAMS(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10),
    TRUMP(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10),
    DAVID(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10),
    RAFA(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10),
    SAULA(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10),
    CANO(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10),
    MALÉ(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10),
    CARPI(2, new int[]{70, 120, 160}, new int[]{20, 30, 40}, 4, 10, 10);

    private int goldCost;
    private int[] maxHealth; //Will vary depending on the level
    private int[] damage; //Will vary depending on the level
    private int range;
    private int attackingVelocity;
    private int movingVelocity;

    Cards(int goldCost, int[] maxHealth, int[] damage, int range, int attackingVelocity, int movingVelocity){
        this.goldCost = goldCost;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.range = range;
        this.attackingVelocity = attackingVelocity;
        this.movingVelocity = movingVelocity;
    }

    public int getGoldCost(){ return goldCost;}
    public int getRange(){ return range;}
    public int getAttackingVelocity(){ return attackingVelocity;}
    public int getMovingVelocity(){return movingVelocity;}
    public int getMaxHealth(int level){ return level >= maxHealth.length ? maxHealth[maxHealth.length-1]: maxHealth[level]; }
    public int getDamage(int level){ return level >= damage.length ? damage[damage.length-1]: damage[level]; }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}

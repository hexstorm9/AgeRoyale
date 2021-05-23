package business.entities;


/**
 * The Cards {@code enum} will contain all the Cards information of the game
 */
public enum Cards {
    ADAMS(3, new int[]{250, 280, 320}, new int[]{20, 25, 30}, 4, 10, 0),
    TRUMP(3, new int[]{250, 280, 320}, new int[]{30, 40, 50}, 2, 6, 10),
    DAVID(5, new int[]{250, 280, 320}, new int[]{60, 90, 120}, 1, 7, 4),
    RAFA(4, new int[]{250, 280, 320}, new int[]{20, 30, 40}, 3, 8, 7),
    SAULA(4, new int[]{250, 280, 320}, new int[]{20, 25, 30}, 6, 10, 0),
    CANO(2, new int[]{250, 280, 320}, new int[]{20, 30, 40}, 4, 8, 8),
    MALÉ(2, new int[]{250, 280, 320}, new int[]{25, 35, 45}, 3, 15, 12),
    CARPI(6, new int[]{250, 280, 320}, new int[]{50, 60, 70}, 4, 10, 4),

    TOWER(true, 1000, new int[]{800, 1000, 1200}, new int[]{10, 15, 20}, 4, 10, 0);


    private int goldCost;
    private int[] maxHealth; //Will vary depending on the level
    private int[] damage; //Will vary depending on the level
    private int range;
    private int attackingVelocity;
    private int movingVelocity;
    private boolean isTower;

    /**
     * Default Cards Constructor
     * @param isTower Whether the Card is a tower or not
     * @param goldCost Its gold cost
     * @param maxHealth Its max health
     * @param damage Its damage
     * @param range Its range
     * @param attackingVelocity Its attacking velocity
     * @param movingVelocity Its moving velocity
     */
    Cards(boolean isTower, int goldCost, int[] maxHealth, int[] damage, int range, int attackingVelocity, int movingVelocity){
        this.goldCost = goldCost * 10; //Internally, the gold cost will go from 0 to 100 instead of 0 to 10
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.range = range;
        this.attackingVelocity = attackingVelocity;
        this.movingVelocity = movingVelocity;
        this.isTower = isTower;
    }

    /**
     * Shorter Cards Constructor. The Cards won't be a Tower
     * @param goldCost Its gold cost
     * @param maxHealth Its max health
     * @param damage Its damage
     * @param range Its range
     * @param attackingVelocity Its attacking velocity
     * @param movingVelocity Its moving velocity
     */
    Cards(int goldCost, int[] maxHealth, int[] damage, int range, int attackingVelocity, int movingVelocity){
        this(false, goldCost, maxHealth, damage, range, attackingVelocity, movingVelocity);
    }

    public int getGoldCost(){ return goldCost;}
    public int getRange(){ return range;}
    public int getAttackingVelocity(){ return attackingVelocity;}
    public int getMovingVelocity(){return movingVelocity;}
    public int getMaxHealth(int level){ return level >= maxHealth.length ? maxHealth[maxHealth.length-1]: maxHealth[level]; }
    public int getDamage(int level){ return level >= damage.length ? damage[damage.length-1]: damage[level]; }
    public boolean isTower(){ return isTower;}
    public boolean isDefensive(){ return movingVelocity == 0;}

    /**
     * Returns the String representing that Cards (its name, but in lowercase)
     * @return String representing that Cards (its name, but in lowercase)
     */
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * Provided a name of the {@link Cards}, returns its value or {@code null} if the card does not exist.
     * @param card The name of the {@link Cards} that wants to be returned
     * @return The {@link Cards} that the provided {@code String} represents or {@code null} if the card does not exist.
     */
    public static Cards fromString(String cardName){

        cardName = cardName.toLowerCase();
        for(Cards c: Cards.values())
            if(c.toString().equals(cardName)) return c;

        return null;
    }
}

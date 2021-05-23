package business;

/**
 * A {@code Vector2} is an object that saves information about a 2D Position (the x and y positions)
 *
 * @version 1.0
 */
public class Vector2 {
    /**
     * The X position of the Vector2
     */
    public double x;
    /**
     * The Y position of the Vector2
     */
    public double y;

    /**
     * Default Vector2 Constructor.
     * @param x The x position
     * @param y The y position
     */
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

}

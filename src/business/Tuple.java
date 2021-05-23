package business;

/**
 * {@code Tuple} is a class that houses (saves) objects in pairs
 * @param <T> First object to save
 * @param <R> Second object to save
 */
public class Tuple<T, R> {

    /**
     * The first object that the Tuple holds
     */
    public final T firstField;
    /**
     * The second object that the Tuple holds
     */
    public final R secondField;

    /**
     * Default Tuple Constructor.
     * @param t First object to save
     * @param r Second object to save
     */
    public Tuple(T t, R r) {
        this.firstField = t;
        this.secondField = r;
    }
}

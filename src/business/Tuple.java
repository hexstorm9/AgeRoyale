package business;

public class Tuple<T, R> {

    public final T firstField;
    public final R secondField;

    public Tuple(T t, R r) {
        this.firstField = t;
        this.secondField = r;
    }
}

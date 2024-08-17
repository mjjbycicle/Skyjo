package core.util;

/**
 * Represents a generic pair of values.
 */
public class Pair<T, U> {
    public final T a;
    public final U b;

    public Pair(T a, U b) {
        this.a = a;
        this.b = b;
    }

    public T a() { return a; }
    public U b() { return b; }
}
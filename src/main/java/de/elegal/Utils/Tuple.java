package de.elegal.Utils;

import java.util.Objects;

/**
 * Just a generic Tuple whenever its needed to store 2 Values
 * @param <T> The first generic value
 * @param <U> the second genric value
 * @author Lars Quentin, Valerius Mattfeld
 */
public class Tuple<T, U> {
    /**
     * The first generic Type
     */
    private T t;

    /**
     * The second generic Type
     */
    private U u;

    /**
     * The Constructor. Since it's generic we don't care if its malformed in any other way.
     * @param t the first generic Type
     * @param u the second generic Type
     */
    public Tuple(T t, U u) {
        setFirst(t);
        setSecond(u);
    }

    /**
     * A setter for the first generic type without any type of checking
     * @param t the new first generic type
     */
    public void setFirst(T t) {
        Objects.requireNonNull(t, "First parameter was null!");
        this.t = t;
    }

    /**
     * A setter for the second generic type without any type of checking
     * @param u the new second generic type
     */
    public void setSecond(U u) {
        Objects.requireNonNull(u, "Second parameter was null!");
        this.u = u;
    }

    /**
     * A getter for the first generic type
     * @return the value of the first generic type
     */
    public T getFirst() {
        return this.t;
    }

    /**
     * A getter for the second generic type
     * @return the value of the second generic type
     */
    public U getSecond() {
        return this.u;
    }
}

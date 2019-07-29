package de.elegal;

public class Tuple<T, U> {
    private T t;
    private U u;

    public Tuple(T t, U u) {
        this.t = t;
        this.u = u;
    }

    public void setFirst(T t) {
        this.t = t;
    }

    public void setSecond(U u) {
        this.u = u;
    }

    public T getFirst() {
        return this.t;
    }

    public U getSecond() {
        return this.u;
    }
}

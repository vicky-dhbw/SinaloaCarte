package org.sinaloaCarte.S01;

public abstract class Subscriber {
    protected int id;  // an abstract class with a protected field acts as an interface

    public Subscriber(int id) {
        this.id = id;
    }
}

package org.sinaloaCarte.S01_S02;

public abstract class Subscriber {
    protected int id;  // an abstract class with a protected field acts as an interface

    public Subscriber(int id) {
        this.id = id;
    }

    public int getID(){
        return id;
    }
}

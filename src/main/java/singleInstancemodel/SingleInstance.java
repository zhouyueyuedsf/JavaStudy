package singleInstancemodel;

public class SingleInstance {
    private final static SingleInstance singleInstance = new SingleInstance();

    public static SingleInstance getSingleInstance() {

        return singleInstance;
    }
}

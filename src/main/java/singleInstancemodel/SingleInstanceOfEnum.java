package singleInstancemodel;

public class SingleInstanceOfEnum {

    public enum Single {
        INSTANCE;
        public void doSomethings(){

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Single.INSTANCE.hashCode());
        }
    }
}

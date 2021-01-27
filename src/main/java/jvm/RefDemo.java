package jvm;

public class RefDemo {

    public void test(RefDemo refDemo) {

    }

    public static void main(String[] args) {
        RefDemo refDemo = new RefDemo();
        refDemo.test(refDemo);
    }
}

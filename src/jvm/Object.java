package jvm;

public class Object {
    public static void main(String[] args) {
        int a = 1;
        a = a++;
        System.out.println(a);
        //System.out.println(Object.class);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

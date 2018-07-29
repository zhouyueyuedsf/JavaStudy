package reflex.proxy.jdkproxy;

public class Chinese implements ChineseInterface{
    public static int i = 10;
    @Override
    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public String toString() {
        return "chinese";
    }
}

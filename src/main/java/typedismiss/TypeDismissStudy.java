package typedismiss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class TypeDismissStudy<T> {

    public static class Fruit {
    }

    public static class Apple extends Fruit{
    }

    public static class Melon extends Fruit {
    }


    T t;


    public static <R extends Fruit> void test1(List<R> fruits) {

    }

    public static <R> void test2(List<R> anys) {

    }

    public static void test3(List<Object> objects) {
//        objects.add(1);

    }

    public static void test4(Object object) {
        boolean b = object instanceof List;
    }

    public static void test5(List<?> anys) {

    }

    public static void test6() {
        List<Fruit> fruits = new ArrayList<>();
//        fruits.add(new Fruit());
        fruits.add(new Apple());
        fruits.add(new Melon());
    }

    public static void printSum(Collection<?> c) {
    }

    public static void printSum2(Collection<Integer> c) {
    }

    public static void main(String[] args) {
        TypeDismissStudy<String> typeDismissStudy = new TypeDismissStudy<>();
        typeDismissStudy.t = "123";
        Object o = "123";
        String s = typeDismissStudy.t;
        List<Apple> apples = new ArrayList<>();
        List<Fruit> fruits = new ArrayList<>();
        List<Melon> melons = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        test1(melons);
        test2(melons);
//        test3(melons);
        test5(melons);

        printSum(melons);
        List<Integer> integers = new ArrayList<>();
        printSum2(new ArrayList<Integer>());
    }
}

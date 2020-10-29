package typedismiss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeDismissStudy<T> {
    T t;
    public static void main(String[] args) {
        TypeDismissStudy<String> typeDismissStudy = new TypeDismissStudy<>();
        typeDismissStudy.t = "123";
        Object o = "123";
        String s = typeDismissStudy.t;
        List<Apple> apples = new ArrayList<>();
        List<Fruit> fruits = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        test2(apples);
        test2(fruits);
    }

    public static void test1(List<Fruit> fruits) {

    }

    /**
     * 泛型只在编译期生效
     * @param apples
     * @param <T>
     */
    public static <T extends Fruit> void test2(List<T> apples) {

    }

    public static void test3(List<Object> objects) {

    }

    public static void test4(Object object) {
        boolean b = object instanceof List;
    }

    public static void printSum(Collection<?> c) {
        List<Integer> list = (List<Integer>)c;
    }

    public static void printSum2(Collection<Integer> c) {
//        if (c instanceof List<Integer>) {
//
//        }
    }
}

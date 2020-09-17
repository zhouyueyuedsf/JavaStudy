package typedismiss;

import java.util.ArrayList;
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
        test2(apples);
    }

    public static void test1(List<Fruit> fruits) {

    }

    public static void test2(List<?> apples) {

    }
}

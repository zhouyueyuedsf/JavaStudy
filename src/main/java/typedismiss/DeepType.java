package typedismiss;


import java.util.ArrayList;
import java.util.List;

interface Animal<T> {
    void test(T t);
}

class Dog implements Animal<String> {
    @Override
    public void test(String s) {

    }
}

public class DeepType {

    public void test1() {
        List<Integer> list = new ArrayList<>();
        //获取不到泛型信息
        list.getClass().getGenericSuperclass();
        List<TypeDismissStudy.Fruit> list1 = new ArrayList() {
        };
        //可以获取到泛型信息
        list1.getClass().getGenericSuperclass();
    }

    public void test2(List<?> list) {

    }

    public <R> void test3(List<R> list) {

    }
}

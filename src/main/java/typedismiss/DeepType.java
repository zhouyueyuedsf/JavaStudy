package typedismiss;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void test4() {
        Set<Integer> integerSet = new HashSet<>();

        Object[] objects = new Long[1];
        objects[0] = "i don't fit in";

        // 居然可以new出一个?类型的列表数组
        List<?>[] lists = new List<?>[2];
        //
//        List<String>[] stringLists = new List<String>[2];
    }


}

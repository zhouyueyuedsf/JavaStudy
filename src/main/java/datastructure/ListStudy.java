package datastructure;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class ListStudy {

    public static void test1() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (String str : list) {
            if ("1".equals(str)) {
                list.remove(str);
            }
        }
        System.out.println(list);
    }

    public static void test1_1() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
//            iterator.remove();
            String str = iterator.next();
            if ("1".equals(str)) {
                list.remove(str);
            }
        }
        System.out.println(list);
    }

    public static void test2() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    public static void test3() {
        final List<String> list = new ArrayList<>();
        //模拟10个线程向list中添加内容，并且读取内容
        for (int i = 0; i < 100; i++) {
            final int j = i;
            new Thread(() -> {
                //添加内容
                System.out.println("curThread = " + Thread.currentThread());
                list.add(j + "-j");
            }).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("curThread = " + Thread.currentThread() + "list size " + list.size() + " list = " + list);
    }

    public static void main(String[] args) {
        ListStudy.test3();
    }
}

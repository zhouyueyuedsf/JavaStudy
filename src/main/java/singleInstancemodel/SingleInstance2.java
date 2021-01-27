package singleInstancemodel;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SingleInstance2 {
    private String mName;
    private static SingleInstance2 getSingleInstance(String name) {
        SingleInstance2 instance =  SingleInstanceHolder.instance;
        instance.setParams(name);
        return instance;
    }

    public void setParams(String name) {
        this.mName = name;
    }

    private static class SingleInstanceHolder {
        private final static SingleInstance2 instance = new SingleInstance2();
    }

    static  int anInt = 0;
    public static void main(String[] args) {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10000; i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(SingleInstance2.getSingleInstance("zhouyueyue" + (++anInt)));
                }
            });
        }

    }
}

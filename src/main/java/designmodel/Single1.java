package designmodel;

public class Single1 {
    //
    private volatile static Single1 instance = null;
    // double check
    public static Single1 getInstance() {
        // 如果这里不做控制，每次都要进入synchronized，影响性能
        if (instance == null) {
            // 多个线程竞争锁，去初始化instance
            synchronized (Single1.class) {
                // A,B,C线程依次进入该代码块，需要判断，A是否初始化了instance
                if (instance == null) {
                    // 1. 分配内存 2.对构造方法进行初始化 3.将instance对象指向分配的内存空间
                    instance = new Single1();
                }
            }
        }
        return instance;
    }
}
enum Single2 {
    SINGLE_2;
    public static Single2 getInstance() {
        return SINGLE_2;
    }
}

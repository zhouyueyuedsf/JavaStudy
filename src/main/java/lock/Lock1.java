package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Lock1 {

    public static  ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public final Object object = new Object();
    public void test1() {
        synchronized (object) {

        }
    }

    public synchronized void test2() {

    }

    public static void main(String[] args) throws InterruptedException {
        int j = 0;

        int i = 0;
        while (j < 10){
            j++;
            lock.lock();
            if(j < 10){
                condition.await();
            }
            System.out.println(i);
            lock.unlock();

        }

    }
}

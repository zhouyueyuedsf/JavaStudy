package lockandthread;

import utils.MyLog;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Lock2 {

    static int i = 0;
    private static Object object = new Object();
    static int count = 1;

    private final ReentrantLock lock = new ReentrantLock();
    public void beginTransaction() {

    }

    public void syncAtomTest() throws InterruptedException {
        while (count < 10000) {
            i++;
            MyLog.INSTANCE.log("syncAtomTest i = " + i);
            count++;
            Thread.yield();
        }
    }

    public void syncAtomTest2() throws InterruptedException {
        lock.lock();
        while (count < 100) {
            i++;
            MyLog.INSTANCE.log("syncAtomTest i = " + i);
            count++;
//            Thread.yield();
        }
        lock.unlock();
    }
    Semaphore semaphore = new Semaphore(1);
    public void syncAtomTest3() throws InterruptedException {
        semaphore.acquire();
        while (count < 100) {
            i++;
            MyLog.INSTANCE.log("syncAtomTest i = " + i);
            count++;
//            Thread.yield();
        }
        semaphore.release();
    }

    public void inc3() throws InterruptedException {
        semaphore.acquire();
        i++;
        MyLog.INSTANCE.log("inc i = " + i);
        semaphore.release();
    }

    public void test() {
        Lock2 lock2 = new Lock2();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.syncAtomTest2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.setPriority(10);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock2.inc();
            }
        });
        thread1.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test3() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncAtomTest3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.setPriority(10);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    inc3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void inc() {
//        synchronized (object) {
            i++;
            MyLog.INSTANCE.log("inc i = " + i);
//        }
    }

    public static void main(String[] args) throws Exception {
        Lock2 lock2 = new Lock2();
        lock2.test3();
    }
}

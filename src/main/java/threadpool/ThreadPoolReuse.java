package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolReuse {

    int i = 0 ;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread() + "\n");
//                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
//        ScheduledThreadPoolExecutor scheduledPool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(3);

        test2();
    }

    private static void test2() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread running");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
            thread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test1() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        pool.setKeepAliveTime(1, TimeUnit.MICROSECONDS);
        pool.allowCoreThreadTimeOut(true);
        for (int i = 0; i < 10; i++){
            ThreadPoolReuse work = new ThreadPoolReuse();
            pool.execute(work.runnable);
        }
        System.out.println("4");
        try {
            Thread.sleep(5000);
            System.out.println("wait time");
            for (int i = 0; i < 5; i++){
                ThreadPoolReuse work = new ThreadPoolReuse();
                pool.execute(work.runnable);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

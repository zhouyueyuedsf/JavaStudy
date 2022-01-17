package lockandthread;

import utils.MyLog;

import java.util.function.Supplier;

public class MyThread extends Thread {

    static class TL1 {
        public static TL1 tl1 = new TL1();
        Session logThread = new Session();
        ThreadLocal<Session> sessions = ThreadLocal.withInitial(Session::new);

        public void test() {
            MyLog.INSTANCE.log("start access logThread " + logThread);
            MyLog.INSTANCE.log("start access sessions " + sessions.get());
        }
    }

    static class Session {

    }

    @Override
    public void run() {
        TL1.tl1.test();
        super.run();
        try {
            for (int i = 0; i < 500000; i++) {
                // check interrupted flag
                if (this.isInterrupted()) {
                    System.out.println("stop");
                    // exception method
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("run catch");
            // to do someThing
        }
    }

    public static void main(String[] args) {
        try {
            Thread.sleep(2000);
            MyThread thread = new MyThread();
            thread.start();
            // 停止线程，设置中断标志位
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
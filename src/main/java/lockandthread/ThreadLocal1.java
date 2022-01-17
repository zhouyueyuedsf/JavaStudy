package lockandthread;

import utils.MyLog;

public class ThreadLocal1 {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        Thread.sleep(1000);
        MyLog.INSTANCE.log(MyThread.TL1.tl1.logThread);
//        MyLog.INSTANCE.log(MyThread.TL1.tl1.sessions.get());

        MyThread myThread1 = new MyThread();
        myThread1.start();
        Thread.sleep(1000);
        MyLog.INSTANCE.log(MyThread.TL1.tl1.logThread);
//        MyLog.INSTANCE.log(MyThread.TL1.tl1.sessions.get());
    }
}

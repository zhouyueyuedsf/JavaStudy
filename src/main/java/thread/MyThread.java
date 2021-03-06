package thread;

public class MyThread extends Thread {
    @Override
    public void run() {
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
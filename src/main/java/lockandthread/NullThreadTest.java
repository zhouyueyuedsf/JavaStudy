package lockandthread;

public class NullThreadTest {
    private static String[] strings = {"1", "2", "3"};
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String[] strings1 = new String[3];
//                System.arraycopy(strings, 0, strings1, 0, 3);
                String[] strings1 = strings;
                try {
                    Thread.sleep(2000);
                    System.out.println("strings1 foreach");
                    for (String s : strings1) {
                        System.out.print(s);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
            System.out.println("strings = null");
            strings = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

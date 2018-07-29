package jvm;

/**
 * 可重入锁验证
 */
public class Sync {

    public void f1() {
        synchronized (Sync.class) {
            int i = 0;
            f2(i);
        }
    }

    public void f2(int i) {
        synchronized (Sync.class) {
            i++;
            System.out.println(i);
        }
    }


    public static void main(String[] args) {
        synchronized (Sync.class){
            Sync sync = new Sync();
            sync.f1();
        }
    }
}

package lockandthread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Lock1 {

    public static  ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public final Object object = new Object();
    public List<String> mList = new ArrayList<>();
    public void test1() {
        synchronized (object) {
            mList.add("");
            mList.notify();
        }
        synchronized (object) {
            try {
                if (mList.isEmpty()) {
                    mList.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test2() throws InterruptedException {
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
    private static final String TITLE_MISTAKE = "整理错题";
    private static final String TITLE_NOTE = "整理笔记";
    private static final String TITLE_HOMEWORK = "写作业";
    private static final String TITLE_WORDS = "背单词";
    private static final String TITLE_POEM = "背古诗";
    private static final String TITLE_RECITE = "朗读";
    private static final String TITLE_REVIEW = "复习";
    private static final String TITLE_INTEREST_CLASS = "兴趣班";

    private static final String TITLE_YU_WEN_CLASS = "语文课";
    private static final String TITLE_SHU_XUE_CLASS = "数学课";
    private static final String TITLE_YIN_YU_CLASS = "英语课";
    private static final String TITLE_TI_YU_CLASS = "体育课";
    private static final String TITLE_MEI_SHU_CLASS = "美术课";
    private static final String TITLE_LAO_DONG_CLASS = "劳动课";
    private static final CountDownLatch sCountDownLatch = new CountDownLatch(1);
    private static List<String> sList;
    public static List<String> getRecommendationList() {
        System.out.println("current Thread = " + Thread.currentThread());
        if (sList == null) {
            synchronized (Lock1.class) {
                if (sList == null) {
                    sList = new ArrayList<>();
                    try {
                        Thread.sleep((long) (Math.random() * 100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final List<String> all = new ArrayList<>(COURSE_SET);
                    all.addAll(RECOMMENDATION_SET);
                    sList.addAll(all);
                    sCountDownLatch.countDown();
                }
            }
        }

        try {
            // 如果mList在thread1中还没有添加完成的时候，thread2正好走到此处，是需要让thread2等待thread1线程添加完成的
            if (sList.size() != COURSE_SET.size() + RECOMMENDATION_SET.size()) {
                System.out.println("countDownLatch await");
                sCountDownLatch.await();
            }
        } catch (InterruptedException e) {
        }
        return sList;
    }

    public static final List<String> COURSE_SET = new ArrayList<>();
    public static final List<String> RECOMMENDATION_SET = new ArrayList<>();

    static {
        COURSE_SET.add(TITLE_YU_WEN_CLASS);
        COURSE_SET.add(TITLE_SHU_XUE_CLASS);
        COURSE_SET.add(TITLE_YIN_YU_CLASS);
        COURSE_SET.add(TITLE_TI_YU_CLASS);
        COURSE_SET.add(TITLE_MEI_SHU_CLASS);
        COURSE_SET.add(TITLE_LAO_DONG_CLASS);

        RECOMMENDATION_SET.add(TITLE_MISTAKE);
        RECOMMENDATION_SET.add(TITLE_NOTE);
        RECOMMENDATION_SET.add(TITLE_HOMEWORK);
        RECOMMENDATION_SET.add(TITLE_WORDS);
        RECOMMENDATION_SET.add(TITLE_POEM);
        RECOMMENDATION_SET.add(TITLE_RECITE);
        RECOMMENDATION_SET.add(TITLE_REVIEW);
        RECOMMENDATION_SET.add(TITLE_INTEREST_CLASS);
    }
    public static void main(String[] args)  {
        Lock1 lock1 = new Lock1();
        new Thread(() -> System.out.println("curThread = " + Thread.currentThread() + getRecommendationList())).start();
        new Thread(() -> System.out.println("curThread = " + Thread.currentThread() + getRecommendationList())).start();
        new Thread(() -> System.out.println("curThread = " + Thread.currentThread() + getRecommendationList())).start();
    }
}

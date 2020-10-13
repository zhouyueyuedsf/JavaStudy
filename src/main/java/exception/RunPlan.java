package exception;

import java.io.IOException;

public class RunPlan {
    public static int inc() throws Exception{
        int x;
        try {
            x = parseIntWithException("1.1");
            return x;
        } catch (Exception e){
            x = 2;
            e.printStackTrace();
//            throw e;
        } finally {
            x = 3;
        }
        return x;
    }

    public static int parseIntWithException(String is){
        return Integer.parseInt(is);
    }

    public static void f1() {
        f2();
    }

    private static void f2() {
        throw new IllegalArgumentException("");
    }


    private static void test1() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("throw ");
        }
    }

    public static void test2() {
        try {
            throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("exception" + e);
        } finally {
            System.out.println("exception finally");
        }
    }

    public static void test3() {
        try {
            throw new IllegalArgumentException();
        } catch (Exception e) {

        }
        System.out.println("ex");
    }

    public static void main(String[] args) {
//        try {
//            inc();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        test3();
    }
}

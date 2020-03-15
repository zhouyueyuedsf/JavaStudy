package exception;

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

    public static void main(String[] args) {
        try {
            f1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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
    public static void main(String[] args) {
        try {
            inc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package exception;

public class RunPlan {
    public static int inc() throws Exception{
        int x;
        try {
            x = Integer.parseInt("1.1");
            return x;
        } catch (Exception e){
            x = 2;
            throw e;
        } finally {
            x = 3;
        }
    }

    public static void main(String[] args) {
        try {
            inc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

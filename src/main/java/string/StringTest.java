package string;

public class StringTest {

    public static void main(String[] args) {
        String s1 = new String("123");
        String s2 = "123";
        System.out.println(s1 == s2);
        String s3 = "12345678";
        System.out.println(s3.length());
        System.out.println(s3.substring(0, 8));

        Integer integer1 = 1;
        Integer integer2 = 1;
        System.out.println(integer1 == integer2);
    }
}

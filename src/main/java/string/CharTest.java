package string;

public class CharTest {
    public static void main(String[] args) {
        String errorAr1 = "ٱﻟﺮَّﺣۡﻤَـٰﻦِ ٱﻟﺮَّﺣِﻴﻢِِ";
        String rightAr2 = "ٱلرَّحۡمَـٰنِ ٱلرَّحِيم";
        String ar3 = "你好中国";
        String ar4 = "你好中国";
        errorAr1 = CharConvertHelper.convert(errorAr1);
        printChar(errorAr1, rightAr2);
    }

    private static void printChar(String ar1, String ar2) {
        System.out.println("ar1 length = " + ar1.length() + " ");
        for (int i = 0; i < ar1.length(); i++) {
            int v = ar1.charAt(i);
            System.out.print("ar1 = " + ar1.charAt(i) + " unicode = " + v + " ");
        }
        System.out.println("");
        System.out.println("ar2 length = " + ar2.length() + " ");
        for (int i = 0; i < ar2.length(); i++) {
            int v = ar2.charAt(i);
            System.out.print("ar2 = " + ar2.charAt(i) + " unicode = " + v + " ");
        }
    }
}

package others;

import basic.InvokeByJavaKt;

public class InvokeKotlin {
    public static String test1() {
        return null;
    }
    public static void main(String[] args) {
        InvokeByJavaKt.testNullParamsInvoke(test1());
    }
}

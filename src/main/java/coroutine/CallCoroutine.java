package coroutine;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.annotations.NotNull;
import utils.MyLog;

/**
 * java调kotlin的协程
 */
public class CallCoroutine {

    public static void main(String[] args) {
        test1();
    }
    private static void test2() {
        Object value =  Suspend.INSTANCE.test2(new Continuation<Unit>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return null;
            }

            @Override
            public void resumeWith(@NotNull Object o) {

            }
        });
    }
    private static void test1() {
        Object value =  Suspend.INSTANCE.hello(new Continuation<Integer>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                if(o instanceof Integer){
                    handleResult(o);
                } else {
                    Throwable throwable = (Throwable) o;
                    throwable.printStackTrace();
                }
            }
        });

        if(value == IntrinsicsKt.getCOROUTINE_SUSPENDED()){
            MyLog.INSTANCE.log("Suspended.");
        } else {
            handleResult(value);
        }
    }

    public static void handleResult(Object o){
        MyLog.INSTANCE.log("The result is " + o);
    }
}

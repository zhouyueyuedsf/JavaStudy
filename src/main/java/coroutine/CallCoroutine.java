package coroutine;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.annotations.NotNull;
import utils.MyLog;

/**
 *
 */
public class CallCoroutine {

    public static void main(String[] args) {
        test3();
    }


    private static void test3() {
        RunSuspend runSuspend = new RunSuspend();
        ContinuationImpl continuation = new ContinuationImpl(runSuspend);
        continuation.resumeWith(Unit.INSTANCE);
        try {
            runSuspend.await();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static void test2() {
        Suspend.INSTANCE.test2(new Continuation<Unit>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                if (o instanceof String) {
                    handleResult(o);
                }
            }
        });
    }

    private static void test1() {
        Object value = Suspend.INSTANCE.creatToken(new Continuation<Integer>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                if (o instanceof Integer) {
                    handleResult(o);
                } else {
                    Throwable throwable = (Throwable) o;
                    throwable.printStackTrace();
                }
            }
        });

        if (value == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            MyLog.INSTANCE.log("Suspended.");
        } else {
            handleResult(value);
        }
    }

    public static void handleResult(Object o) {
        MyLog.INSTANCE.log("The result is " + o);
    }
}

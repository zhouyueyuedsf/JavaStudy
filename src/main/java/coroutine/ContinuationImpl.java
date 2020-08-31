package coroutine;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.annotations.NotNull;
import utils.MyLog;

public class ContinuationImpl implements Continuation<Object> {
    private int label = 0;
    private final Continuation<Unit> completion;

    public ContinuationImpl(Continuation<Unit> completion) {
        this.completion = completion;
    }

    @NotNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Override
    public void resumeWith(@NotNull Object o) {
        try {
            Object result = o;
            switch (label) {
                case 0: {
                    MyLog.INSTANCE.log(1);
                    result = Suspend.INSTANCE.createToken(this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 1: {
                    MyLog.INSTANCE.log(result);
                    MyLog.INSTANCE.log(2);
                    result = Suspend.INSTANCE.createPost((Token) result, this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 2: {
                    result = Suspend.INSTANCE.processPost(this);
                    MyLog.INSTANCE.log("returnImmediately");
                    label++;
                    if (isSuspended(result)) return;
                }
            }
            completion.resumeWith(result);
        } catch (java.lang.Exception e) {
            completion.resumeWith(e);
        }
    }

    private boolean isSuspended(Object result) {
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
}

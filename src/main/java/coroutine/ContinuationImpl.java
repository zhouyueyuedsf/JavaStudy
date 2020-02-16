package coroutine;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;
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
                    result = Suspend.INSTANCE.returnSuspended( this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 1: {
                    MyLog.INSTANCE.log(result);
                    MyLog.INSTANCE.log(2);
                        result = DelayKt.delay(1000, this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 2: {
                    MyLog.INSTANCE.log(3);
                    result = Suspend.INSTANCE.returnImmediately( this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 3:{
                    MyLog.INSTANCE.log(result);
                    MyLog.INSTANCE.log(4);
                }
            }
            completion.resumeWith(Unit.INSTANCE);
        } catch (Exception e) {
            completion.resumeWith(e);
        }
    }
    private boolean isSuspended(Object result) {
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
}

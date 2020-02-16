package coroutine;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;
import org.jetbrains.annotations.NotNull;
import utils.MyLog;

public class RunSuspend implements Continuation<Object> {
    private Object result;

    @NotNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Override
    public void resumeWith(@NotNull Object o) {
        synchronized (this) {
            this.result = result;
            notifyAll(); // 协程已经结束，通知下面的 wait() 方法停止阻塞
        }
    }

    public void await() throws Throwable {
        synchronized (this) {
            while (true) {
                Object result = this.result;
                if (result == null) wait(); // 调用了 Object.wait()，阻塞当前线程，在 notify 或者 notifyAll 调用时返回
                else if (result instanceof Throwable) {
                    throw (Throwable) result;
                } else return;
            }
        }
    }

    private boolean isSuspended(Object result) {
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
}

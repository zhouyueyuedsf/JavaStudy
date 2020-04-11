package coroutine;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CancellableContinuationImpl implements CancellableContinuation<Object> {
    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public boolean cancel(@Nullable Throwable throwable) {
        return false;
    }

    @Override
    public void completeResume(@NotNull Object o) {

    }

    @Override
    public void invokeOnCancellation(@NotNull Function1<? super Throwable, Unit> function1) {

    }

    @Override
    public void resume(Object o, @NotNull Function1<? super Throwable, Unit> function1) {

    }

    @Nullable
    @Override
    public Object tryResume(Object o, @Nullable Object o2) {
        return null;
    }

    @Nullable
    @Override
    public Object tryResumeWithException(@NotNull Throwable throwable) {
        return null;
    }

    @Override
    public void resumeUndispatched(@NotNull CoroutineDispatcher coroutineDispatcher, Object o) {

    }

    @Override
    public void resumeUndispatchedWithException(@NotNull CoroutineDispatcher coroutineDispatcher, @NotNull Throwable throwable) {

    }

    @NotNull
    @Override
    public CoroutineContext getContext() {
        return null;
    }

    @Override
    public void resumeWith(@NotNull Object o) {

    }
}

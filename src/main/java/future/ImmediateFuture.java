package future;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.*;

abstract class ImmediateFuture<V> implements ListenableFuture<V> {

    private static final String TAG = "ImmediateFuture";

    /**
     * Returns a future that contains a null value.
     *
     * <p>This should be used any time a null value is needed as it uses a static ListenableFuture
     * that contains null, and thus will not allocate.
     */
    public static <V> ListenableFuture<V> nullFuture() {
        @SuppressWarnings({"unchecked", "rawtypes"}) // Safe since null can be cast to any type
        ListenableFuture<V> typedNull = (ListenableFuture) ImmediateSuccessfulFuture.NULL_FUTURE;
        return typedNull;
    }

    @Override
    public void addListener(@NotNull Runnable listener, @NotNull Executor executor) {

        try {
            executor.execute(listener);
        } catch (RuntimeException e) {

        }

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    @Nullable
    public abstract V get() throws ExecutionException;

    @Override
    @Nullable
    public V get(long timeout, @NotNull TimeUnit unit) throws ExecutionException {
        return get();
    }

    static final class ImmediateSuccessfulFuture<V> extends ImmediateFuture<V> {

        static final ImmediateFuture<Object> NULL_FUTURE =
                new ImmediateSuccessfulFuture<>(null);

        @Nullable
        private final V mValue;

        ImmediateSuccessfulFuture(@Nullable V value) {
            mValue = value;
        }


        @Nullable
        @Override
        public V get() {
            return mValue;
        }

        @Override
        public String toString() {
            // Behaviour analogous to AbstractResolvableFuture#toString().
            return super.toString() + "[status=SUCCESS, result=[" + mValue + "]]";
        }
    }

    static class ImmediateFailedFuture<V> extends ImmediateFuture<V> {

        @NotNull
        private final Throwable mCause;

        ImmediateFailedFuture(@NotNull Throwable cause) {
            mCause = cause;
        }

        @Nullable
        @Override
        public V get() throws ExecutionException {
            throw new ExecutionException(mCause);
        }

        @Override
        @NotNull
        public String toString() {
            // Behaviour analogous to AbstractResolvableFuture#toString().
            return super.toString() + "[status=FAILURE, cause=[" + mCause + "]]";
        }
    }

    static final class ImmediateFailedScheduledFuture<V> extends ImmediateFailedFuture<V> implements
            ScheduledFuture<V> {

        ImmediateFailedScheduledFuture(@NotNull Throwable cause) {
            super(cause);
        }

        @Override
        public long getDelay(@NotNull TimeUnit timeUnit) {
            return 0;
        }

        @Override
        public int compareTo(@NotNull Delayed delayed) {
            return -1;
        }
    }
}

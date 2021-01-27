package future;

public class CameraX {
    public static void main(String[] args) {
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(e.getMessage());
            }
        });
        try {
            new Defaults();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    static CameraX sInstance = null;
    static CameraX checkInitialized() {
        return waitInitialized();
    }

    private static CameraX waitInitialized() {
        ListenableFuture<CameraX> future = getInstance();
        try {
            return future.get();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static ListenableFuture<CameraX> getInstance() {
        CameraX cameraX = sInstance;
        if (cameraX == null) {
            return new ImmediateFuture.ImmediateFailedFuture<>(new IllegalStateException("Must "
                    + "call CameraX.initialize() first"));
        }
        return null;
    }


}

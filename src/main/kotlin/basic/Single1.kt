package basic

class Single1 {
    // 1.分配内存
    // 2.对构造方法进行初始化
    // 3.将instance对象指向分配的内存空间 A,B,C线程依次进入该代码块，需要判断，A是否初始化了instance
    // 多个线程竞争锁，去初始化instance
    // 如果这里不做控制，每次都要进入synchronized，影响性能
    @Volatile
    var instance: Single1? = null
        get() {
            // 如果这里不做控制，每次都要进入synchronized，影响性能
            if (field == null) {
                // 多个线程竞争锁，去初始化instance
                synchronized(Single1::class.java) {
                    // A,B,C线程依次进入该代码块，需要判断，A是否初始化了instance
                    if (field == null) {
                        // 1. 分配内存 2.对构造方法进行初始化 3.将instance对象指向分配的内存空间
                        field = Single1()
                    }
                }
            }
            return field
        }
        private set

}
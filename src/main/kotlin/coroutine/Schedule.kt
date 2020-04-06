package coroutine

import coroutine.Schedule.test1
import coroutine.Schedule.test2
import coroutine.Schedule.test4
import coroutine.Schedule.test5
import coroutine.Schedule.test6
import coroutine.Schedule.test7
import kotlinx.coroutines.*
import utils.MyLog.log
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import kotlin.coroutines.*

/**
 * 返回结果的拦截器
 */
class MyContinuationInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>): MyContinuation<T> {
        return MyContinuation(continuation)
    }
}

class MyContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> {
    override val context = continuation.context
    override fun resumeWith(result: Result<T>) {
        log("<coroutine.MyContinuation> $result")
        continuation.resumeWith(result)
    }
}

object Schedule {
    suspend fun <T> CompletableFuture<T>.await(): T =
            suspendCoroutine<T> { cont ->
                whenComplete { result, exception ->
                    if (exception == null) {
                        cont.resume(result)
                    } else {
                        cont.resumeWithException(exception)
                    }
                }
            }

    suspend fun test1() {
        GlobalScope.launch(start = CoroutineStart.DEFAULT, context = MyContinuationInterceptor()) {
            // thread1
            log(1)
            // 这里会有线程切换
            val deferred = async(context = MyContinuationInterceptor()) {
                // thread2
                log(2)
                delay(1000)
                // 这里可能会有线程切换
                log(3)
                "Hello"
            }
            // thread1
            log(4)
            val result = deferred.await()
            log("5. $result")
        }.join()
        log(6)
    }

    /**
     * 多个线程运行时，挂起后的resume都会有线程切换
     */
    suspend fun test2() {
        Executors.newScheduledThreadPool(10)
                .asCoroutineDispatcher().use { dispatcher ->
                    GlobalScope.launch(dispatcher) {
                        log(1)
                        val job = async {
                            log(2)
                            delay(1000)
                            log(3)
                            "Hello"
                        }
                        log(4)
                        val result = job.await()
                        log("5. $result")
                    }.join()
                    log(6)
                }
    }

    suspend fun test3() {
        coroutineScope {
            launch(newSingleThreadContext("")) {

            }
        }
    }

    suspend fun test4() {
        coroutineScope {
            val job = GlobalScope.async {
                log(1)
                delay(5000)
                log(2)
            }
            job.await()
            log(3)
        }
        log(4)
    }

    suspend fun test5() {
        coroutineScope {
            launch(start = CoroutineStart.DEFAULT, context = Dispatchers.Default) {
                log(1)
                val job = launch {
                    log(2)
                    delay(1000)
                    log(3)
                    "Hello"
                }
                log(4)
                val result = job.join()
                log("5. $result")
            }
        }
    }

    suspend fun test6() {
        Executors.newScheduledThreadPool(10)
                .asCoroutineDispatcher().use { dispatcher ->
                    GlobalScope.launch(dispatcher) {
                        log(1)
                        val job = launch {
                            log(2)
                            delay(1000)
                            log(3)
                            "Hello"
                        }
                        log(4)
                        val result = job.join()
                        log("5. $result")
                    }.join()
                    log(6)
                }
    }

    suspend fun test7() {
        GlobalScope.launch(newSingleThreadContext("Dispather")) {
            log(1)
            val job = launch {
                log(2)
                delay(1000)
                log(3)
                "Hello"
            }
            log(4)
            val result = job.join()
            log("5. $result")
        }.join()
        log(6)
    }
}

fun main() = runBlocking {
    test1()
    log("end")
}
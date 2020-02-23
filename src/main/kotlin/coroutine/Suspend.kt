package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.invokeOnCompletion
import org.apache.tools.ant.taskdefs.Execute.launch
import utils.MyLog.log
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

object Suspend {
    suspend fun test1() {
        log("start")
        val job1 = GlobalScope.launch {
            // ①
            log(1)
            // 协程挂起1秒
            try {
                delay(1000) // ②
            } catch (e: Exception) {
                log("cancelled. $e")
            }
            log(2)
        }
        delay(100)
        log(3)
        job1.cancel()
        job1.join()
        log(4)
    }

    suspend fun hello() = suspendCoroutineUninterceptedOrReturn<Int> { continuation ->
        log(1)
        thread {
            Thread.sleep(1000)
            log(2)
            continuation.resume(1024)
        }
        log(3)
        COROUTINE_SUSPENDED
    }

    suspend fun returnSuspended() = suspendCoroutineUninterceptedOrReturn<String> { continuation ->
        thread {
            Thread.sleep(1000)
            continuation.resume("Return suspended.")
        }
        COROUTINE_SUSPENDED
    }

    suspend fun returnImmediately() = suspendCoroutineUninterceptedOrReturn<String> {
        log(1)
        "Return immediately."
    }

    suspend fun test2() {
        log(1)
        log(returnSuspended())
        log(2)
        delay(1000)
        log(3)
        log(returnImmediately())
        log(4)
    }

    suspend fun test3() {
        GlobalScope.launch {
            delay(200L)
            log("Task from runBlocking")
        }
        coroutineScope {
            // 创建⼀个协程作⽤域
            launch {
                delay(500L)
                log("Task from nested launch")
            }
            delay(100L)
            log("Task from coroutine scope") // 这⼀⾏会在内嵌 launch 之前输出
        }
        // 这里是在runBlock中，阻塞了
        log("Coroutine scope is over") // 这⼀⾏在内嵌 launch 执⾏完毕后才输出
    }

    /**
     * 比较test4和test5的区别, 主要是在线程上的区别
     */
    suspend fun test4() {
        coroutineScope {
            launch { // launch a new coroutine in the scope of runBlocking
                delay(1000L)
                log("World!")
            }
            log("Hello,")
        }
    }

    suspend fun test5() {
        coroutineScope {
            GlobalScope.launch { // launch a new coroutine in the scope of runBlocking
                delay(1000L)
                log("World!")
            }
            log("Hello,")
        }
    }
}

fun main() = runBlocking {
    repeat(100_000) { // launch a lot of coroutines
        launch {
            delay(1000L)
            log(".")
        }
    }
}





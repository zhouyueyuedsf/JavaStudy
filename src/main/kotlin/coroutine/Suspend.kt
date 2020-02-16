package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.invokeOnCompletion
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

    suspend fun returnSuspended() = suspendCoroutineUninterceptedOrReturn<String>{
        continuation ->
        thread {
            Thread.sleep(1000)
            continuation.resume("Return suspended.")
        }
        COROUTINE_SUSPENDED
    }

    suspend fun returnImmediately() = suspendCoroutineUninterceptedOrReturn<String>{
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
}

fun main() = runBlocking {
    log(Suspend.test2())
}



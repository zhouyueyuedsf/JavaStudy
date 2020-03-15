package coroutine

import coroutine.Suspend.test1
import coroutine.Suspend.test6
import coroutine.Suspend.test7
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.invokeOnCompletion
import org.apache.tools.ant.taskdefs.Execute
import org.apache.tools.ant.taskdefs.Execute.launch
import utils.MyLog.log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

object Suspend {
    suspend fun test1() {
        log("start")
        coroutineScope {
            val job1 = launch {
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
            launch {
                // launch a new coroutine in the scope of runBlocking
                delay(1000L)
                log("World!")
            }
            log("Hello,")
        }
    }

    suspend fun test5() {
        coroutineScope {
            GlobalScope.launch {
                // launch a new coroutine in the scope of runBlocking
                delay(1000L)
                log("World!")
            }
            log("Hello,")
        }
    }

    suspend fun doSomethingUsefulOne(): Int {
//        delay(1000L)
        val file = File("E:\\YueyueProjects\\hindict_android.rar")
        val bufferedReader = BufferedReader(FileReader(file))
        while (true) {
            bufferedReader.readLine() ?: break
        }
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
//        delay(1000L)
        val file = File("E:\\YueyueProjects\\hindict_android.rar")
        val bufferedReader = BufferedReader(FileReader(file))
        while (true) {
            bufferedReader.readLine() ?: break
        }
        return 29
    }

    /**
     * 并发等待测试
     */
    suspend fun test6() {
        coroutineScope {
            val one = async {
                val res = doSomethingUsefulOne()
//                log(res)
//                var i = 0
//                while (i < 100) {
//                    log(++i)
//                }
                res
            }
            val two = async {
                val res = doSomethingUsefulTwo()
//                log(res)
//                var i = 0
//                while (i < 100) {
//                    log(++i)
//                }
                res
            }
            log("The answer is ${one.await() + two.await()}")
        }
    }

    suspend fun test7() {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        log("The answer is ${one + two}")
    }
}

@ExperimentalTime
fun main() = runBlocking {
    test1()
}





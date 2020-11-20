package coroutine


import kotlinx.coroutines.*
import utils.MyLog.log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.Exception
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime

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

    suspend fun createToken() = suspendCoroutineUninterceptedOrReturn<Token> { continuation ->
        log("createToken start")
        thread {
            Thread.sleep(1000)
            log("createToken return")
            continuation.resume(Token())
        }
        log("createToken suspend")
        COROUTINE_SUSPENDED
    }


    suspend fun createPost(token: Token) = suspendCoroutineUninterceptedOrReturn<Post> { continuation ->
        log("createPost start")
        thread {
            Thread.sleep(1000)
            log("createPost return")
            continuation.resume(Post())
        }
        log("createPost suspend")
        COROUTINE_SUSPENDED
    }


    suspend fun returnSuspended() = suspendCoroutineUninterceptedOrReturn<String> { continuation ->
        thread {
            Thread.sleep(1000)
            continuation.resume("Return suspended.")
        }
        COROUTINE_SUSPENDED
    }

    suspend fun processPost() = suspendCoroutineUninterceptedOrReturn<String> {
        log("processPost start")
        "Return immediately."
    }

    suspend fun test2() {
        log(1)
        log(returnSuspended())
        log(2)
//        delay(1000)
        log(3)
        log(processPost())
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

    fun doTaskOne() {
        val file = File("E:\\YueyueProjects\\YDLogManager.kt")
        val bufferedReader = BufferedReader(FileReader(file))
        while (true) {
            bufferedReader.readLine() ?: break
        }
    }

    fun doTaskTwo() {
        val file = File("E:\\YueyueProjects\\YDLogManager.kt")
        val bufferedReader = BufferedReader(FileReader(file))
        while (true) {
            bufferedReader.readLine() ?: break
        }
    }

    /**
     * 并发等待测试
     */
    suspend fun test6() {
        val totalTime = measureTimeMillis {
            coroutineScope {
                launch(Dispatchers.Default) {
                    val oneTask = async {
                        log("start do Task1")
                        val res = measureTimeMillis { doTaskOne() }
                        res
                    }
                    val twoTask = async {
                        log("start do Task2")
                        val res = measureTimeMillis { doTaskTwo() }
                        res
                    }
                    log("The answer is ${oneTask.await() + twoTask.await()}")
                }
            }
        }
        log("The totalTime is $totalTime")
    }

    suspend fun test7() {
        val totalTime = measureTimeMillis {
            coroutineScope {
                val oneTask = async(newSingleThreadContext("dispather-1")) {
                    log("start do Task1")
                    val res = measureTimeMillis { doTaskOne() }
                    log("end do Task1")
                    res
                }
                val twoTask = async(newSingleThreadContext("dispather-2")) {
                    log("start do Task2")
                    val res = measureTimeMillis { doTaskTwo() }
                    log("end do Task2")
                    res
                }
                log("The answer is ${oneTask.await() + twoTask.await()}")
            }
        }
        log("The totalTime is $totalTime")
    }

    suspend fun test10() {
        coroutineScope {
            val oneTask = async(newSingleThreadContext("dispather-1")) {
                log("start do Task1")
                val res = doTaskOne()
                log("end do Task1")
                res
            }
            val twoTask = async(newSingleThreadContext("dispather-2")) {
                log("start do Task2")
                val res = doTaskTwo()
                log("end do Task2")
                res
            }
            oneTask.await()
            twoTask.await()
            // Task3
            (withContext(newSingleThreadContext("dispather-3")) {
                delay(1000)
                log("end task")
            })
        }
    }

    suspend fun test11() {
        coroutineScope {
            newSingleThreadContext("dispather-1").use { dispather1 ->
                newSingleThreadContext("dispather-2").use { dispather2 ->
                    val oneTask = async(dispather1) {
                        log("start do Task1")
                        val res = doTaskOne()
                        log("end do Task1")
                        delay(2000)
                        res
                    }
                    val twoTask = async(dispather2) {
                        log("start do Task2")
                        val res = doTaskTwo()
                        log("end do Task2")
                        res
                    }
                    oneTask.await()
                    twoTask.await()
                    // Task3
                    (withContext(dispather2) {
                        delay(1000)
                        log("end task")
                    })
                }
            }
        }
    }

    suspend fun test12() {
        coroutineScope {
            launch(Dispatchers.IO) {
                val oneTask = async {
                    log("start do Task1")
                    val res = doTaskOne()
                    log("end do Task1")
                    res
                }
                val twoTask = async {
                    log("start do Task2")
                    val res = doTaskTwo()
                    log("end do Task2")
                    res
                }
                oneTask.await()
                twoTask.await()
                // Task3
                (async {
                    delay(1000)
                    log("end task")
                }.await())
            }
        }
    }
}


/**
 * 回调变挂起
 */
suspend fun test8() =
        suspendCoroutine<String> {
            it.resume("test8")
        }


fun test9(callback: (String) -> Unit) {
    callback.invoke("test9")
}

suspend fun test10() {
    coroutineScope {
        launch(Dispatchers.IO) {

        }
    }
}

suspend fun test11() {
    coroutineScope {
        log(3)
    }
    log(2)
}

/**
 * test10: 11042
 * test11: 11038
 * test12: 11048
 */
@ExperimentalTime
fun main() = runBlocking {
    log(test11())
    log("end")
}





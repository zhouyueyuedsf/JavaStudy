package coroutine

import coroutine.Cancel.test1
import coroutine.Cancel.test2
import coroutine.Cancel.test3
import kotlinx.coroutines.*
import utils.MyLog.log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object Cancel {
    suspend fun test1() {
        supervisorScope {  }
        coroutineScope {
            val job = launch(context = MyContinuationInterceptor()) {
                try {
                    repeat(100000) { i ->
                        log("job: I'm sleeping $i ...")
                        // 这里并没有计算任务，只是挂起了，所以协程是可以取消的
                        delay(500L)
                    }
                } catch (e: Throwable) {
                    log(e)
                } finally {
                    log("job: I'm running finally")
                }
            }
            delay(1300L) // 延迟一段时间
            log("main: I'm tired of waiting!")
            job.cancelAndJoin() // 取消该作业并且等待它结束
            log("main: Now I can quit.")
        }
    }

    suspend fun test2() {
        coroutineScope {
            val job = launch(Dispatchers.Default) {
                log("1")
                val file = File("E:\\Opera_64.0.3417.54_Setup_x64.exe")
                val bufferedReader = BufferedReader(FileReader(file))
                while (isActive) {
                    bufferedReader.readLine() ?: break
                }
                log("2")
            }
            delay(100L) // 等待一段时间
            job.cancelAndJoin()
            log("3")
        }
    }

    suspend fun test3() {
        coroutineScope {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (isActive && i < 5) { // 一个执行计算的循环，只是为了占用 CPU
                    // 每秒打印消息两次
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        log("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1300L) // 等待一段时间（等待执行launch协程体，其次验证不可取消的协程）
            log("main: I'm tired of waiting!")
            job.cancelAndJoin() // 取消一个作业并且等待它结束
            log("main: Now I can quit.")
        }
    }

    suspend fun test4() = suspendCancellableCoroutine<String> { cont ->
        // 定义一个取消回调事件
        cont.invokeOnCancellation {

        }
    }
}

fun main(args: Array<String>) = runBlocking {
    test2()
    log("end")
}
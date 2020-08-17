package coroutine

import coroutine.Cancel.test1
import coroutine.Cancel.test1_1
import coroutine.Cancel.test1_2
import coroutine.Cancel.test1_3
import coroutine.Cancel.test1_4
import coroutine.Cancel.test1_5
import coroutine.Cancel.test2
import coroutine.Cancel.test3
import coroutine.Cancel.test4
import coroutine.Cancel.test5
import kotlinx.coroutines.*
import utils.MyLog.log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.Exception
import kotlin.IllegalArgumentException

object Cancel {

    @ExperimentalStdlibApi
    suspend fun test1_5() {
        val job = Job()
        val supervisorJob = SupervisorJob()
        val scope = CoroutineScope(supervisorJob)
        scope.launch {
            delay(500)
            log("Child 1 context Job = " + this.coroutineContext[Job])
            throw IllegalArgumentException()
        }
        scope.launch {
            // Child 2
            delay(1000)
            log("Child 2 context Job = " + this.coroutineContext[Job])
        }
        delay(5000)
    }

    @ExperimentalStdlibApi
    suspend fun test1_4() {
        val job = Job()
        val supervisorJob = SupervisorJob()
        val scope = CoroutineScope(job)
        log("Job = $job and $supervisorJob")
        try {
            coroutineScope {
                // new coroutine -> can suspend
                log("main context Job = " + this.coroutineContext[Job])
                launch {
                    // Child 1
                    delay(500)
                    log("Child 1 context Job = " + this.coroutineContext[Job])
                    throw IllegalArgumentException()
                }
                launch {
                    // Child 2
                    delay(1000)
                    log("Child 2 context Job = " + this.coroutineContext[Job])
                }
            }.join()
        } catch (e: Exception) {
            log("exception = $e")
        }
    }

    @ExperimentalStdlibApi
    suspend fun test1_3() {
        coroutineScope {
            log("main context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
            log("main context Job = " + this.coroutineContext[Job])
            log("main context CoroutineName = " + this.coroutineContext[CoroutineName])
            log("main context CoroutineExceptionHandler = " + this.coroutineContext[CoroutineExceptionHandler])
            launch(Dispatchers.IO + CoroutineExceptionHandler { _, _ -> } + CoroutineName("test1_3 coroutine")) {
                log("child context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
                log("child context Job = " + this.coroutineContext[Job])
                log("child context CoroutineName = " + this.coroutineContext[CoroutineName])
                log("child context CoroutineExceptionHandler = " + this.coroutineContext[CoroutineExceptionHandler])
            }
        }
    }


    @ExperimentalStdlibApi
    suspend fun test1_2() {
        coroutineScope {
            log("main context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
            log("main context Job = " + this.coroutineContext[Job])
            log("main context CoroutineName = " + this.coroutineContext[CoroutineName])
            log("main context CoroutineExceptionHandler = " + this.coroutineContext[CoroutineExceptionHandler])
            launch {
                log("child context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
                log("child context Job = " + this.coroutineContext[Job])
                log("child context CoroutineName = " + this.coroutineContext[CoroutineName])
                log("child context CoroutineExceptionHandler = " + this.coroutineContext[CoroutineExceptionHandler])
            }
        }
    }

    @ExperimentalStdlibApi
    suspend fun test1_1() {
        coroutineScope {
            log("main context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
            val scope = CoroutineScope(Job() + this.coroutineContext)
            scope.launch(Dispatchers.Default) parent@{
                log("parent context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
                log("parent context Job = " + this.coroutineContext[Job])
                scope.launch (Dispatchers.IO) {
                    log("child context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
                    log("child context Job = " + this.coroutineContext[Job])
                }.join()
                log("parent context Dispatcher = " + this.coroutineContext[CoroutineDispatcher])
                log("parent context Job = " + this.coroutineContext[Job])
            }.join()
        }
    }

    @ExperimentalStdlibApi
    suspend fun test1() {
//        supervisorScope {
//
//        }
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            scope.launch(Dispatchers.IO) {
                log("context = " + this.coroutineContext[CoroutineDispatcher])
            }
        }
//        coroutineScope {
//            scope.launch {
//                log("context = " + this@coroutineScope.coroutineContext[CoroutineDispatcher])
//                log("context = ${this.coroutineContext[CoroutineDispatcher]}")
//            }
//        }

//        coroutineScope {
//            val job = launch(context = Dispatchers.IO + CoroutineName("child")) {
//                try {
//                    log("context = " + this@coroutineScope.coroutineContext[CoroutineDispatcher])
//                    log("context = ${this.coroutineContext[CoroutineDispatcher]}")
//                    repeat(100000) { i ->
//                        log("job: I'm sleeping $i ...")
//                        // 这里并没有计算任务，只是挂起了，所以协程是可以取消的
//                        delay(500L)
//                    }
//                } catch (e: Throwable) {
//                    log(e)
//                } finally {
//                    log("job: I'm running finally")
//                }
//            }
//            delay(1300L) // 延迟一段时间
//            log("main: I'm tired of waiting!")
//            job.cancelAndJoin() // 取消该作业并且等待它结束
//            log("main: Now I can quit.")
//        }
    }

    suspend fun readFile() = suspendCancellableCoroutine<Unit> { cont ->
        var existed = true
        cont.invokeOnCancellation {
            log("start cancel")
            existed = false
        }
        val file = File("E:\\Opera_64.0.3417.54_Setup_x64.exe")
        val bufferedReader = BufferedReader(FileReader(file))
        while (existed) {
            log(1)
            bufferedReader.readLine() ?: break
        }
        log("cancelled")
    }

    suspend fun test5() {
        coroutineScope {
            val job = launch {
                readFile()
            }
            delay(100L)
            job.cancel()
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
            log("invoke cancel")
        }
        mCont = cont
//        cont.cancel()
    }
}

var mCont: CancellableContinuation<String>? = null

@ExperimentalStdlibApi
fun main(args: Array<String>) = runBlocking(CoroutineName("Main")) {
    test1_5()
    log("end")
}
package coroutine

import coroutine.Exception.test12
import coroutine.Exception.test13
import coroutine.Exception.test14
import coroutine.Exception.test15
import coroutine.Exception.test8
import coroutine.Exception.test9_1
import kotlinx.coroutines.*
import utils.MyLog.log
import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.concurrent.TimeoutException
import kotlin.Exception

object Exception {
    @ObsoleteCoroutinesApi
    suspend fun test1() {
        try {
            coroutineScope {
                launch(newSingleThreadContext("dispather-1")) {
                    log(1)
                    throw AssertionError()
                }
            }
        } catch (e: AssertionError) {
            log(e)
        }
    }

    // 异常传播
    suspend fun test2() {
        try {
            coroutineScope {
                try {
                    launch {
                        launch {
                            try {
                                coroutineScope {
                                    launch(newSingleThreadContext("dispatcher")) {
                                        //                                    try {
                                        log(1)
                                        throw IOException("")
//                                    } catch (e: Throwable) {
//                                        log("1 level handle $e")
//                                        throw e
//                                    }
                                    }.join()
                                }
                            } catch (e: Throwable) {
                                log("2 level handle $e")
                            }
                        }
                    }
                } catch (e: Throwable) {
                    log("3 $e")
                }
            }
        } catch (e: Throwable) {
            log("4 $e")
        }
    }

    suspend fun test3() {
        supervisorScope {
            val child = launch {
                try {
                    log("Child throws an exception")
                    throw AssertionError()
                } catch (e: Throwable) {
                    log(e)
                }
            }
            log("Scope is completing")
        }
        log("Scope is completed")
    }

    suspend fun test4() {
        coroutineScope {
            try {
                test6()
            } catch (e: Throwable) {
                log("$e suppressed" + e.suppressed?.contentToString())
            }
        }
    }

    suspend fun test5() {
        coroutineScope {
            test2()
        }
    }

    suspend fun test6() {
        try {
            coroutineScope {
                launch {
                    launch {
                        try {
                            delay(1000)
                            launch(newSingleThreadContext("dispatcher")) {
                                log(1)
                                delay(300)
                                throw IOException()
                            }
                        } finally {
                            throw IOException()
                        }
                    }
                }
                launch {
                    log(2)
                    delay(100)
                    throw AssertionError()
                }
            }
        } catch (e: Throwable) {

        }

    }

    suspend fun test7() {
        try {
            coroutineScope {
                launch {
                    launch {
                        launch(newSingleThreadContext("dispatcher-1")) {
                            log(1)
                            throw IOException()
                        }
                    }
                }
                launch(newSingleThreadContext("dispatcher-2")) {
                    log(2)
                    throw AssertionError()
                }
            }
        } catch (e: Throwable) {
            log(e)
        }
    }

    suspend fun test8() {
        val handler = CoroutineExceptionHandler { coroutinContext, exception ->
            log(" Caught $exception")
        }
        coroutineScope {
            log(this.coroutineContext)
            GlobalScope.launch(handler) {
                try {
                    withTimeout(1000) {
                        delay(2000)
                    }
                } catch (e: Throwable) {
                    log(e)
                    throw IOException()
                }
            }.join()

            GlobalScope.async(handler) {
                throw AssertionError()
            }.join()
        }
    }

    suspend fun test9() {
        val handler = CoroutineExceptionHandler { coroutinContext, exception ->
            log("$coroutinContext Caught $exception")
        }
        GlobalScope.launch(handler) {
            test9_1()
        }.join()
    }

    suspend fun test9_1() {
        coroutineScope {
            try {
                launch(newSingleThreadContext("")) {
                    throw IOException()
                }
            } catch (e: Throwable) {
                log(e)
            }

        }
    }

    suspend fun test10() {
        log(1)
        try {
            coroutineScope {
                //①
                log(2)
                launch {
                    // ②
                    log(3)
                    try {
                        launch {
                            // ③
                            log(4)
                            delay(100)
                            throw ArithmeticException("Hey!!")
                        }.join()
                    } catch (e: Throwable) {
                        log(e)
                    }

                    log(5)
                }
                log(6)
                val job = launch {
                    // ④
                    log(7)
                    delay(1000)
                }
                try {
                    log(8)
                    job.join()
                    log("9")
                } catch (e: Throwable) {
                    log("10. $e")
                }
            }
            log(11)
        } catch (e: Throwable) {
            log("12. $e")
        }
        log(13)
    }

    // 异常传播
    suspend fun test11() {
        try {
            coroutineScope {
                launch(CoroutineName("2 level")) {
                    try {
                        // 声明在2 level的管理范围里，遇到问题可以自己处理，也可以反馈给我处理
                        coroutineScope {
                            // 反馈问题给上级处理,但是上级不会直接取消所有任务
                            try {
                                withContext(newSingleThreadContext("dispatcher-1")) {
                                    log("dispatcher-1")
                                    throw IOException("dispatcher-1")
                                }
                            } catch (e: Throwable) {
                                log("${this.coroutineContext[CoroutineName]} handle this $e")
                            }
                            // 反馈问题给上级处理,处理后上级直接取消所有任务
                            launch(newSingleThreadContext("dispatcher-2")) {
                                log("dispatcher-2")
                                delay(1000)
                                throw IOException("dispatcher-2")
                            }
                        }
                    } catch (e: Throwable) {
                        log("${this.coroutineContext[CoroutineName]} handle this $e")
                    }
                }
            }
        } catch (e: Throwable) {
            log("4 $e")
        }
    }

    suspend fun test12() {
        log(1)
        try {
            coroutineScope {// 1
                log(2)
                launch() {// 2
                    log(3)
                    launch() {// 3
                        log(4)
                        delay(100)
                        throw IOException("Hey!!")
                    }
                    log(5)
                }
                log(6)
                val job = launch {// 4
                    log(7)
                    delay(1000)
                }
                try {
                    log(8)
                    job.join()
                    log("9")
                } catch (e: Throwable) {
                    log("10. $e")
                }
            }
            log(11)
        } catch (e: Throwable) {
            log("12. $e")
        }
        log(13)
    }

    suspend fun test13() {
        log(1)
        try {
            supervisorScope {
                try {
                    launch { // ③
                        log(2)
                        delay(100)
                        throw IOException("Hey!!")
                    }
                } catch (e: Throwable) {
                    log(e)
                }
            }
            log(3)
        } catch (e: Throwable) {
            log("4. $e")
        }
        log(5)
    }

    suspend fun test14() {
        coroutineScope {
            try {
                withContext(Dispatchers.IO) {
                    withTimeout(100) {
                        delay(1000)
                    }
                }
            } catch (e: Exception) {
                log(e)
            }
        }
    }

    suspend fun test15() {
        val handler = CoroutineExceptionHandler { coroutinContext, exception ->
            log("handler exception $exception")
        }
        val scope = CoroutineScope(handler)
        scope.launch {
            val deferred = async {
                delay(1000)
                throw IllegalStateException()
            }
            try {
                deferred.await()
            } catch (e: Exception) {
                log("catch a exception $e")
            }
        }.join()
    }
}

fun main() = runBlocking {
    test15()
    log("end")
}
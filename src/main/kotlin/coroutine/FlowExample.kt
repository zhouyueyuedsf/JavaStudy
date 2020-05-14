package coroutine

import coroutine.FlowExample.test1
import coroutine.FlowExample.test10
import coroutine.FlowExample.test11
import coroutine.FlowExample.test2
import coroutine.FlowExample.test3
import coroutine.FlowExample.test4
import coroutine.FlowExample.test6
import coroutine.FlowExample.test7
import coroutine.FlowExample.test8
import coroutine.FlowExample.test9
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import utils.MyLog
import utils.MyLog.log

object FlowExample {

    @ExperimentalCoroutinesApi
    // 流构建器
    fun test1(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // 假装我们在这里做了一些有用的事情
            emit(i) // 发送下一个值
        }
    }

    /**
     * 可以理解为懒序列
     */
    fun test2(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们正在计算
            log("test2 $i")
            yield(i) // 产生下一个值
        }
    }

    suspend fun test3() {
        coroutineScope {
            flowOf(1, 2, 3).collect {

            }
            (1..3).asFlow().map {
                // 处理请求
                "$it"
            }.collect {

            }
            (1..3).asFlow().transform { req ->
                // 可以发射多次值
                emit("$req")
                emit("$req * 2")
            }.map {
                log("transfrom map")
                it
            }.catch {
                log("catch")
            }.flowOn(Dispatchers.IO).collect {
                log("collect $it")
            }
            // 末端操作符
            (1..5).asFlow().map {
                // 处理请求
                it
            }.reduce { a, b ->
                log("a = $a b = $b")
                a + b
            }
        }
    }

    suspend fun test4() {
        coroutineScope {
            (1..4).asFlow().map {
                log("$it")
                it
            }.flowOn(Dispatchers.Default)
                    .collect {
                        log("collect $it")
                    }
        }
    }

    suspend fun test5() {
        coroutineScope {
            test1().buffer().collect {

            }
        }
    }

    suspend fun test6() {
        listOf(1, 2, 3).asFlow().filter {
            it != 2
        }.collect {
            log("collect $it")
        }
    }

    suspend fun test7() {
        (1..3).asFlow().transform { req ->
            // 可以发射多次值
            emit("$req")
            emit("${req * 2}")
        }.collect {
            log("collect $it")
        }
    }

    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500) // 等待 500 毫秒
        emit("$i: Second")
    }

    suspend fun test8() {
        (1..3).asFlow().flatMapConcat {
            requestFlow(it)
        }.collect {
            log(it)
        }
    }

    suspend fun test9() {
        (1..6).asFlow().take(3)
                .flatMapConcat {
                    requestFlow(it)
                }.collect {
                    log(it)
                }
    }

    suspend fun test10() {
        (1..6).asFlow().takeWhile {
            it < 3
        }.flatMapConcat {
                    requestFlow(it)
                }.collect {
                    log(it)
                }
    }

    suspend fun test11() {
        (1..6).asFlow().dropWhile {
            it > 3
        }.flatMapConcat {
            requestFlow(it)
        }.collect {
            log(it)
        }
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun main() = runBlocking {
    //    // 启动并发的协程以验证主线程并未阻塞
//    launch {
//        for (k in 1..3) {
//            MyLog.log("I'm not blocked $k")R
//            delay(100)
//        }
//    }
////
////    MyLog.log("end")

    test11()

//    val sequence = test2()
//    sequence.take(2).forEach { value -> log(value) }
//    sequence.take(3).forEach { value -> log(value) }
    log("end")
}
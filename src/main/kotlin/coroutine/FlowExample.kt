package coroutine

import coroutine.FlowExample.test1
import coroutine.FlowExample.test2
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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
            yield(i) // 产生下一个值
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

//    test1().collect { value -> log(value) }

    val sequence = test2()
    sequence.take(2).forEach { value -> log(value) }
    sequence.take(3).forEach { value -> log(value) }
}
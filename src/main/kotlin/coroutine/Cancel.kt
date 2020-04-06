package coroutine

import kotlinx.coroutines.*
import utils.MyLog.log

object Cancel {
    suspend fun test1() {
        coroutineScope {
            val job = launch {
                try {
                    repeat(1000) { i ->
                        log("job: I'm sleeping $i ...")
                        delay(500L)
                    }
                } catch(e: Throwable) {
                    log(e)
                } finally {
                    log("job: I'm running finally")
                }
            }
            log(1300L) // 延迟一段时间
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // 取消该作业并且等待它结束
            println("main: Now I can quit.")
        }
    }
}

fun main(args: Array<String>) = runBlocking {
    log("end")
}
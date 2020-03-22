package coroutine

import coroutine.Launch.test1
import coroutine.Launch.test2
import coroutine.Launch.test4
import kotlinx.coroutines.*
import utils.MyLog.log


object Launch  {
    suspend fun test1() {
        log(1)
        val job = GlobalScope.launch(context = Dispatchers.IO, start = CoroutineStart.DEFAULT) {
            log(2)
            delay(1000)
            log(3)
        }
        job.cancel()
        log(4)
        job.join()
    }

    suspend fun test3() {
        log(1)
        val job = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
            log(2)
            delay(1000)
            log(3)
        }
        job.cancel()
        log(4)
        job.join()
    }

    suspend fun test2() {
        log(1)
        val job = GlobalScope.launch(start = CoroutineStart.DEFAULT) {
            log(2)
            delay(1000)
            log(3)
        }
        log(4)
        job.join()
        log(5)
    }

    suspend fun test4() {
        log(1)
        val job = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            log(2)
            delay(1000)
            log(3)
        }
        log(4)
        job.join()
        log(5)
    }
}

fun main() = runBlocking {
    test4()
    log("end")
}

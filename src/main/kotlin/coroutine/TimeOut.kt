package coroutine

import coroutine.TimeOut.test1
import kotlinx.coroutines.*
import org.apache.tools.ant.taskdefs.Sleep
import utils.MyLog

object TimeOut {
    fun timeOutFunction1() {
        MyLog.log("timeOutFunction1 run")
        Thread.sleep(20000)
    }

    suspend fun test1() {
        MyLog.log("test1 start")
        val timeOut = runCatching {
            MyLog.log("test1 runCatching")
            val d = GlobalScope.async {
                timeOutFunction1()
            }
            withTimeout(1000L) {
                d.await()
            }
        }.getOrElse {
            MyLog.log("test1  $it")
        }
        MyLog.log("test1  $timeOut")
    }
}


fun main() = runBlocking {
    test1()
    delay(50_5000)
}
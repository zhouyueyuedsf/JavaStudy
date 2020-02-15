import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import utils.MyLog.log
import java.lang.Exception

fun main() = runBlocking {
    log("start")
    val job1 = GlobalScope.launch {
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
    log(4)
}
import kotlinx.coroutines.*
import utils.MyLog.log
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class MyContinuationInterceptor: ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>) = MyContinuation(continuation)
}

class MyContinuation<T>(val continuation: Continuation<T>): Continuation<T> {
    override val context = continuation.context
    override fun resumeWith(result: Result<T>) {
        log("<MyContinuation> $result" )
        continuation.resumeWith(result)
    }
}
fun main() = runBlocking {
    test2()
}

suspend fun test1() {
    GlobalScope.launch(MyContinuationInterceptor()) {
        log(1)
        val job = async {
            log(2)
            delay(1000)
            log(3)
            "Hello"
        }
        log(4)
        val result = job.await()
        log("5. $result")
    }.join()
    log(6)
}

suspend fun test2() {
    Executors.newFixedThreadPool(2)
            .asCoroutineDispatcher().use { dispatcher ->
                GlobalScope.launch(dispatcher) {
                    log(1)
                    val job = async {
                        log(2)
                        delay(1000)
                        log(3)
                        "Hello"
                    }
                    log(4)
                    val result = job.await()
                    log("5. $result")
                }.join()
                log(6)
            }
}
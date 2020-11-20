package coroutine

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import coroutine.Interrupt.test1
import coroutine.Interrupt.test2
import coroutine.Interrupt.test3
import coroutine.Interrupt.test4
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import retrofit2.converter.gson.GsonConverterFactory
import utils.MyLog.log
import java.lang.Exception
import kotlinx.coroutines.runBlocking
import java.time.Instant
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object Interrupt {
    val gitHubServiceApi by lazy {
        val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) // 这里添加 Adapter
                .build()
        retrofit.create(GitHubServiceApi::class.java)
    }


    suspend fun test2() = suspendCoroutine<Int> {
        GlobalScope.launch {
            try {
                val result = withTimeout(1300L) {
                    repeat(1000) { i ->
                        log("I'm sleeping $i ...")
                        delay(500L)
                    }
                }
                log(result)
                it.resume(1)
            } catch (e: Exception) {
                // 在子协程里的异常捕获需要用到continuation
                it.resumeWithException(e)
            }
//            log("hello")
        }
    }

    suspend fun test3() {
        coroutineScope {
            withTimeout(1300L) {
                repeat(1000) { i ->
                    log("I'm sleeping $i ...")
                    delay(500L)
                }
            }
        }
    }

    suspend fun test4() {
        coroutineScope {
            val channel = Channel<Int>()
            val job1 = launch {
                delay(5200)
                channel.send(1)
            }

            val job2 = launch {
                delay(5300)
                channel.send(2)
            }
            log("find start ${Instant.now()}")
            val find = withTimeoutOrNull(2000L) {
                channel.receive()
            }
            log("find end ${Instant.now()}")
            log("find = $find")
        }
    }

    fun test1() {
        GlobalScope.launch {
            log(1)
            val deferred = gitHubServiceApi.getUserCoroutine("zhouyueyuedsf")
            log(2)
            withContext(Dispatchers.IO) {
                deferred.cancel()
            }
            try {
                deferred.await()
            } catch (e: Exception) {
                log(e.message)
            }
        }
        log(3)
    }
}

fun main() = runBlocking {
    try {
        log(test4())
    } catch (e: Exception) {
        log(e.message)
    }
    log(3)
}



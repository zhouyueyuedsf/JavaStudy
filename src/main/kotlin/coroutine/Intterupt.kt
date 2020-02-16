package coroutine

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.converter.gson.GsonConverterFactory
import utils.MyLog.log
import java.lang.Exception
import kotlinx.coroutines.runBlocking

val gitHubServiceApi by lazy {
    val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // 这里添加 Adapter
            .build()
    retrofit.create(GitHubServiceApi::class.java)
}

fun main() = runBlocking {
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
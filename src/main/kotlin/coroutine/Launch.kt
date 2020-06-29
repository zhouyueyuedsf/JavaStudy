package coroutine

import coroutine.Launch.postItem
import coroutine.Launch.test1
import coroutine.Launch.test2
import coroutine.Launch.test4
import coroutine.Launch.test5
import kotlinx.coroutines.*
import utils.MyLog.log


object Launch {
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

    suspend fun test5() {
        List(100_000) {
            println(it)
        }
    }

    fun loadImageAsync(name: String): Deferred<String> = GlobalScope.async {
        "async sequential behavior"
    }

    suspend fun loadImage(name: String): String = "async concurrent behavior"

    suspend fun test6() {
        val deferred1 = loadImageAsync("load iamge async")
        val deferred2 = loadImageAsync("load iamge async 2")
        //
        loadImage("")
    }

    suspend fun requestToken(): Token {
        // request for a token and wait result
        delay(1000)
        return Token()
    }

    suspend fun createPost(token: Token, item: Item): Post {
        // sends item to the server & waits
        delay(2000)
        return Post() // returns resulting post
    }

    fun processPost(post: Post) {
        // do some local processing of result
    }

    suspend fun postItem(item: Item) {
        coroutineScope {
            val job = launch(context = MyContinuationInterceptor()) {
                val token = requestToken()
                val post = createPost(token, item)
                log("run processPost")
                processPost(post)
            }
            job.cancel()
        }
    }
}
class Token {}
class Item {}
class Post {}
fun main() = runBlocking {
    postItem(Item())
    log("end")
}

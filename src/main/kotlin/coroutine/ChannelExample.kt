package coroutine

import coroutine.ChannelExample.test7
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import utils.MyLog.log
import kotlin.contracts.ExperimentalContracts

@ExperimentalCoroutinesApi
object ChannelExample {


    suspend fun test1() {
        val channel = Channel<Int>()
        val producer = GlobalScope.launch {
            var i = 0
            while (true) {
                channel.send(i++)
                delay(1000)
            }
        }

        val consumer = GlobalScope.launch {
            while (true) {
                val element = channel.receive()
                log(element)
            }
        }

        producer.join()
        consumer.join()
    }

    suspend fun test2() {
        val receiveChannel = GlobalScope.produce {
            for (i in 0..5) {
                log(i)
                send(i)
            }
        }

        receiveChannel.consumeEach {
            log(it)
        }
    }

    /**
     * 生产者消费者模型，一对一
     */
    suspend fun test3() {
        val channel = Channel<Int>(3)

        val producer = GlobalScope.launch {
            List(5) {
                channel.send(it)
                log("send $it")
            }
            channel.close()
            log("close channel. ClosedForSend = ${channel.isClosedForSend} ClosedForReceive = ${channel.isClosedForReceive}")
        }

        val consumer = GlobalScope.launch {
            for (element in channel) {
                log("receive: $element")
                delay(1000)
            }

            log("After Consuming. ClosedForSend = ${channel.isClosedForSend} ClosedForReceive = ${channel.isClosedForReceive}")
        }

        producer.join()
        consumer.join()
    }

    suspend fun test4() {
        flow {
            List(100) {
                log("send $it")
                emit(it)
            }
        }.collect { value ->
            log("Collecting $value")
            delay(100)
            log("$value collected")
        }
    }

    private fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) {
            // 在流中开始从 1 生产无穷多个整数
            send(x++)
        }
    }

    private fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (x in numbers) send(x * x)
    }

    suspend fun test5() {
        coroutineScope {
            val numbers = produceNumbers()
            val squares = square(numbers)
            repeat(5) {
                log(squares.receive())
            }
            log("Done!")
            // 取消子协程
            coroutineContext.cancelChildren()
        }
    }

    fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
        for (x in numbers) if (x % prime != 0) send(x)
    }

    fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
        var x = start
        while (true) send(x++) // 开启了一个无限的整数流
    }

    /**
     * a function which find a prime
     */
    suspend fun test6() {
        coroutineScope {
            var cur = numbersFrom(2)
            repeat(10) {
                val prime = cur.receive()
                log(prime)
                cur = filter(cur, prime)
            }
            coroutineContext.cancelChildren() // 取消所有的子协程来让主协程结束
        }
    }

    fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        for (msg in channel) {
            log("Processor #$id received $msg")
        }
    }

    suspend fun <E> ReceiveChannel<E>.launchConsumeEach(action: (E) -> Unit) {
        log(this)
        for (e in this) {
            action(e)
        }
    }


    public inline fun repeatOf(times: Int, action: (Int) -> Unit) {
        for (index in 0 until times) {
            action(index)
        }
    }
    /**
     * fade out
     */
    suspend fun test7() {
        coroutineScope {
            val producer = produceNumbers()
            repeatOf(5) {
//                producer.launchConsumeEach {
//                    log(it)
//                }
                launchProcessor(it, producer)
                log("")
            }
            delay(950)
            producer.cancel() // 取消协程生产者从而将它们全部杀死
        }
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun main() = runBlocking {
    test7()
    log("end")
}
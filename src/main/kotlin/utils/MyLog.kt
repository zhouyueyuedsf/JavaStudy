package utils

import java.time.Instant

object MyLog {
    fun log(any: Any?) {
        println("${Thread.currentThread()} $any and time ${Instant.now()}")
    }
}
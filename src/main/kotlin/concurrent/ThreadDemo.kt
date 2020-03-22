package concurrent

import utils.MyLog.log
import kotlin.concurrent.thread


object ThreadDemo {
    fun test1() {
        thread(start = true) {
            log(1)
        }
        log(2)
    }
}

fun main() {
    ThreadDemo.test1()
}
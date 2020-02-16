package utils

object MyLog {
    fun log(any: Any?) {
        println("${Thread.currentThread()}" + any)
    }
}
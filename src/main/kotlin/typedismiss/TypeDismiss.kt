package typedismiss

import java.lang.IllegalArgumentException


class Herd<T: Fruit> {

    fun add(t: T) {

    }

//    operator fun get(o: Int): T {
//        return Fruit()
//    }
}


object TypeDismiss {
    fun test1(`object`: Any?) {
        val b = `object` is List<*>
    }

    fun test2() {
        printSum(listOf("a", "b", "c"))
    }

    fun printSum(c: Collection<*>) {

        val intList = c as? List<Int> ?: throw IllegalArgumentException("List is expected")
        println(intList.sum())
    }

    fun printSum2(c: Collection<Int>) {
        // java是检查不了的
        if (c is List<Int>) {}
    }

    fun addAnswer2(list: List<Any>) {

    }

    fun addAnswer(list: MutableList<Any>) {
//        list.add(42)
    }

    fun test3() {
        val strings = mutableListOf("abc", "edf")
//        addAnswer(strings)
        val apples = listOf(Apple(), Apple())
        addAnswer2(apples)
    }
}

fun main() {
    TypeDismiss.test2()
}
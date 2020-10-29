package basic

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator


enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
    };

    override fun applyAsInt(t: Int, u: Int): Int {
        return this.apply(1, 2)
    }
}

fun main() {
    val a = 13
    val b = 31
    for (f in IntArithmetics.values()) {
        println("$f($a, $b) = ${f.applyAsInt(a, b)}")
    }
    var index = 0;
    var textsLen = 2
    println(index++ % textsLen)
    println(index++ % textsLen)
    println(index++ % textsLen)
    println(index++ % textsLen)
    println(index++ % textsLen)
}
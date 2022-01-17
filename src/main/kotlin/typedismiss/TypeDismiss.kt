package typedismiss

import typedismiss.TypeDismissStudy.Apple
import typedismiss.TypeDismissStudy.Fruit
import java.util.ArrayList
import com.sun.org.apache.xpath.internal.functions.FunctionDef1Arg


open class Animal {

}

open class Cat : Animal() {}
class MaineCoon : Cat() {}

object TypeDismiss {


    fun test1(`object`: Any?) {
        val b = `object` is List<*>
    }

    fun test2() {
        printSum(listOf("a", "b", "c"))
    }

    fun printSum(c: Collection<*>) {
//        val intList = c as? List<Int>
//        println(intList?.sum())
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
        val apples = listOf(TypeDismissStudy.Apple(), TypeDismissStudy.Apple())
        addAnswer2(apples)
    }

    fun enumerteCats(cats: List<Any>): List<Number> {
        return listOf(0, 1, 2)
    }


    val typeBuilder = object : Function1<MaineCoon, MaineCoon> {
        override fun invoke(p1: MaineCoon): MaineCoon {
            TODO("Not yet implemented")
        }

    }


    fun test4() {
        enumerteCats(listOf(Animal()))
        enumerteCats(listOf(0, 1, 2))
//        typeBuilder.invoke(listOf(Animal()))
    }
}
public interface Function1<in P1, out R> : Function<R> {
    /** Invokes the function with the specified argument. */
    public operator fun invoke(p1: P1): R
}
fun main() {
    TypeDismiss.test2()
}
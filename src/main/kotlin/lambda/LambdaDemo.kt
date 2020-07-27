package lambda

import com.google.gson.Gson
import kotlin.math.max

/**
 * @time 2020/5/14
 * @author joy zhou
 */

object LambdaDemo {

    fun test1() {
        // 带接受者的函数字面值
        val intPlus: Int.(Int) -> Int = Int::plus
        // 两种调用方式
        2.intPlus(2)
        intPlus(1, 2)
    }

    inline fun max(v1: String, v2: String, crossinline comparetor: (String, String) -> Unit): Boolean {
        comparetor.invoke(v1, v2)
        return true
    }

    fun test2() {
        max("123", "234") { a, b ->
            // comparetor
//            a.length < b.length
        }
    }

//    a(fun b(param: Int): String {
//        return param.toString()
//    })

    val d = fun(param: Int): String = param.toString()

    inline fun <reified T> Gson.fromJson(json: String) =
            fromJson(json, T::class.java)

    fun testFold() {
        val items = listOf(1, 2, 3, 4, 5)

        // Lambdas 表达式是花括号括起来的代码块。
        items.fold(0, {
            // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
            acc: Int, i: Int ->
            print("acc = $acc, i = $i, ")
            val result = acc + i
            println("result = $result")
            // lambda 表达式中的最后一个表达式是返回值：
            result
        })

        // lambda 表达式的参数类型是可选的，如果能够推断出来的话：
        val joinedToString = items.fold("Elements:", { acc, i -> "$acc $i" })
        println("joinedToString = $joinedToString")
        // 函数引用也可以用于高阶函数调用：
        // A.(B) -> C <=> (A, B) -> C
        val product = items.fold(1, Int::times)
        println("product = $product")
    }

    fun testSiteLog() {
        val time1 = log.filter { it.os == OS.WINDOWS }.map(SiteVisit::duration).average()
        val time2 = log.averageDurationFor {
            it.os == OS.ANDROID
        }
    }
}

fun <T, R> Collection<T>.fold(
        initial: R,
        combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

//
typealias aliasIntFunction = (Int) -> Int

// typealias的另外一种具名（带具体名称）写法
class IntTransformer : (Int) -> Int {
    override operator fun invoke(x: Int): Int = 1 + 2
}

val intFunction = IntTransformer()


data class SiteVisit(val path: String, val duration: Double, val os: OS)
enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

val log = listOf(SiteVisit("/", 34.0, OS.WINDOWS),
        SiteVisit("/", 22.0, OS.MAC),
        SiteVisit("/login", 12.0, OS.WINDOWS),
        SiteVisit(" / signup", 8.0, OS.IOS),
        SiteVisit("/", 16.3, OS.ANDROID))

/**
 * 针对特定的平台log统计抽象的一个函数
 */
fun List<SiteVisit>.averageDurationFor(os: OS) = filter { it.os == os }.map(transform = SiteVisit::duration).average()

/**
 * 进一步提取 筛选条件由调用者自行给出
 */
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
        filter(predicate)
                // (SiteVisit) -> Double
                // T: SiteVisit
                // R: Double
                .map(SiteVisit::duration)
                .average()

fun main() {
    LambdaDemo.testFold()
    intFunction.invoke(1)
}
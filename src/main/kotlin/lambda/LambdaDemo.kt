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

    inline fun max(v1: String, v2: String, crossinline comparetor: (String, String) -> Boolean): Boolean {
        return comparetor.invoke(v1, v2)
    }

    fun test2() {
//        max("123", "234") { a, b ->
//            // comparetor
////            a.length < b.length
//        }
    }

//    a(fun b(param: Int): String {
//        return param.toString()
//    })

    val toString = fun(param: Int): String {
        return param.toString()
    }

    inline fun <reified T> Gson.fromJson(json: String) =
            fromJson(json, T::class.java)

    fun testFold() {
        toString(1)
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
        log.filter { it.os == OS.WINDOWS }.map { it.duration }.average()
        val time1 = log.filter { it.os == OS.WINDOWS }.map(SiteVisit::duration).average()
        val time2 = log.averageDurationFor {
            it.os == OS.ANDROID
        }
    }

    // 比较匿名函数与labmda表达式的区别
    fun testNmhs() {
        max("123", "234") { a: String, b: String ->
            a.length < b.length
        }
        max("123", "234", fun(a: String, b: String): Boolean {
            return a.length < b.length
        })
        max("123", "234", fun(a, b) = a.length < b.length)
    }

    fun compare(a: String) { a.length < 0 }

    inline fun ifNotZero(f1: (String) -> Unit) {
        f1.invoke("123")
    }

    fun testsmh() {
        ifNotZero(this::compare)
    }
    val age = Person::age
    fun testLambda() {
        val sum = { x: Int, y: Int -> x + y }
        val s = sum(1, 2)
        max("123", "234", { a: String, b: String ->
            a.length < b.length
        })
        // lambda表达式可以卸载末尾
        max("123", "234") { a: String, b: String ->
            a.length < b.length
        }
        // String类型可以推断
        max("123", "234") { a, b ->
            a.length < b.length
        }
        // lambda字面值表示
        val compare = { a: String, b: String -> a.length < b.length }
        max("123", "234", compare)
    }

    fun testAnonymousFunction() {
        // 将函数作为局部变量
        val fun1 = fun(x: Int, y: Int): Int = x + y
        fun1(1, 2)
    }
}

fun <T, R> Collection<T>.fold(
        initial: R,
        combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        combine.invoke(accumulator, element)
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
val mDuration = SiteVisit::duration
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

data class Person(val name: String, val age: Int)

val people = listOf(Person("Alice", 29), Person("Bob", 31))
fun lookForAlice(people: List<Person>) {
    for (person in people) {
        if (person.name == "Alice") {
            println("Found")
            return
        }
    }
    println("Alice is not found")
}
// 换成foreach
fun lookForAlice2(people: List<Person>) {
    // 局部返回方案
    // forEach 接受一个lambda函数
    people.forEach { person ->
        if (person.name == "Alice") {
            println("Found")
            return@forEach
        }
        println("Alice is not found")
    }

    // 匿名函数局部返回
    println("匿名函数实验开始")
    people.forEach(fun(person) {
        if (person.name == "Alice") {
            println("Found")
            return
        }
        println("Alice is not found")
    })
    println("匿名函数实验结束")
    // forEach 接受一个lambda函数
    people.forEach { person ->
        if (person.name == "Alice") {
            println("Found")
            // 内联+lambda 实现了非局部返回
            return
        }
        println("Alice is not found")
    }
}

fun sendEmail(person: Person, message: String) {}
val action = { person: Person, message: String ->
    sendEmail(person, message)
}
val nextAction = ::action
fun Person.isAdult() = age >= 21

/**
 * 带有接收者的函数字面值
 */

val sum: Int.(Int) -> Int = { other -> this.plus(other) }

class HTML {
    fun body() {

    }
}
typealias IntFunction = Int.(Int) -> Int

fun html(init: HTML.() -> Unit): HTML {
//    val sum: Int.(Int) -> Int = { other -> plus(other) }
    val html = HTML()  // 创建接收者对象
    html.init()        // 将该接收者对象传给该 lambda
    return html
}

fun main() {

    lookForAlice2(people)
    nextAction.invoke()
//    nextAction(Person("12", 1), "")
    val predicate = Person::isAdult
//    LambdaDemo.testFold()
//    intFunction.invoke(1)
    html {       // 带接收者的 lambda 由此开始
        body()   // 调用该接收者对象的一个方法
    }
}
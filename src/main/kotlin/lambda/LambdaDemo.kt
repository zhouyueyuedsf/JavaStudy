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

    fun max(v1: String, v2: String, comparetor: (String, String) -> Boolean): Boolean {
        return comparetor(v1, v2)
    }

    fun test2() {
        max("123", "234") { a, b ->
            // comparetor
            a.length < b.length
        }
    }

//    a(fun b(param: Int): String {
//        return param.toString()
//    })

    val d = fun(param: Int): String = param.toString()

    inline fun <reified T> Gson.fromJson(json: String) =
            fromJson(json, T::class.java)
    

}
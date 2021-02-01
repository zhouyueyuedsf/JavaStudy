package basic

import others.InvokeKotlin

fun testNullParamsInvoke(s: String) {
    println(s)
}

fun testInvokeNullParams() {
    testNullParamsInvoke(InvokeKotlin.test1())
}

fun main() {
    testInvokeNullParams()
}
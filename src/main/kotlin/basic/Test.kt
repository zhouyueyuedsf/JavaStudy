package basic

import java.io.*

/**
 * @time 2020/6/23
 * @author joy zhou
 */

fun test1(): Unit {
    var `in`: InputStream? = null
    try {
        // 得到输入流
        `in` = FileInputStream("E:\\test\\a.txt")
        //得到输出流
        val file = File("E:\\test\\b.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        val out: OutputStream = FileOutputStream(file, true)
        var i: Int //从输入流读取一定数量的字节，返回 0 到 255 范围内的 int 型字节值
        while (`in`.read().also { i = it } != -1) {
            out.write(i)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun main() {
//    println(LangProvider.ENGLISH)
    var res: Int? = null
    val r = res?.let { 1 } ?: 2
    print(r)
}
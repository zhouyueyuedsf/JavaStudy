package concurrent

object ListStudy {

    fun test1() {
        val list = arrayListOf("1", "2", "3")
        val iterator = list.iterator()
//        iterator.forEach {
//            if (it == "1") iterator.remove()
//        }
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (it == "1") list.remove(it)
        }
        print(list)
    }
}

fun main() {
    ListStudy.test1()
}

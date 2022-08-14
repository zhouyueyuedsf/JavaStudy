package time

import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class TimeTest {

    fun endTimeStampOfMonth(timeStamp: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.time = Date(timeStamp)
        calendar.set(
            calendar.get(Calendar.YEAR).also { println("$it") },
            calendar.get(Calendar.MONTH).also { println("$it") },
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH).also { println("$it") },
            23,
            59,
            59
        )
        calendar[Calendar.MILLISECOND] = 0
        return calendar.timeInMillis
    }

    fun Long.year(): Int {
        val calender = Calendar.getInstance()
        calender.time = Date(this)
        println(SimpleDateFormat("yyyy/MM/dd").format(Date(this)))

        return calender.get(Calendar.YEAR)
    }

    fun toDate(year: Int): Long {
        val calender = Calendar.getInstance()
        calender.set(Calendar.YEAR, year)
        return calender.timeInMillis
    }

    fun getDate(year: Int, month: Int): Date {
        val calender = Calendar.getInstance()
        calender.set(Calendar.YEAR, year)
        calender.set(Calendar.MONTH, month)
        calender.set(Calendar.DAY_OF_MONTH, 1)
        calender.set(Calendar.HOUR_OF_DAY, 0)
        calender.set(Calendar.MINUTE, 0)
        calender.set(Calendar.SECOND, 0)
        return calender.time
    }

    fun test1() {
        println("${(-309135347637L).year()}")
        println("${toDate(1960)}")
    }

    fun test2() {
        println("${getDate(2022, 1)}")
    }
}

fun main(args: Array<String>) {
    val test = TimeTest()
    test.test2()
}
package iii_conventions

import java.time.LocalDate

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        return LocalDate.of(year, month + 1, dayOfMonth).compareTo(
                LocalDate.of(other.year, other.month + 1, other.dayOfMonth))
    }

    operator fun rangeTo(other: MyDate): DateRange {
        return DateRange(this, other)
    }

    operator fun plus(timeInterval: TimeInterval):  MyDate {
        return addTimeIntervals(timeInterval, 1)
    }

    operator fun plus(myInterval: MyInterval): MyDate {
        var updated = LocalDate.of(year, month + 1, dayOfMonth)
                .plusYears(myInterval.years.toLong())
                .plusMonths(myInterval.months.toLong())
                .plusDays(myInterval.days.toLong())
        return MyDate(updated.year, updated.month.value - 1, updated.dayOfMonth)
    }
}


operator fun MyDate.compareTo(other: MyDate) : Int {
    return compareTo(other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {

            var current : MyDate = start

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val nextDate = LocalDate.of(current.year, current.month + 1, current.dayOfMonth)
                        .plusDays(1)
                return current.let {
                    current = MyDate(nextDate.year, nextDate.month.value - 1, nextDate.dayOfMonth)
                    return it
                }
            }
        }
    }

    operator fun contains(d: MyDate): Boolean {
        return start <= d && d <= endInclusive
    }

}

class MyInterval (val years: Int, val months: Int, val days: Int) {

    operator fun plus(other: MyInterval): MyInterval {
        return MyInterval(years + other.years, months + other.months, days + other.days)
    }

}

fun fromInterval(timeInterval: TimeInterval): MyInterval {
    return when (timeInterval) {
        TimeInterval.YEAR -> MyInterval(1, 0, 0)
        TimeInterval.WEEK -> MyInterval(0, 0, 7)
        TimeInterval.DAY -> MyInterval(0, 0, 1)
    }
}

operator fun TimeInterval.times(times: Int): MyInterval {
    var total: MyInterval = MyInterval(0, 0, 0)

    for (i in 0 until times) {
        total += fromInterval(this)
    }

    return total
}

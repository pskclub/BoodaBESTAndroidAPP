package com.boodabest.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val MSEC = 1
const val SEC = 1000
const val MIN = 60000
const val HOUR = 3600000
const val DAY = 86400000

@Retention(AnnotationRetention.SOURCE)
annotation class TimeMillis


fun timestamp(): Long = System.currentTimeMillis()


fun Date.asString(format: DateFormat): String = format.format(this)

fun Date.asString(format: String): String = asString(SimpleDateFormat(format, Locale.US))


object DateTimeHelper {
    const val DATE_FORMAT_SIMPLE_STRING = "yyyy-MM-dd HH:mm:ss"

    @JvmField
    val DATE_FORMAT_SIMPLE_FORMAT = object : ThreadLocal<DateFormat>() {
        override
        fun initialValue(): DateFormat {
            return SimpleDateFormat(DATE_FORMAT_SIMPLE_STRING, Locale.US)
        }
    }
}
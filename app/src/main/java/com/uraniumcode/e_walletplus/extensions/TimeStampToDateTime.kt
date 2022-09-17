package com.uraniumcode.e_walletplus.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.day():String{
    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = Date(this * 1000)
    val day = cal.get(Calendar.DAY_OF_MONTH)
    return day.toString()
}

fun Long.month():String{
    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = Date(this)
    val month = cal.get(Calendar.MONTH)
    return month.toString()
}

fun Long.year():String{
    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = Date(this)
    val year = cal.get(Calendar.YEAR)
    return year.toString()
}

fun Long.time():String{
    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = Date(this)
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(cal.time).toString()
}

fun Long.dateTime():String{
    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.time = Date(this)
    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
    return sdf.format(cal.time).toString()
}
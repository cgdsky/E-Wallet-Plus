package com.uraniumcode.e_walletplus.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.timeStamp(): Long {
    val formatter: DateFormat = SimpleDateFormat("dd-MM-yyyy")
    val timeStamp = formatter.parse(this) as Date
    return timeStamp.time
}
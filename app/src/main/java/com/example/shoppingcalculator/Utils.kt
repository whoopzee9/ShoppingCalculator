package com.example.shoppingcalculator

fun getFormattedTime(hour: Int, minute: Int): String {
    if ((hour >= 0) and (hour < 24) and (minute < 60) and (minute >= 0)) {
        val hourStr: String = if (hour < 10) {
            "0$hour"
        } else {
            hour.toString()
        }
        var minuteStr: String = if (minute < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        return "$hourStr:$minuteStr"
    } else {
        return "00:00"
    }
}


fun debtCounting(users: List<PaymentUser>): Double {
    var total = 0.0
    for (item in users) {
        if (!item.isPaid) {
            total += item.payment
        }
    }
    return total
}
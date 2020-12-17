package com.example.shoppingcalculator

import org.junit.Assert.assertEquals
import org.junit.Test


class AppUnitTests {

    @Test
    fun checkFormattedTimeResult() {
        assertEquals(getFormattedTime(211, 20), "00:00")
        assertEquals(getFormattedTime(23, 59), "23:59")
        assertEquals(getFormattedTime(12, 34), "12:34")
        assertEquals(getFormattedTime(9, 5), "09:05")
        assertEquals(getFormattedTime(0, 12), "00:12")
        assertEquals(getFormattedTime(11, 0), "11:00")
        assertEquals(getFormattedTime(13, 2), "13:02")
    }

    @Test
    fun checkEmptyDebtCounting() {
        val users = listOf<PaymentUser>()
        assertEquals(debtCounting(users), 0.0, 0.0000001)
    }

    @Test
    fun checkDebtCounting() {
        val user1 = PaymentUser(1, 200.0, true)
        val user2 = PaymentUser(2,400.0, false)
        val user3 = PaymentUser(3,1000.01, false)
        val user4 = PaymentUser(4,310.0, false)
        val user5 = PaymentUser(5,215.0, false)
        val user6 = PaymentUser(6,20.45, false)
        val user7 = PaymentUser(7,21.45, true)
        val user8 = PaymentUser(8,13.23, false)
        val users = listOf<PaymentUser>(user1, user2, user3, user4, user5, user6, user7, user8)
        assertEquals(debtCounting(users), 1958.69, 0.01)
    }
}
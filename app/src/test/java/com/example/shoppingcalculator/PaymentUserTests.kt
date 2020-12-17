package com.example.shoppingcalculator

import org.junit.Assert
import org.junit.Test
import java.security.InvalidParameterException


class PaymentUserTests {
    @Test
    fun paymentUserNotNull() {
        Assert.assertNotNull(PaymentUser(5, 40.0, true))
    }

    @Test(expected = InvalidParameterException::class)
    fun checkInvalidPaymentException() {
        val user = PaymentUser(5, -40.0, true)

    }

    @Test(expected = InvalidParameterException::class)
    fun checkInvalidIDException() {
        val user = (PaymentUser(-2, 2.0, true))
    }
}
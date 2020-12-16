package com.example.shoppingcalculator

import android.view.View
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito


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

}
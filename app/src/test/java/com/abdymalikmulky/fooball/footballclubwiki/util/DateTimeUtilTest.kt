package com.abdymalikmulky.fooball.footballclubwiki.util

import com.abdymalikmulky.fooball.footballclubwiki.util.DateTimeUtil.convertToReadableDate
import org.junit.Test

import org.junit.Assert.*

class DateTimeUtilTest {

    @Test
    fun testConvertToReadableDate() {
        val dateString = "25/08/18"
        assertEquals("25 August 2018", convertToReadableDate(dateString))
    }
}
package com.abdymalikmulky.fooball.footballclubwiki.util

import java.text.SimpleDateFormat

object DateTimeUtil {
    fun convertToReadableDate(dateString: String) : String{
        val sdf = SimpleDateFormat("d/MM/yy")
        val d = sdf.parse(dateString)
        sdf.applyPattern("d MMMM yyyy")
        val newDateFormat = sdf.format(d)
        return newDateFormat
    }
}
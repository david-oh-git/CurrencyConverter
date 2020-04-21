package io.audioshinigami.currencyconverter.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * utility to get today's date
 */

object Date {
    val currentDate: String
        get() = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format( Calendar.getInstance().time ).trim()
}
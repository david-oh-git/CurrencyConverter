package io.audioshinigami.currencyconverter.utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun Map<String, String>.toApiString(): String {
    var result = ""

    for( (key,value) in this ){
        result += "&$key=$value"
    } /*end FOR*/

    return result.removePrefix("&")

} /*end toApiString*/

val Double.currencyFormat: String
    get() = DecimalFormat("#,###.##").apply { roundingMode = RoundingMode.CEILING }.format(this)
package io.audioshinigami.currencyconverter.utils

fun Map<String, String>.toApiString(): String {
    var result = ""

    for( (key,value) in this ){
        result += "&$key=$value"
    } /*end FOR*/

    return result.removePrefix("&")

} /*end toApiString*/


package io.audioshinigami.currencyconverter.utils

/*
* simple utility object takes rate and amount returns converted amount
* & other currency codes manipulations */

object RateUtil {

    fun convertAmount(rate: Double = 1.0, amount: Double) = rate * amount
    fun getRequestCode(fromCode: String, toCode: String ) = "${fromCode}_$toCode,${toCode}_$fromCode"
    fun getCode(currencyCode: String) = currencyCode.substring(0 until currencyCode.indexOf(',') )
}
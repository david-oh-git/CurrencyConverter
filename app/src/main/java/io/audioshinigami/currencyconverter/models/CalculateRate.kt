package io.audioshinigami.currencyconverter.models

object CalculateRate {

    fun convertAmount(rate: Double = 1.0, amount: Double) = rate * amount
}
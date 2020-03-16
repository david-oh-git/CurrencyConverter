package io.audioshinigami.currencyconverter.models

data class Rate(val currencyCode: String, var rateValue: Double = 0.0, var reverseRate: Double = 0.0)
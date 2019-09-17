package io.audioshinigami.currencyconverter.convertAmount

import androidx.lifecycle.ViewModel
import io.audioshinigami.currencyconverter.models.RateResponse

class CurrencyConvertViewModel : ViewModel() {

    fun convertCurrency(inputValue: Double, fromCode: String, toCode: String){

    }

    fun getRequestCode(fromCode: String, toCode: String): String {
        return ""
    }

    fun getRate(code: String): RateResponse? {
        return null
    }
} /*END*/

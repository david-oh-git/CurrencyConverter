package io.audioshinigami.currencyconverter.convertAmount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.audioshinigami.currencyconverter.models.RateResponse

class CurrencyConvertViewModel : ViewModel() {

    // Two-way databinding, exposing MutableLiveData
    val convertedAmount = MutableLiveData<String>()
    val inputAmount = MutableLiveData<String>()

    fun convertCurrency(inputValue: Double? = inputAmount.value?.toDouble(), fromCode: String, toCode: String){

    }

    fun getRequestCode(fromCode: String, toCode: String): String {
        return ""
    }

    fun getRate(code: String): RateResponse? {
        return null
    }
} /*END*/

package io.audioshinigami.currencyconverter.convertAmount

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.models.CurrencyItem
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.repository.CodeRepository
import io.audioshinigami.currencyconverter.repository.RateRepository

class CurrencyConvertViewModel(
    codeRepository: CodeRepository? = null,
    rateRepository: RateRepository? = null
) : ViewModel() {

    // Two-way databinding, exposing MutableLiveData
    val convertedAmount = MutableLiveData<String>()
    val inputAmount = MutableLiveData<String>()
    val fromCode = MutableLiveData<String>()
    val toCode = MutableLiveData<String>()

    fun start(){

    }

    fun convertCurrency(inputValue: Double? = inputAmount.value?.toDouble(), fromCode: String, toCode: String){

    }

    fun getRequestCode(fromCode: String , toCode: String): String {
        return ""
    }

    fun getRate(code: String): RateResponse? {
        return null
    }

    fun getAllCurrencyItems(): ArrayList<CurrencyItem> {

        return arrayListOf()
    }
} /*END*/

package io.audioshinigami.currencyconverter.convertAmount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.models.CurrencyItem
import io.audioshinigami.currencyconverter.models.Rate
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyConvertViewModel(
    val flagDataRepository: FlagDataRepository? = null,
    val rateRepository: RateRepository? = null
) : ViewModel() {

    // Two-way databinding, exposing MutableLiveData
    val convertedAmount = MutableLiveData<String>()
    val inputAmount = MutableLiveData<String?>()
    val fromCode = MutableLiveData<String>()
    val toCode = MutableLiveData<String>()
    val rates: Map<String, Rate> = mutableMapOf() // contains all rates since app started.

    // code to make request eg USD_NGN -> US dollars to naira
    val code = {
        fromCode: String, toCode: String ->
    }

    val reverseCode = {
        code: String ->

    }


    fun start(){

    }

    fun fetchRate(requestCode: String) {
        // TODO use repo to fetchRate data
        // TODO call extractRate with data if not null
    }

    fun getAllCurrencyItems(): ArrayList<CurrencyItem> {

        return arrayListOf()
    }

    fun initRateProcess(requestCode: String){
        //TODO starts the process of getting a rate value
    }

    fun extractRate(data: Map<String, String>){
        //TODO updates rates Map
    }

    fun setConvertedAmount(code: String){
        //TODO get rate and inputAmount & convert
    }

} /*END*/

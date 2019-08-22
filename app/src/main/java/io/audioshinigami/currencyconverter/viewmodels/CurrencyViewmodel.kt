package io.audioshinigami.currencyconverter.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.models.CurrencyRate
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class CurrencyViewmodel: ViewModel() {

    private val rateRepository = RateRepository(ApiFactory.rateApi)
    val rateLiveData = MutableLiveData<CurrencyRate>()

    fun demo() = viewModelScope.launch(Dispatchers.IO) {
        //TODO code here
    }

    fun getRate(currencyCode: String) = viewModelScope.launch (Dispatchers.Default) {
        val data = withContext(Dispatchers.IO){ rateRepository.getRate(currencyCode) }
        rateLiveData.postValue(data)
    }
}
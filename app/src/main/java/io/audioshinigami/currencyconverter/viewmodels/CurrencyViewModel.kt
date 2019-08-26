package io.audioshinigami.currencyconverter.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.models.Rate
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyViewModel: ViewModel() {

    private val rateRepository = RateRepository(ApiFactory.rateApi)
    val rateLiveData = MutableLiveData<RateResponse>()

    fun fetchRate(currencyCode: String) = viewModelScope.launch (Dispatchers.Default) {
        val data = withContext(Dispatchers.IO){ rateRepository.fetchRateFromApi(currencyCode) }
        rateLiveData.postValue(data)
    }

    fun extractRate(code: String, result: Map<String, String>): Rate {
        return rateRepository.getRate(code, result)
    }/*end extractrate*/
}
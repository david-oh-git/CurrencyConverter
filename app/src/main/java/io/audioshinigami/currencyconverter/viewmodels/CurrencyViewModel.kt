package io.audioshinigami.currencyconverter.viewmodels

import android.app.Application
import android.content.res.TypedArray
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.Currency
import io.audioshinigami.currencyconverter.models.FlagData
import io.audioshinigami.currencyconverter.models.Rate
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyViewModel(application: Application): AndroidViewModel(application) {

    private val rateRepository = RateRepository(ApiFactory.rateApi)
    val rateLiveData = MutableLiveData<RateResponse>()
    private val flagData by lazy { FlagData.getCodes { mContext.resources.getStringArray(R.array.countries).toCollection(ArrayList()) } }
    private val mContext by lazy { getApplication<Application>().applicationContext }
    val flags: ArrayList<Int>
    get()
    = FlagData.getFlags {
            val results: ArrayList<Int> = arrayListOf()
            val images: TypedArray = mContext.resources.obtainTypedArray(R.array.flags_codes)

            for( index in 0 until 166 ){
                val id: Int = images.getResourceId(index, -1)
                results.add(index, id)
            }
            images.recycle()
            results
        }

    fun fetchRate(currencyCode: String) = viewModelScope.launch (Dispatchers.Default) {
        val data = withContext(Dispatchers.IO){ rateRepository.fetchRateFromApi(currencyCode) }
        rateLiveData.postValue(data)
    }

    fun extractRate(code: String, result: Map<String, String>): Rate {

        return rateRepository.getRate(code, result)
    }/*end extract*/

    fun getCode(position: Int) = flagData[position]

    fun getAllCurrency() =
        synchronized(this){
            val result: ArrayList<Currency> = arrayListOf()
            flags.forEachIndexed{
                index, flag ->
                result.add( Currency(flagData[index], flag))
            }

            result
        }
}
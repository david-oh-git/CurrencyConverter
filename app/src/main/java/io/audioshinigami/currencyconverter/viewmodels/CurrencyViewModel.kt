package io.audioshinigami.currencyconverter.viewmodels

import android.app.Application
import android.content.res.TypedArray
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem
import io.audioshinigami.currencyconverter.models.FlagData
import io.audioshinigami.currencyconverter.models.Rate
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.utils.currency_codes_total
import kotlinx.coroutines.*

class CurrencyViewModel(application: Application): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val rateRepository = RateRepository(ApiFactory.rateApi)
    val rateLiveData = MutableLiveData<RateResponse>()
    private val currencyCodes by lazy { FlagData.getCodes { mContext.resources.getStringArray(R.array.countries).toCollection(ArrayList()) } }
    private val mContext by lazy { getApplication<Application>().applicationContext }
    private val flags: ArrayList<Int>
    get()
    = FlagData.getFlags {
            val results: ArrayList<Int> = arrayListOf()
            val images: TypedArray = mContext.resources.obtainTypedArray(R.array.flags_codes)

            for( index in 0 until currency_codes_total ){
                val id: Int = images.getResourceId(index, -1)
                results.add(index, id)
            }
            images.recycle()
            results
        }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun fetchRate(currencyCode: String) = uiScope.launch  {
        val data = withContext(Dispatchers.IO){ rateRepository.fetchRateFromApi(currencyCode) }
        rateLiveData.postValue(data)
    }

    fun extractRate(code: String, result: Map<String, String>): Rate {

        return rateRepository.getRate(code, result)
    }/*end extract*/

    fun getRequestCode(fromCode: String, toCode: String): String {
        return "${fromCode}_$toCode,${toCode}_$fromCode"
    }

    fun getCode(position: Int) = currencyCodes[position]

    fun getAllCurrency() =
        synchronized(this){
            val result: ArrayList<CurrencyItem> = arrayListOf()
            flags.forEachIndexed{
                index, flag ->
                result.add( CurrencyItem(currencyCodes[index], flag))
            }

            result
        }
}
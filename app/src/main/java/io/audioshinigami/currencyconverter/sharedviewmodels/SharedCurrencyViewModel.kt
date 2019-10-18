package io.audioshinigami.currencyconverter.sharedviewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.convertAmount.events.Event
import io.audioshinigami.currencyconverter.models.CurrencyItem
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.utils.currencyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SharedCurrencyViewModel(
    val flagDataRepository: FlagDataRepository? = null,
    val rateRepository: RateRepository? = null
) : ViewModel() {

    // Two-way databinding, exposing MutableLiveData
    private val _convertedAmount = MutableLiveData<String>()
    val convertedAmount: LiveData<String> = _convertedAmount
    val inputAmount = MutableLiveData<String?>()

    private val _fromCode = MutableLiveData<String>().apply { value = "NGN" }
    val fromCode: LiveData<String> = _fromCode

    private val _toCode = MutableLiveData<String>().apply { value = "USD" }
    val toCode: LiveData<String> = _toCode

    var ratesCache = mutableMapOf<String, Double>() // contains all ratesCache since app started.

    lateinit var networkAvailable: () -> Boolean

    // convertCode to make request eg USD_NGN -> US dollars to naira
    val convertCode = {
        fromCode: String, toCode: String ->
        "${fromCode}_${toCode}"
    }

    val reverseCode = {
        code: String ->

    }

    fun start(){
        // check if both codes are same hence no conversion required
        if( _fromCode.value == _toCode.value){
            _convertedAmount.value = inputAmount.value
            return
        }

        val code = convertCode(fromCode.value.toString(), toCode.value.toString())
        initRateProcess(code)
    }

    fun fetchRate(code: String) {

        val requestCode = getRequestCode(fromCode.value ?: "", toCode.value ?: "")
         viewModelScope.launch(Dispatchers.Main) {

             try{
                 val data = withContext(Dispatchers.IO){ rateRepository?.fetchRateFromApi(requestCode) }
                 val tempCache: Map<String, String> = data?.rate ?: mutableMapOf()

                 // add code & their ratesCache to storage TODO add db to store ratesCache
                 for( (k,v) in tempCache){
                     ratesCache[k] = v.toDouble()
                 }

                 setConvertedAmount(ratesCache[code] ?: 0.0)
             }catch (e : UnknownHostException){
                 Log.d("TAGU", " error message is : ${e.message}")

                 // Send a toast of the error message
                 e.message?.apply{
                     EventBus.getDefault().post(Event.ToastEvent(e.message!!))
                 }
             }
             catch (e : SocketTimeoutException){
                 Log.d("TAGU", " error message is : ${e.message}")

                 e.message?.apply{
                     EventBus.getDefault().post(Event.ToastEvent(e.message!!))
                 }
             }
             catch (e : Exception){
                 Log.d("TAGU", " error message is : ${e.message}")

                 e.message?.apply{
                     EventBus.getDefault().post(Event.ToastEvent(e.message!!))
                 }
             }
        } // end [viewModelScope]
    }

    fun getAllCurrencyItems(): ArrayList<CurrencyItem> {

        return arrayListOf()
    }

    fun initRateProcess(code: String){
        //starts the process of getting a rate value
        val isRateCached = checkRateCache(code)

        // if rate exists in cache then convert & set currency value
        if(isRateCached){
            setConvertedAmount(ratesCache[code] ?: 0.0)
            return
        }

        //check for network b4 calling [fetchRate] TODO now check for internet
        if( networkAvailable.invoke())
            fetchRate(code)
        else
            EventBus.getDefault().post( Event.ToastEvent( "Internet required .." ))
    }

    fun setConvertedAmount(rate: Double){
        // set converted Amount
        val inputValue = inputAmount.value?.toDoubleOrNull() ?: 0.0
        _convertedAmount.value = ( inputValue * rate ).currencyFormat
    }

    fun checkRateCache(code: String): Boolean {
        //check if rate for code already exists
        return ratesCache.contains(code)
    }

    fun getRequestCode(fromCode: String, toCode: String): String {
        return "${fromCode}_${toCode},${toCode}_${fromCode}"
    }

    fun setFromCode(code: String){
        _fromCode.value = code
    }

    fun setToCode(code: String){
        _toCode.value = code
    }

} /*END*/

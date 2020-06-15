/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.convertAmount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.data.AppRepository
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.utils.OneTimeLiveData
import io.audioshinigami.currencyconverter.utils.SnackMessage
import io.audioshinigami.currencyconverter.utils.currencyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ConvertViewModel(
    private val repository: AppRepository
): ViewModel() {

    private val _convertedAmount = MutableLiveData<String>()
    val convertedAmount: LiveData<String> = _convertedAmount

    val inputAmount = MutableLiveData<Double>()

    private val _fromCode = repository.fromCode
    val fromCode: LiveData<String> = _fromCode

    private val _toCode = repository.toCode
    val toCode: LiveData<String> = _toCode


    private val apiCallCode: String
        get() = "${fromCode}_${toCode}"

    val snackMessage = OneTimeLiveData<SnackMessage>()

    fun initConversion() = viewModelScope.launch {
        // check if to & from code is d same
        if( _toCode.value == _fromCode.value ){
            setConvertedAmount()
            return@launch
        }

        // check if rate is in DB
        val rate = checkDbForRate()
        if( rate != null){
            setConvertedAmount(rate.rate)
            return@launch
        }
        
        // make api call for rate
        if( repository.hasNetworkConnection.value == true){
            fetchRateFromApi()
        }else{
            snackMessage.sendData( SnackMessage("Internet required"))
        }
    }

    fun setHasNetworkConnection(value: Boolean) = viewModelScope.launch {
        repository.setNetworkConnection(value)
    }

    private fun fetchRateFromApi() = viewModelScope.launch {

        try{
            repository.getResult(apiCallCode)
                ?.rate?.let {
                    setConvertedAmount(it)
                }



        }catch (e : UnknownHostException){
            Timber.d( " error message is : ${e.message}")
        }
        catch (e : SocketTimeoutException){
            Timber.d( " error message is : ${e.message}")

        }
        catch (e : Exception){
            Timber.d( " error message is : ${e.message}")
        }
    }
    
    private fun checkDbForRate(): Rate? {
        
        var rate: Rate? = null
        viewModelScope.launch { 
            rate = withContext(Dispatchers.IO) { repository.getAllRates() }
                .find { it.code == apiCallCode }
        }
        
        return rate
    }

    private fun setConvertedAmount(rate: Double = 1.0) = viewModelScope.launch {
        _convertedAmount.postValue( inputAmount.value?.times(rate)?.currencyFormat )
    }
}
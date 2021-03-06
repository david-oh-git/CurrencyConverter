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

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.data.AppRepository
import io.audioshinigami.currencyconverter.utils.OneTimeLiveData
import io.audioshinigami.currencyconverter.utils.SnackMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.RoundingMode
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.DecimalFormat
import javax.inject.Inject

class ConvertViewModel @Inject constructor(
    app: App,
    private val repository: AppRepository
): AndroidViewModel(app) {

    private val _convertedAmount = MutableLiveData<String>().apply { value = repository.convertedAmount }
    val convertedAmount: LiveData<String> = _convertedAmount

    private val _inputAmount = MutableLiveData<String>().apply { value = repository.inputAmount }
    val inputAmount = _inputAmount

    private val _fromCode = MutableLiveData<String>().apply { value = repository.fromCode }
    val fromCode: LiveData<String> = _fromCode

    private val _toCode = MutableLiveData<String>().apply { value = repository.toCode }
    val toCode: LiveData<String> = _toCode

    private val apiCallCode: String
        get() = "${fromCode.value}_${toCode.value}"

    private val Double.currencyFormat: String
        get() = DecimalFormat("#,###.##").apply { roundingMode = RoundingMode.CEILING }.format(this)

    private val hasNetworkConnection: MutableLiveData<Boolean> = MutableLiveData(false)

    val snackMessage = OneTimeLiveData<SnackMessage>()

    fun initConversion() = viewModelScope.launch(Dispatchers.IO) {
        // check if to & from code is d same
        if( toCode.value == fromCode.value ){
            setConvertedAmount()
            return@launch
        }
        // check if rate is in DB
        repository.find(apiCallCode)
            ?.let {
                setConvertedAmount(it.rate)
                return@launch
            }

        updateNetworkStatus() // check for network LOLLIPOP API & below
        
        // make api call for rate
        if( hasNetworkConnection.value == true ){
            fetchRateFromApi()
        }else{
            launch(Dispatchers.Main) {
                snackMessage.sendData( SnackMessage(getApplication<App>().getString(R.string.internet_required)))
            }
        }
    }

    fun setHasNetworkConnection(value: Boolean) = viewModelScope.launch {
        hasNetworkConnection.postValue(value)
    }

    fun setFromCode(code: String) = viewModelScope.launch {
        _fromCode.postValue(code)
    }

    fun setToCode(code: String) = viewModelScope.launch {
        _toCode.postValue(code)
    }

    fun setSharedPreferenceListener(listener: SharedPreferences.OnSharedPreferenceChangeListener){
        repository.setSharedPreferenceListener(listener)
    }

    fun removeSharedPreferenceListener(listener: SharedPreferences.OnSharedPreferenceChangeListener){
        repository.removeSharedPreferenceListener(listener)
    }

    private fun fetchRateFromApi() = viewModelScope.launch {

        launch(Dispatchers.IO) {
            try{

                repository.getResult(apiCallCode)
                    ?.rate?.let {
                        setConvertedAmount(it)
                    }

            }catch (e : UnknownHostException){
                Timber.d( " error message is : ${e.message}")
                launch(Dispatchers.Main) {
                    val context = getApplication<App>().applicationContext
                    snackMessage.sendData( SnackMessage(context.getString(R.string.no_internet_error)))
                }
            }
            catch (e : SocketTimeoutException){
                Timber.d( " error message is : ${e.message}")
                launch(Dispatchers.Main) {
                    val context = getApplication<App>().applicationContext
                    snackMessage.sendData( SnackMessage(context.getString(R.string.no_internet_error)))
                }
            }
            catch (e : Exception){
                Timber.d( " error message is : ${e.message}")
                launch(Dispatchers.Main) {
                    val context = getApplication<App>().applicationContext
                    snackMessage.sendData( SnackMessage(context.getString(R.string.no_internet_error)))
                }
            }
        }

    }

    fun setConvertedAmount(rate: Double = 1.0) = viewModelScope.launch {

        inputAmount.value?.toDouble()?.times(rate)?.let {
            _convertedAmount.postValue(it.currencyFormat)
            repository.setConvertedAmount(it.currencyFormat)
            repository.setInputAmount(_inputAmount.toString())
        }

    }

    private fun updateNetworkStatus() = viewModelScope.launch {
        // check if network is available & update
        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            val context = getApplication<App>().applicationContext
            val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connManager.activeNetworkInfo
            val connected = networkInfo != null && networkInfo.isAvailable && networkInfo.isConnectedOrConnecting

            setHasNetworkConnection(connected)
        }
    }
}
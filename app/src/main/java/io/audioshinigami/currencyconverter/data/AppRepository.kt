package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.network.Result

interface AppRepository {

    val fromCode: MutableLiveData<String>

    val toCode: MutableLiveData<String>

    val hasNetworkConnection: MutableLiveData<Boolean>
        get() = MutableLiveData(false)

    suspend fun <T> save(obj: T)

    suspend fun <T> delete( obj: T)

    fun observeRates(): LiveData<List<Rate>>

    suspend fun getAllRates(): List<Rate>

    suspend fun <T> deleteAll( type: Class<T> )

    suspend fun getResult(code: String): Result<List<Rate>>

    suspend fun setNetworkConnection(enabled: Boolean)

    suspend fun setFromCode(code: String)

    suspend fun setToCode(code: String)

}
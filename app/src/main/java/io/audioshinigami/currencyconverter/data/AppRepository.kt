package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.network.Result

interface AppRepository {

    val fromCode: MutableLiveData<String>

    val toCode: MutableLiveData<String>

    suspend fun <T> save(obj: T)

    suspend fun <T> delete( obj: T)

    fun observeRates(): LiveData<List<Rate>>

    suspend fun getAllRates(): List<Rate>

    suspend fun <T> deleteAll( type: Class<T> )

    suspend fun getResult(code: String): Result<List<Rate>>

}
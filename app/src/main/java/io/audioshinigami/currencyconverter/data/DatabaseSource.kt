package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData

interface DatabaseSource {

    suspend fun <T> save(obj: T)

    suspend fun <T> delete( obj: T)

    fun observePapers(): LiveData<List<Paper>>

    fun observeRates(): LiveData<List<Rate>>

    suspend fun getAllPapers(): List<Paper>

    suspend fun getAllRates(): List<Rate>

    suspend fun <T> deleteAll( type: Class<T> )

}
package io.audioshinigami.currencyconverter.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.network.Result

class FakeDatabaseSource(
    var rateStorage: MutableList<Rate> = mutableListOf()
): DatabaseSource {

    override suspend fun <T> save(obj: T) {

        when(obj){
            is Rate -> rateStorage.add(obj)
        }
    }

    override suspend fun <T> delete(obj: T) {
        when(obj){
            is Rate -> rateStorage.remove(obj)
        }
    }

    override fun observeRates(): LiveData<List<Rate>> {
        return MutableLiveData(rateStorage)
    }

    override suspend fun getAllRates(): List<Rate> {
        return rateStorage
    }

    override suspend fun <T> deleteAll(type: Class<T>) {
        when{
            type.isAssignableFrom(Rate::class.java) -> rateStorage.clear()
        }
    }

    override suspend fun getResult(code: String): Result<List<Rate>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllRates() {
        rateStorage.clear()
    }
}
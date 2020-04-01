package io.audioshinigami.currencyconverter.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.Rate

class FakeDatabaseSourceTest(
    var paperStorage: MutableList<Paper> = mutableListOf(),
    var rateStorage: MutableList<Rate> = mutableListOf()
): DatabaseSource {

    override suspend fun <T> save(obj: T) {
        when(obj){
            is Paper -> paperStorage.add(obj)
            is Rate -> rateStorage.add(obj)
        }
    }

    override suspend fun <T> delete(obj: T) {
        when(obj){
            is Paper -> paperStorage.remove(obj)
            is Rate -> rateStorage.remove(obj)
        }
    }

    override fun observePapers(): LiveData<List<Paper>> {
        return MutableLiveData(paperStorage)
    }

    override fun observeRates(): LiveData<List<Rate>> {
        return MutableLiveData(rateStorage)
    }

    override suspend fun getAllPapers(): List<Paper> {
        return paperStorage
    }

    override suspend fun getAllRates(): List<Rate> {
        return rateStorage
    }

    override suspend fun <T> deleteAll(type: Class<T>) {
        when{
            type.isAssignableFrom(Paper::class.java) -> paperStorage.clear()
            type.isAssignableFrom(Rate::class.java) -> rateStorage.clear()
        }
    }
}
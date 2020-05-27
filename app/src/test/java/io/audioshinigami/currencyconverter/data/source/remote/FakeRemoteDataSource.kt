package io.audioshinigami.currencyconverter.data.source.remote

import androidx.lifecycle.LiveData
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.network.Result

class FakeRemoteDataSource(
    var rates: MutableList<Rate>? = mutableListOf()
): DatabaseSource {

    override suspend fun getResult(code: String): Result<List<Rate>> {

        rates?.let {
            it.add( Rate(56, code, 0.30, "no date"))
            return Result.Success(it)
        }
        return Result.Error( Exception("Error : can not get data"))
    }

    override suspend fun <T> save(obj: T) {
        TODO("Not yet implemented")
    }

    override suspend fun <T> delete(obj: T) {
        TODO("Not yet implemented")
    }

    override fun observeRates(): LiveData<List<Rate>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRates(): List<Rate> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> deleteAll(type: Class<T>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllRates() {
        TODO("Not yet implemented")
    }
}
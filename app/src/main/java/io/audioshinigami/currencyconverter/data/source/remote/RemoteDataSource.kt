package io.audioshinigami.currencyconverter.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.network.ConverterApi
import io.audioshinigami.currencyconverter.network.Result
import io.audioshinigami.currencyconverter.utils.Date
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(
    private val api: ConverterApi,
    private val currentDate: String = Date.currentDate,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): BaseRemoteDataSource() , DatabaseSource {

    private suspend fun fetchRateFromApi(currencyCode: String) = withContext(ioDispatcher) {

        safeApiCall(
            call = { api.getRate(currencyCode = currencyCode).await() },
            errorMsg = "Error fetching currency rate"
        )

    }

    private suspend fun fetchRates(code: String) = withContext(ioDispatcher){
        safeApiCall(
            call = {api.getRates(currencyCode = code).await() },
            errorMsg = "Failed to retrieve Rates"
        )
    }

    private suspend fun fetchCurrencies() = withContext(ioDispatcher){
        safeApiCall(
            call = {api.getCurrencies().await() },
            errorMsg = "Failed to retrieve Currencies"
        )
    }

    fun getRate(code: String, result: Map<String, String>): List<Rate> {


        val listOfRates: MutableList<Rate> = mutableListOf()

        for( (key, value) in result){
            val rate = Rate(0,key, value.toDouble(), currentDate )

            listOfRates.add(rate)
        }

        return listOfRates
    }

    override suspend fun getResult(code: String): Result<List<Rate>> = withContext(ioDispatcher){

        return@withContext Result.Error(Exception("not implemented"))
    }

    override suspend fun getAllRates(): List<Rate> {
        // Not needed
        return mutableListOf()
    }

    override fun observeRates(): LiveData<List<Rate>> {
        // Not needed
        return MutableLiveData(mutableListOf())
    }

    override suspend fun <T> save(obj: T) {
        // Not needed
    }

    override suspend fun <T> delete(obj: T) {
        // Not needed
    }

    override suspend fun <T> deleteAll(type: Class<T>) {
        // pass
    }

    override suspend fun deleteAllRates() {
        // Not needed
    }
}
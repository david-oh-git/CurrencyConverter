package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRepository(
    private val databaseSource: DatabaseSource,
    private val remoteDataSource: DatabaseSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AppRepository {

    override val fromCode: MutableLiveData<String>
        get() = MutableLiveData("NGN")
    override val toCode: MutableLiveData<String>
        get() = MutableLiveData("USD")

    override suspend fun <T> save(obj: T) = withContext(ioDispatcher){
        databaseSource.save(obj)
    }

    override suspend fun <T> delete(obj: T) = withContext(ioDispatcher){
        databaseSource.delete(obj)
    }

    override suspend fun getAllRates(): List<Rate> = withContext(ioDispatcher) {
        return@withContext databaseSource.getAllRates()
    }

    override fun observeRates(): LiveData<List<Rate>> = databaseSource.observeRates()

    override suspend fun <T> deleteAll(type: Class<T>) = withContext(ioDispatcher){
        databaseSource.deleteAll(type)
    }

    override suspend fun getResult(code: String): Result<List<Rate>> = withContext(ioDispatcher){
        return@withContext Result.Error(Exception("Not implemented"))
    }
}
package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRepository(
    private val databaseSource: DatabaseSource,
    private val sharedPreference: SharedPreferenceSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AppRepository {

    override suspend fun save(key: String, value: String) = withContext(ioDispatcher) {
        sharedPreference.save(key, value)
    }

    override suspend fun save(key: String, value: Boolean) = withContext(ioDispatcher){
        sharedPreference.save(key, value)
    }

    override suspend fun save(key: String, value: Int) = withContext(ioDispatcher){
        sharedPreference.save(key, value)
    }

    override suspend fun getString(key: String): String? = withContext(ioDispatcher){
        return@withContext sharedPreference.getString(key)
    }

    override suspend fun getBoolean(key: String)= withContext(ioDispatcher) {
        sharedPreference.getBoolean(key)
    }

    override suspend fun getInt(key: String)= withContext(ioDispatcher){
        return@withContext sharedPreference.getInt(key)
    }

    override suspend fun remove(key: String)= withContext(ioDispatcher) {
        sharedPreference.remove(key)
    }

    override suspend fun <T> save(obj: T) = withContext(ioDispatcher){
        databaseSource.save(obj)
    }

    override suspend fun <T> delete(obj: T) = withContext(ioDispatcher){
        databaseSource.delete(obj)
    }

    override suspend fun getAllPapers(): List<Paper> = withContext(ioDispatcher){
        return@withContext databaseSource.getAllPapers()
    }

    override suspend fun getAllRates(): List<Rate> = withContext(ioDispatcher) {
        return@withContext databaseSource.getAllRates()
    }

    override fun observePapers(): LiveData<List<Paper>> = databaseSource.observePapers()

    override fun observeRates(): LiveData<List<Rate>> = databaseSource.observeRates()

    override suspend fun <T> deleteAll(type: Class<T>) = withContext(ioDispatcher){
        databaseSource.deleteAll(type)
    }
}
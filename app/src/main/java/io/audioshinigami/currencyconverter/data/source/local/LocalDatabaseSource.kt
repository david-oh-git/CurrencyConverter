package io.audioshinigami.currencyconverter.data.source.local

import androidx.lifecycle.LiveData
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDatabaseSource(
    private val paperDao: PaperDao,
    private val rateDao: RateDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): DatabaseSource {

    override suspend fun <T> save(obj: T) = withContext(ioDispatcher){
        when(obj){
            is Paper -> paperDao.add(obj)
            is Rate -> rateDao.add(obj)
        }
    }

    override suspend fun <T> delete(obj: T) = withContext(ioDispatcher){
        when(obj){
            is Paper -> paperDao.delete(obj)
            is Rate -> rateDao.delete(obj)
        }
    }

    override suspend fun <T> deleteAll(type: Class<T>) = withContext(ioDispatcher){

        when{
            type.isAssignableFrom(Paper::class.java) -> paperDao.deleteAllPaper()
            type.isAssignableFrom(Rate::class.java) -> rateDao.deleteAllRates()
        }
    }

    override fun observeRates(): LiveData<List<Rate>> = rateDao.observeRates()

    override suspend fun getAllRates(): List<Rate> = withContext(ioDispatcher){
        return@withContext rateDao.getRates()
    }

    override suspend fun getResult(code: String): Result<List<Rate>> {
        // Not needed
        return Result.Error( Exception("Not needed"))
    }

    override suspend fun deleteAllRates() = withContext(ioDispatcher){
        rateDao.deleteAllRates()
    }

}
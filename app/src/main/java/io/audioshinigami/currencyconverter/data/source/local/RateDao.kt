package io.audioshinigami.currencyconverter.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.utils.RATES_TABLE_NAME

@Dao
abstract class RateDao: BaseDao<Rate> {

    @Query("SELECT * FROM $RATES_TABLE_NAME ORDER BY rowid ASC")
    abstract fun observeRates():LiveData<List<Rate>>

    @Query("SELECT * FROM $RATES_TABLE_NAME ORDER BY rowid ASC")
    abstract suspend fun getRates(): List<Rate>

    @Query("DELETE FROM $RATES_TABLE_NAME")
    abstract suspend fun deleteAllRates()
}
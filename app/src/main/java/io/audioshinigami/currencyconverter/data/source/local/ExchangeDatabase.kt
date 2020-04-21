package io.audioshinigami.currencyconverter.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.Rate


@Database( entities = [Paper::class, Rate::class], version = 1, exportSchema = false)
abstract class ExchangeDatabase: RoomDatabase() {

    abstract fun paperDao(): PaperDao

    abstract fun rateDao(): RateDao
}
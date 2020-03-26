package io.audioshinigami.currencyconverter

import android.content.Context
import androidx.room.Room
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabase

object ServiceLocator {

    private var database: ExchangeDatabase? = null

    private fun createDatabase(context: Context): ExchangeDatabase {
        val db = Room.databaseBuilder(
            context.applicationContext,
            ExchangeDatabase::class.java, "app_db"
        ).build()
        database = db
        return db
    }
}
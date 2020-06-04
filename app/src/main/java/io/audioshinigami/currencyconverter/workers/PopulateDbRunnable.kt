package io.audioshinigami.currencyconverter.workers

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabase
import io.audioshinigami.currencyconverter.data.source.local.PaperDao
import io.audioshinigami.currencyconverter.utils.APP_DB_FILENAME
import io.audioshinigami.currencyconverter.utils.PAPER_DATA_FILENAME
import timber.log.Timber

class PopulateDbRunnable(
    private val applicationContext: Context
): Runnable {

    override fun run() {
        loadPaperDb()
    }

    private fun loadPaperDb(){
        try {
            applicationContext.assets.open(PAPER_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val paperType = object : TypeToken<List<Paper>>() {}.type
                    val paperList: List<Paper> = Gson().fromJson(jsonReader, paperType)

                    Timber.d("Data size is ${paperList.size}")
                    val paperDao: PaperDao = buildDatabase().paperDao()

                    paperDao.add(paperList)
                }
            }


        }catch (ex: Exception){
            Timber.d("Error populating the db")
            Timber.e("Error msg: \n ${ex.message}\n")
        }
    }

    private fun buildDatabase(): ExchangeDatabase =
        Room.databaseBuilder(
            applicationContext,
            ExchangeDatabase::class.java, APP_DB_FILENAME
        ).build()
}
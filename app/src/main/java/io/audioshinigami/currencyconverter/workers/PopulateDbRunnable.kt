/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

/**
 * Runnable that pre populates the [PaperDao] DB on 1st app launch only
 */

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
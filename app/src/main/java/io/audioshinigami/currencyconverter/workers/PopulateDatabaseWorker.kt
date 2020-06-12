/*
 * Copyright 2020 Audio Shinigami Studio.
 *  All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.audioshinigami.currencyconverter.workers

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabase
import io.audioshinigami.currencyconverter.data.source.local.PaperDao
import io.audioshinigami.currencyconverter.utils.APP_DB_FILENAME
import io.audioshinigami.currencyconverter.utils.PAPER_DATA_FILENAME
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class PopulateDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PAPER_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val paperType = object : TypeToken<List<Paper>>() {}.type
                    val paperList: List<Paper> = Gson().fromJson(jsonReader, paperType)

                    Timber.d("Data size is ${paperList.size}")
                    val paperDao: PaperDao = buildDatabase().paperDao()

                    paperDao.add(paperList)

                    Result.success()
                }
            }


        }catch (ex: Exception){
            Timber.d("Error populating the db")
            Result.failure()
        }
    }

    private fun buildDatabase(): ExchangeDatabase =
        Room.databaseBuilder(
            applicationContext,
            ExchangeDatabase::class.java, APP_DB_FILENAME
        ).build()
}
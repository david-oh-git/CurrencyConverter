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

package io.audioshinigami.currencyconverter.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabase
import io.audioshinigami.currencyconverter.di.ApplicationScope
import io.audioshinigami.currencyconverter.utils.APP_DB_FILENAME
import io.audioshinigami.currencyconverter.workers.PopulateDatabaseWorker
import io.audioshinigami.currencyconverter.workers.PopulateDbRunnable
import kotlinx.coroutines.Dispatchers

@Module
object AppModule {

    @JvmStatic
    @ApplicationScope
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideExchangeDatabase(context: Context): ExchangeDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            ExchangeDatabase::class.java, APP_DB_FILENAME
        ).addCallback( object: RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
//                val request = OneTimeWorkRequestBuilder<PopulateDatabaseWorker>().build()
//                WorkManager.getInstance(context).enqueue(request)
                val dbRunnable = PopulateDbRunnable(context)
                val thread = Thread(dbRunnable)
                thread.start()
            }
        }).build()
}
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

package io.audioshinigami.currencyconverter.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabase
import io.audioshinigami.currencyconverter.data.source.local.RateDao
import io.audioshinigami.currencyconverter.di.ApplicationScope
import io.audioshinigami.currencyconverter.utils.APP_DB_FILENAME
import io.audioshinigami.currencyconverter.utils.DATE_NAME
import io.audioshinigami.currencyconverter.utils.Date
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

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
        ).build()

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideRateDao(db: ExchangeDatabase): RateDao = db.rateDao()

    @Provides
    @JvmStatic
    @ApplicationScope
    @Named(DATE_NAME)
    fun provideDate() = Date.currentDate
}
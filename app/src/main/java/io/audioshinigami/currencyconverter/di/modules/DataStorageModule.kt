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
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.data.*
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabase
import io.audioshinigami.currencyconverter.data.source.local.LocalDatabaseSource
import io.audioshinigami.currencyconverter.data.source.local.LocalPreferenceSource
import io.audioshinigami.currencyconverter.data.source.local.RateDao
import io.audioshinigami.currencyconverter.data.source.remote.RemoteDataSource
import io.audioshinigami.currencyconverter.di.ApplicationScope
import io.audioshinigami.currencyconverter.network.ConverterApi
import io.audioshinigami.currencyconverter.utils.DATASOURCE_LOCAL
import io.audioshinigami.currencyconverter.utils.DATASOURCE_REMOTE
import io.audioshinigami.currencyconverter.utils.SETTINGS_PREF_NAME
import javax.inject.Named


@Module
object DataStorageModule {

    @Provides
    @JvmStatic
    @ApplicationScope
    @Named(SETTINGS_PREF_NAME)
    fun provideSharedPreferenceName(): String = SETTINGS_PREF_NAME

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideSharedPreference(@Named(SETTINGS_PREF_NAME) name: String , context: Context): SharedPreferences =
        context.getSharedPreferences( name, 0)

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideSharedPreferenceSource(sharedPreferences: SharedPreferences): SharedPreferenceSource =
        LocalPreferenceSource(sharedPreferences)

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideRateDao(db: ExchangeDatabase): RateDao = db.rateDao()

    @Provides
    @JvmStatic
    @ApplicationScope
    @Named(DATASOURCE_LOCAL)
    fun provideLocalDatabaseSource(rateDao: RateDao): DatabaseSource =
        LocalDatabaseSource(rateDao)

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideRemoteDataSource(api: ConverterApi): RemoteSource =
        RemoteDataSource(api)

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideDefaultRepository(@Named(DATASOURCE_LOCAL) databaseSource: DatabaseSource,
                                  remoteDataSource: RemoteSource
    ): AppRepository =
        DefaultRepository(
            databaseSource, remoteDataSource
        )
}
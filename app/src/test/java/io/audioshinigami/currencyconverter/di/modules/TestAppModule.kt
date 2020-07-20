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

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.data.AppRepository
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.DefaultRepository
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperDatabaseSource
import io.audioshinigami.currencyconverter.data.PaperRepoImpl
import io.audioshinigami.currencyconverter.data.PaperRepository
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.data.RemoteSource
import io.audioshinigami.currencyconverter.data.source.local.FakeDatabaseSource
import io.audioshinigami.currencyconverter.data.source.local.FakePaperSource
import io.audioshinigami.currencyconverter.data.source.local.PaperSource
import io.audioshinigami.currencyconverter.data.source.remote.FakeRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.mockito.Mockito
import javax.inject.Named
import javax.inject.Singleton

@Module
object TestAppModule {

    private const val RATE_DB = "rate_db"
    private const val PAPER_DB = "paper_db"

    @JvmStatic
    @Singleton
    @Provides
    @Named(RATE_DB)
    fun provideFakeRateDatabase(): MutableList<Rate> = mutableListOf()

    @JvmStatic
    @Singleton
    @Provides
    @Named(PAPER_DB)
    fun provideFakePaperDatabase(): MutableList<Paper> = mutableListOf()

    @JvmStatic
    @Singleton
    @Provides
    fun provideFakeDatabaseSource(@Named(RATE_DB) rateDb: MutableList<Rate>): DatabaseSource = FakeDatabaseSource(rateDb)

    @JvmStatic
    @Singleton
    @Provides
    fun providePaperSource(@Named(PAPER_DB) paperDb: MutableList<Paper>): PaperDatabaseSource = FakePaperSource(paperDb)

    @JvmStatic
    @Singleton
    @Provides
    fun provideFakeRemoteSource(): RemoteSource = FakeRemoteDataSource { code -> 0.25}

    @JvmStatic
    @Provides
    @Singleton
    fun provideIoDispatcher() = Dispatchers.Unconfined

    @JvmStatic
    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = Mockito.mock(SharedPreferences::class.java)

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppRepository(
        databaseSource: DatabaseSource,
        remoteSource: RemoteSource,
        sharedPreferences: SharedPreferences,
        ioDispatcher: CoroutineDispatcher
    ): AppRepository = DefaultRepository(databaseSource, remoteSource, sharedPreferences, ioDispatcher)

    @JvmStatic
    @Singleton
    @Provides
    fun providePaperRepository(
        paperDatabaseSource: PaperDatabaseSource,
        sharedPreferences: SharedPreferences,
        ioDispatcher: CoroutineDispatcher): PaperRepository
            = PaperRepoImpl(paperDatabaseSource, sharedPreferences, ioDispatcher)
}
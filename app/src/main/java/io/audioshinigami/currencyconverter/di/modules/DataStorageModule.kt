package io.audioshinigami.currencyconverter.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.data.AppRepository
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.DefaultRepository
import io.audioshinigami.currencyconverter.data.SharedPreferenceSource
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
    @Named(DATASOURCE_REMOTE)
    fun provideRemoteDataSource(api: ConverterApi): DatabaseSource =
        RemoteDataSource(api)

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideDefaultRepository(@Named(DATASOURCE_LOCAL) databaseSource: DatabaseSource,
                                 @Named(DATASOURCE_REMOTE) remoteDataSource: DatabaseSource
    ): AppRepository =
        DefaultRepository(
            databaseSource, remoteDataSource
        )
}
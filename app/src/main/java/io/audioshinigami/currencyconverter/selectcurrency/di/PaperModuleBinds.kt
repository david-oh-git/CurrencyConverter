package io.audioshinigami.currencyconverter.selectcurrency.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.audioshinigami.currencyconverter.data.PaperDatabaseSource
import io.audioshinigami.currencyconverter.data.PaperRepoImpl
import io.audioshinigami.currencyconverter.data.PaperRepository
import io.audioshinigami.currencyconverter.data.source.local.PaperSource
import io.audioshinigami.currencyconverter.di.ViewModelKey
import io.audioshinigami.currencyconverter.selectcurrency.PaperViewModel
import javax.inject.Singleton

@Module
abstract class PaperModuleBinds {

    @Binds
    @IntoMap
    @SelectPaperFragScope
    @ViewModelKey(PaperViewModel::class)
    abstract fun bindViewModel(viewModel: PaperViewModel): ViewModel

    @Binds
    @SelectPaperFragScope
    abstract fun bindPaperDataBaseSource(source: PaperSource): PaperDatabaseSource

    @Binds
    @SelectPaperFragScope
    abstract fun bindRepository(repository: PaperRepoImpl): PaperRepository
}
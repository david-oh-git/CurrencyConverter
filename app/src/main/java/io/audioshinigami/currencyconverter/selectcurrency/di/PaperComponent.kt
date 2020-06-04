package io.audioshinigami.currencyconverter.selectcurrency.di

import dagger.Subcomponent
import io.audioshinigami.currencyconverter.selectcurrency.CurrencySelectFragment

@SelectPaperFragScope
@Subcomponent(
    modules = [
        PaperModuleBinds::class,
        PaperModule::class
    ]
)
interface PaperComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PaperComponent
    }

    fun inject(target: CurrencySelectFragment)
}
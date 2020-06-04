package io.audioshinigami.currencyconverter.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import io.audioshinigami.currencyconverter.di.ApplicationScope
import io.audioshinigami.currencyconverter.di.modules.AppModule
import io.audioshinigami.currencyconverter.di.modules.DataStorageModule
import io.audioshinigami.currencyconverter.di.modules.RetrofitModule
import io.audioshinigami.currencyconverter.di.modules.ViewModelBuilderModule
import io.audioshinigami.currencyconverter.selectcurrency.di.PaperComponent

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        DataStorageModule::class,
        RetrofitModule::class,
        ViewModelBuilderModule::class,
        SubComponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun paperComponent(): PaperComponent.Factory

}

@Module(
    subcomponents = [
        PaperComponent::class
    ]
)
object SubComponentsModule
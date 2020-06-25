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

package io.audioshinigami.currencyconverter.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import io.audioshinigami.currencyconverter.convertAmount.di.ConvertComponent
import io.audioshinigami.currencyconverter.data.AppRepository
import io.audioshinigami.currencyconverter.di.ApplicationScope
import io.audioshinigami.currencyconverter.di.modules.AppModule
import io.audioshinigami.currencyconverter.di.modules.AppModuleBinds
import io.audioshinigami.currencyconverter.di.modules.RetrofitModule
import io.audioshinigami.currencyconverter.di.modules.ViewModelBuilderModule
import io.audioshinigami.currencyconverter.selectcurrency.di.PaperComponent

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        ViewModelBuilderModule::class,
        AppModuleBinds::class,
        SubComponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun paperComponent(): PaperComponent.Factory

    fun convertComponent(): ConvertComponent.Factory

    val repository: AppRepository

}

@Module(
    subcomponents = [
        PaperComponent::class,
        ConvertComponent::class
    ]
)
object SubComponentsModule
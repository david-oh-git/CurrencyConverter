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
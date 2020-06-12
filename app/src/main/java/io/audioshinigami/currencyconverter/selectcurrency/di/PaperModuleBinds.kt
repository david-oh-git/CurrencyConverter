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
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
import io.audioshinigami.currencyconverter.data.DefaultRepositoryTest
import io.audioshinigami.currencyconverter.data.PaperRepoImplTest
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabaseTest
import io.audioshinigami.currencyconverter.di.modules.TestAppModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@Singleton
@Component( modules = [
        TestAppModule::class
    ]
)
interface TestAppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestAppComponent
    }

    @ExperimentalCoroutinesApi
    fun inject(target: DefaultRepositoryTest)

    @ExperimentalCoroutinesApi
    fun inject(target: PaperRepoImplTest)

    @ExperimentalCoroutinesApi
    fun inject(target: ExchangeDatabaseTest)
}
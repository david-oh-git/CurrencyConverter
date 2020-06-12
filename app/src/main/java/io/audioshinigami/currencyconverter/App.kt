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

package io.audioshinigami.currencyconverter

import android.app.Application
import io.audioshinigami.currencyconverter.di.components.AppComponent
import io.audioshinigami.currencyconverter.di.components.DaggerAppComponent
import timber.log.Timber

class App: Application() {

    val appComponent: AppComponent by lazy {
        initDagger()
    }

    override fun onCreate() {
        super.onCreate()

        if( BuildConfig.DEBUG )
            Timber.plant( Timber.DebugTree() )

        synchronized(this){
            instance = this
        }
    } /*end onCreate*/

    private fun initDagger(): AppComponent =
        DaggerAppComponent.factory().create(applicationContext)

    companion object{
        lateinit var instance: App
        private set
    }
}
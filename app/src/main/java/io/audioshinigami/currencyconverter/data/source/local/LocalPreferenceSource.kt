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

package io.audioshinigami.currencyconverter.data.source.local

import android.content.SharedPreferences
import androidx.core.content.edit
import io.audioshinigami.currencyconverter.data.SharedPreferenceSource
import io.audioshinigami.currencyconverter.utils.DEFAULT_PREF_INT_VALUE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalPreferenceSource(
    private val sharedPreferences: SharedPreferences ,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): SharedPreferenceSource {

    override suspend fun save(key: String, value: String) =  withContext(ioDispatcher){
        sharedPreferences.edit {
            putString( key, value )
        }
    }

    override suspend fun save(key: String, value: Boolean) = withContext(ioDispatcher) {
        sharedPreferences.edit {
            putBoolean( key, value )
        }
    }

    override suspend fun save(key: String, value: Int) = withContext(ioDispatcher) {
        sharedPreferences.edit {
            putInt( key, value )
        }
    }

    override suspend fun getString(key: String): String = withContext(ioDispatcher){
        return@withContext sharedPreferences.getString(key, "" ) ?: ""
    }

    override suspend fun getBoolean(key: String): Boolean = withContext(ioDispatcher){
        return@withContext sharedPreferences.getBoolean(key, false )
    }

    override suspend fun getInt(key: String) = withContext(ioDispatcher) {
        return@withContext sharedPreferences.getInt(key, DEFAULT_PREF_INT_VALUE)
    }

    override suspend fun remove(key: String) = withContext(ioDispatcher){
        sharedPreferences.edit{
            remove(key)
        }
    }
}
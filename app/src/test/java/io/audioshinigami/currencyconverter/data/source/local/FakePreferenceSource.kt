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

import io.audioshinigami.currencyconverter.data.SharedPreferenceSource

class FakePreferenceSource(
    var sharedPreference: MutableMap<String, Any> = mutableMapOf()
) : SharedPreferenceSource {

    override suspend fun save(key: String, value: String) {
        sharedPreference[key] = value
    }

    override suspend fun save(key: String, value: Boolean) {
        sharedPreference[key] = value
    }

    override suspend fun save(key: String, value: Int) {
        sharedPreference[key] = value
    }

    override suspend fun getString(key: String): String {
        return ( sharedPreference[key] as String)
    }

    override suspend fun getBoolean(key: String): Boolean {
        return ( sharedPreference[key] as Boolean)
    }

    override suspend fun getInt(key: String): Int {
        return ( sharedPreference[key] as Int)
    }

    override suspend fun remove(key: String) {
        sharedPreference.remove(key)
    }
}
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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.network.Result

class FakeDatabaseSource(
    var rateStorage: MutableList<Rate> = mutableListOf()
): DatabaseSource {

    override suspend fun <T> save(obj: T) {

        when(obj){
            is Rate -> rateStorage.add(obj)
        }
    }

    override suspend fun <T> delete(obj: T) {
        when(obj){
            is Rate -> rateStorage.remove(obj)
        }
    }

    override fun observeRates(): LiveData<List<Rate>> {
        return MutableLiveData(rateStorage)
    }

    override suspend fun getAllRates(): List<Rate> {
        return rateStorage
    }

    override suspend fun <T> deleteAll(type: Class<T>) {
        when{
            type.isAssignableFrom(Rate::class.java) -> rateStorage.clear()
        }
    }

    override suspend fun getResult(code: String): Result<List<Rate>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllRates() {
        rateStorage.clear()
    }
}
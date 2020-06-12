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

package io.audioshinigami.currencyconverter.data.source.remote

import androidx.lifecycle.LiveData
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.network.Result

class FakeRemoteDataSource(
    var rates: MutableList<Rate>? = mutableListOf()
): DatabaseSource {

    override suspend fun getResult(code: String): Result<List<Rate>> {

        rates?.let {
            it.add( Rate(56, code, 0.30, "no date"))
            return Result.Success(it)
        }
        return Result.Error( Exception("Error : can not get data"))
    }

    override suspend fun <T> save(obj: T) {
        TODO("Not yet implemented")
    }

    override suspend fun <T> delete(obj: T) {
        TODO("Not yet implemented")
    }

    override fun observeRates(): LiveData<List<Rate>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRates(): List<Rate> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> deleteAll(type: Class<T>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllRates() {
        TODO("Not yet implemented")
    }
}
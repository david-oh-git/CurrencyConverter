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
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDatabaseSource(
    private val rateDao: RateDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): DatabaseSource {

    override suspend fun <T> save(obj: T) = withContext(ioDispatcher){
        when(obj){
            is Rate -> rateDao.add(obj)
        }
    }

    override suspend fun <T> delete(obj: T) = withContext(ioDispatcher){
        when(obj){
            is Rate -> rateDao.delete(obj)
        }
    }

    override suspend fun <T> deleteAll(type: Class<T>) = withContext(ioDispatcher){

        when{
            type.isAssignableFrom(Rate::class.java) -> rateDao.deleteAllRates()
        }
    }

    override fun observeRates(): LiveData<List<Rate>> = rateDao.observeRates()

    override suspend fun getAllRates(): List<Rate> = withContext(ioDispatcher){
        return@withContext rateDao.getRates()
    }

    override suspend fun getResult(code: String): Result<List<Rate>> {
        // Not needed
        return Result.Error( Exception("Not needed"))
    }

    override suspend fun deleteAllRates() = withContext(ioDispatcher){
        rateDao.deleteAllRates()
    }

}
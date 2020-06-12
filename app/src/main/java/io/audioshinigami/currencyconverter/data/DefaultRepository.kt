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

package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRepository(
    private val databaseSource: DatabaseSource,
    private val remoteDataSource: DatabaseSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AppRepository {

    override val fromCode: MutableLiveData<String>
        get() = MutableLiveData("NGN")
    override val toCode: MutableLiveData<String>
        get() = MutableLiveData("USD")

    override suspend fun <T> save(obj: T) = withContext(ioDispatcher){
        databaseSource.save(obj)
    }

    override suspend fun <T> delete(obj: T) = withContext(ioDispatcher){
        databaseSource.delete(obj)
    }

    override suspend fun getAllRates(): List<Rate> = withContext(ioDispatcher) {
        return@withContext databaseSource.getAllRates()
    }

    override fun observeRates(): LiveData<List<Rate>> = databaseSource.observeRates()

    override suspend fun <T> deleteAll(type: Class<T>) = withContext(ioDispatcher){
        databaseSource.deleteAll(type)
    }

    override suspend fun getResult(code: String): Result<List<Rate>> = withContext(ioDispatcher){
        // TODO implement
        return@withContext Result.Error(Exception("Not implemented"))
    }

    override suspend fun setNetworkConnection(enabled: Boolean) = withContext(ioDispatcher) {
        hasNetworkConnection.postValue(enabled)
    }

    override suspend fun setFromCode(code: String) = withContext(ioDispatcher) {
        fromCode.postValue(code)
    }

    override suspend fun setToCode(code: String) = withContext(ioDispatcher) {
        toCode.postValue(code)
    }
}
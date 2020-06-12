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

package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRepository(
    private val databaseSource: DatabaseSource,
    private val remoteDataSource: RemoteSource,
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
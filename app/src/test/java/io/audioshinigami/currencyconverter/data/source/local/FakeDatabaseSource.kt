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
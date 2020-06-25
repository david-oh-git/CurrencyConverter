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

package io.audioshinigami.currencyconverter.data.source.remote

import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.data.RemoteSource
import io.audioshinigami.currencyconverter.network.ConverterApi
import io.audioshinigami.currencyconverter.utils.DATE_NAME
import io.audioshinigami.currencyconverter.utils.Date
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class RemoteDataSource @Inject constructor(
    private val api: ConverterApi,
    @Named(DATE_NAME) private val currentDate: String,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): BaseRemoteDataSource() , RemoteSource {

//    currency code format to get both 'USD_AED,AED_USD'
    private suspend fun fetchRateFromApi(currencyCode: String) = withContext(ioDispatcher) {

        safeApiCall(
            call = { api.getRate(currencyCode = "$currencyCode,${reverseCode(currencyCode)}").await() },
            errorMsg = "Error fetching currency rate"
        )

    }

    override suspend fun getRate(code: String): List<Rate> = withContext(ioDispatcher){

        return@withContext fetchRateFromApi(code)?.rate?.map {
            Timber.d("Key is ${it.key} Value is ${it.value}")
            Rate(0, it.key, it.value.toDouble(), currentDate)
        } ?: mutableListOf()

    }

    private fun reverseCode(callingCode: String) = callingCode.split("_").reversed().joinToString("_")

}
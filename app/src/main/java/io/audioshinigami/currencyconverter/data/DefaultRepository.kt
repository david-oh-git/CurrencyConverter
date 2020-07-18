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

import android.content.SharedPreferences
import androidx.core.content.edit
import io.audioshinigami.currencyconverter.utils.CONVERTED_AMOUNT_KEY
import io.audioshinigami.currencyconverter.utils.FROM_CODE_KEY
import io.audioshinigami.currencyconverter.utils.INPUT_AMOUNT_KEY
import io.audioshinigami.currencyconverter.utils.TO_CODE_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val databaseSource: DatabaseSource,
    private val remoteDataSource: RemoteSource,
    private val sharedPreferences: SharedPreferences,
    private val ioDispatcher: CoroutineDispatcher
): AppRepository {

    override val fromCode: String
            get() = sharedPreferences.getString(FROM_CODE_KEY, "NGN") ?: "NGN"
    
    override val toCode: String
        get() = sharedPreferences.getString(TO_CODE_KEY, "USD") ?: "USD"

    override val convertedAmount: String
        get() = sharedPreferences.getString(CONVERTED_AMOUNT_KEY, "0.0") ?: "0.0"

    override val inputAmount: String
        get() = sharedPreferences.getString(INPUT_AMOUNT_KEY, "0.0") ?: "0.0"

    override suspend fun save(rate: Rate) = withContext(ioDispatcher){
        databaseSource.save(rate)
    }

    override suspend fun save(rates: List<Rate>) = withContext(ioDispatcher){
        databaseSource.save(rates)
    }

    override suspend fun find(code: String): Rate? = withContext(ioDispatcher){
        return@withContext databaseSource.find(code)
    }

    override suspend fun delete(rate: Rate) = withContext(ioDispatcher){
        databaseSource.delete(rate)
    }

    override suspend fun getAllRates(): List<Rate> = withContext(ioDispatcher) {
        return@withContext databaseSource.getAllRates()
    }

    override suspend fun deleteAll() = withContext(ioDispatcher){
        databaseSource.deleteAll()
    }

    override suspend fun getResult(code: String): Rate? = withContext(ioDispatcher){
        val rates = remoteDataSource.getRate(code)
        Timber.d("Number of rates received ? ${rates.size}")
        databaseSource.save(rates)
        return@withContext rates.find { it.code == code }
    }

    override suspend fun setFromCode(code: String) = withContext(ioDispatcher) {
        sharedPreferences.edit {
            putString(FROM_CODE_KEY, code)
        }
    }

    override suspend fun setToCode(code: String) = withContext(ioDispatcher) {
        sharedPreferences.edit {
            putString(TO_CODE_KEY, code)
        }
    }

    override suspend fun setConvertedAmount(amount: String) = withContext(ioDispatcher){
        sharedPreferences.edit {
            putString(CONVERTED_AMOUNT_KEY, amount)
        }
    }

    override suspend fun setInputAmount(amount: String) = withContext(ioDispatcher){
        sharedPreferences.edit {
            putString(INPUT_AMOUNT_KEY, amount)
        }
    }

    override fun setSharedPreferenceListener(listener: SharedPreferences.OnSharedPreferenceChangeListener){
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun removeSharedPreferenceListener(listener: SharedPreferences.OnSharedPreferenceChangeListener){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
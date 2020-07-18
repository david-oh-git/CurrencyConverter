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

interface AppRepository {

    val fromCode: String

    val toCode: String

    fun setSharedPreferenceListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

    fun removeSharedPreferenceListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

    val convertedAmount: String

    val inputAmount: String

    suspend fun save(rate: Rate)

    suspend fun save(rates: List<Rate>)

    suspend fun delete( rate: Rate)

    suspend fun getAllRates(): List<Rate>

    suspend fun find(code: String): Rate?

    suspend fun deleteAll()

    suspend fun getResult(code: String): Rate?

    suspend fun setFromCode(code: String)

    suspend fun setToCode(code: String)

    suspend fun setConvertedAmount(amount: String)

    suspend fun setInputAmount(amount: String)

}
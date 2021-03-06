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

import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Rate

class FakeDatabaseSource(
    var rateStorage: MutableList<Rate> = mutableListOf()
): DatabaseSource {

    override suspend fun save(rate: Rate) {
        rateStorage.add(rate)
    }

    override suspend fun save(rates: List<Rate>) {
        rateStorage.addAll(rates)
    }

    override suspend fun find(code: String): Rate? = rateStorage.find { it.code == code }

    override suspend fun delete(rate: Rate) {
        rateStorage.remove(rate)
    }

    override suspend fun getAllRates(): List<Rate> {
        return rateStorage
    }

    override suspend fun deleteAll() {
        rateStorage.clear()
    }

    override suspend fun deleteAllRates() {
        rateStorage.clear()
    }
}
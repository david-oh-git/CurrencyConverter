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

package io.audioshinigami.currencyconverter.utils

import io.audioshinigami.currencyconverter.data.Rate
import kotlin.random.Random

/**
 * To auto create [Rate] data for testing
 */

object RateFactory {

    val rates = listOf(
        Rate(randomId(),"NGN_USD", randomRate(), Date.currentDate),
        Rate(randomId(),"NGN_AUD", randomRate(), Date.currentDate),
        Rate(randomId(),"JYP_EUR", randomRate(), Date.currentDate),
        Rate(randomId(),"NZD_EUR", randomRate(), Date.currentDate),
        Rate(randomId(),"NZD_RWF", randomRate(), Date.currentDate)
    )

    val rate: Rate
        get() = Rate(randomId(), "AUD_NGN", randomRate(), Date.currentDate)

    private fun randomRate() = Random.nextDouble(0.0, 1.0)

    private fun randomId() = Random.nextInt(1,11)
}